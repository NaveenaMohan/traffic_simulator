package managers.vehicle;

import common.Common;
import dataAndStructures.IDataAndStructures;
import jdk.nashorn.internal.ir.Block;
import managers.globalconfig.IGlobalConfigManager;
import managers.runit.Blockage;
import managers.runit.IRUnitManager;
import managers.runit.RUnit;
import managers.runit.*;
import managers.runit.TrafficLight;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;

/**
 * Created by Fabians on 18/02/2015.
 */
public class VehicleMotor {
    private double maxAcceleration;//maximum acceleration the car can get
    private double maxDeceleration;
    private double currentVelocity;//current speed in metres
    private double currentAcceleration;
    private double depthInCurrentRUnit;//if the RUnit is say 100m long we need to keep track of how far in the vehicle is
    private int maxSpeedLimit;//the speed limit as according to the signs
    public String currentStrategy;

    public VehicleMotor(double maxAcceleration, double maxDeceleration, int maxSpeedLimit) {
        this.maxAcceleration = maxAcceleration;
        this.maxDeceleration = maxDeceleration;
        this.currentVelocity = 0;
        this.currentAcceleration = 0;
        this.depthInCurrentRUnit = 0;
        this.maxSpeedLimit = maxSpeedLimit;
    }

    public double getCurrentVelocity() {
        return currentVelocity;
    }

    public IRUnitManager PrepareAction(IRUnitManager rUnit, Driver driver, VehicleState vehicleState
            , double slipperinessOffset) {

        //get the slowest and next objects within x metres
        VehicleMemoryObject slowestObject = vehicleState.getSlowestWithin(200);
        VehicleMemoryObject nextObject = vehicleState.nextObjectWithin(200);

        //initially aim for the speed limit
        currentAcceleration = aimForSpeed(
                driver.getSpeed(slipperinessOffset, maxSpeedLimit),//maximum speed limit
                0,//safe stop distance
                1//distance to
        );

        if (nextObject != null) {

            respondToObject(rUnit, nextObject, slowestObject, driver, slipperinessOffset, vehicleState.isChangeableClear());
        }
        return rUnit;
    }

    public IRUnitManager performAction(double timePassed, IDataAndStructures dataAndStructures,
                                       IRUnitManager rUnit) {

        //get distance to be travelled in the timePassed with proposedVelocity
        double distanceTravelled = currentVelocity * timePassed;

        //get the amount of rUnits to travel
        int RUnitsTravelled = (int) (Math.floor((distanceTravelled + depthInCurrentRUnit)
                / dataAndStructures.getGlobalConfigManager().getMetresPerRUnit()));

        //update velocity based on time passed and currentAcceleration
        updateVelocity(timePassed);

        //get the depth
        depthInCurrentRUnit = (distanceTravelled + depthInCurrentRUnit) %
                dataAndStructures.getGlobalConfigManager().getMetresPerRUnit();

        //travel the rUnits
        for (int i = 0; i < RUnitsTravelled; i++) {//go through as many metres as many you have travelled since last move
            if (rUnit.getNextRUnitList().size() > 0) {
                if (rUnit.getNextRUnitList().get(0).getBlockage() == null) {

                    //see and process the objects we have just passed
                    processObjectPassed(VehiclePerception.getObjectAtRUnit(rUnit));

                    //advance
                    rUnit = rUnit.getNextRUnitList().get(0);
                }
            }
            break;
        }
        return rUnit;
    }


    private IRUnitManager respondToObject(IRUnitManager currentRUnit, VehicleMemoryObject nextObject, VehicleMemoryObject slowestObject,
                                          Driver driver, double slipperinessOffset, boolean isChangeableClear) {
        //this function forms a response to a moving or stationary object

        //distance by which you should start matching their speed
        double decelerationDistanceToNextObject = nextObject.getDistance() -
                driver.getDecelerationSafeDistance(
                        currentVelocity,
                        nextObject.getVelocity(),
                        nextObject.getDistance(),
                        slipperinessOffset,
                        nextObject.getObject());
        double decelerationDistanceToSlowestObject = slowestObject.getDistance() -
                driver.getDecelerationSafeDistance(
                        currentVelocity,
                        slowestObject.getVelocity(),
                        slowestObject.getDistance(),
                        slipperinessOffset,
                        slowestObject.getObject());

        //focus on the object which speed you need to match sooner
        VehicleMemoryObject objectToMatch = (decelerationDistanceToNextObject > decelerationDistanceToSlowestObject ?
                slowestObject : nextObject);


        String nextData = nextObject.getObject() + (nextObject.getObject() instanceof Vehicle ? "(" + ((Vehicle) nextObject.getObject()).getVehID() + ")" : "") + " v: " + Common.round(nextObject.getVelocity(), 2) +
                " dis: " + Common.round(nextObject.getDistance(), 2) +
                " sdec: " + Common.round(driver.getDecelerationSafeDistance(currentVelocity, nextObject.getVelocity(), nextObject.getDistance() - depthInCurrentRUnit - 5, slipperinessOffset, nextObject.getObject()), 2) +
                " decDist: " + Common.round(decelerationDistanceToNextObject, 2);
        String slowestData = slowestObject.getObject() + (slowestObject.getObject() instanceof Vehicle ? "(" + ((Vehicle) slowestObject.getObject()).getVehID() + ")" : "") + " v: " + Common.round(slowestObject.getVelocity(), 2) +
                " dis: " + Common.round(slowestObject.getDistance(), 2) +
                " sdec: " + Common.round(driver.getDecelerationSafeDistance(currentVelocity, slowestObject.getVelocity(), slowestObject.getDistance() - depthInCurrentRUnit - 5, slipperinessOffset, slowestObject.getObject()), 2) +
                " decDist: " + Common.round(decelerationDistanceToSlowestObject, 2);

        currentStrategy = "next {" + nextData + "} slowest {" + slowestData + "} objectToMatch: " + objectToMatch.getObject();

        double speedToMatch = maxSpeedLimit;

        //if you are within the distance to start slowing down
        if (objectToMatch.getDistance() <= driver.getDecelerationSafeDistance(
                currentVelocity,
                objectToMatch.getVelocity(),
                objectToMatch.getDistance() - depthInCurrentRUnit - 5,
                slipperinessOffset,
                objectToMatch.getObject()))
            if (isChangeableClear)//change lane if possible
                return currentRUnit.getChangeAbleRUnit();
            else {
                if (objectToMatch.getVelocity() < currentVelocity)
                    speedToMatch = objectToMatch.getVelocity();
            }
        //aim to match the object speed
        currentAcceleration = aimForSpeed(
                driver.getSpeed(slipperinessOffset, speedToMatch),
                driver.getStopDistance(slipperinessOffset, objectToMatch.getObject()),
                objectToMatch.getDistance() - depthInCurrentRUnit);

        currentStrategy +=  "[gS:" + Common.round(driver.getSpeed(slipperinessOffset, speedToMatch),2) +
                ", gSD:" + Common.round(driver.getStopDistance(slipperinessOffset, objectToMatch.getObject()), 2) +
                " D:" + Common.round(objectToMatch.getDistance() - depthInCurrentRUnit, 2) +
                " a:" + Common.round(currentAcceleration,2);

        return currentRUnit;
    }


    private double aimForSpeed(double requiredVelocity, double safeStopDistance, double distance)//returns the acceleration
    {
        //this function gradually changes the vehicle speed to match the requiredSpeed in distance metres

        //to get the acceleration we will use a kinematic equation
        //finalVelocity^2=initialVelocity^2 + 2*acceleration*distance
        //Hence acceleration=(finalVelocity^2-initialVelocity^2)/2*distance

        double acceleration;

        //if the object is very close drop down your acceleration to -100 in order to come to a full stop

        if (requiredVelocity < currentVelocity & distance < safeStopDistance)
            acceleration = maxDeceleration;
        else
            acceleration = ((requiredVelocity * requiredVelocity) - (currentVelocity * currentVelocity)) /
                    (2 * ((distance-safeStopDistance) == 0 ? 1 : (distance-safeStopDistance)));

        return Math.max(Math.min(acceleration, maxAcceleration), maxDeceleration);
    }


    private void updateVelocity(double timePassed) {
        //velocity can't be below 0
        currentVelocity = Math.max(0,
                currentVelocity + currentAcceleration * timePassed);
    }

    private void processObjectPassed(Object obj) {
        //if you have just passed a speed sign update your maxSpeedLimit
        if (obj instanceof SpeedLimitSign) {
            maxSpeedLimit = (((SpeedLimitSign) obj).getSpeedLimit() * 1000) / 3600;//convert km/h to m/s
        }
    }

    public double getCurrentAcceleration() { //ADDED BY LORENA TO TEST ACCELERATION IN THE REPORTS
        return currentAcceleration;
    }


    public double getDepthInCurrentRUnit() {
        return depthInCurrentRUnit;
    }
}
