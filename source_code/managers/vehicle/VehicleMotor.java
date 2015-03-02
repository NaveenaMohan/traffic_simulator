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
import managers.space.VehicleDirection;

/**
 * Created by Fabians on 18/02/2015.
 */
public class VehicleMotor {
    //this objects holds the information required to move on the map. It also provides functions for moving on the map

    private double maxAcceleration;//maximum acceleration the car can get
    private double maxDeceleration;
    private double currentVelocity;//current speed in metres
    private double currentAcceleration;
    private double depthInCurrentRUnit;//if the RUnit is say 100m long we need to keep track of how far in the vehicle is
    private int maxSpeedLimit;//the speed limit as according to the signs
    private ObjectInSpace objectInSpace;
    private String destination;


    //TODO: delete this
    public String currentStrategy;

    public VehicleMotor(double maxAcceleration, double maxDeceleration, int maxSpeedLimit, String destination, ObjectInSpace objectInSpace) {
        this.maxAcceleration = maxAcceleration;
        this.maxDeceleration = maxDeceleration;
        this.currentVelocity = 0;
        this.currentAcceleration = 0;
        this.depthInCurrentRUnit = 0;
        this.maxSpeedLimit = maxSpeedLimit;
        this.destination = destination;
        this.objectInSpace = objectInSpace;

        objectInSpace.setDirection(new VehicleDirection(1, 1, 1, 1));
    }

    public ObjectInSpace getObjectInSpace() {
        return objectInSpace;
    }

    public String getDestination() {
        return destination;
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

            //react controls speed and immediate decision making
            react(rUnit, nextObject, slowestObject, driver, slipperinessOffset, vehicleState.isChangeableClear());

            //replan is responsible for long term decision making
            replan(nextObject, vehicleState);
        }
        return rUnit;
    }

    public IRUnitManager performAction(double timePassed, IDataAndStructures dataAndStructures,
                                       IRUnitManager rUnit, VehicleState vehicleState) {

        //get the previous rUnit for Direction calculations
        IRUnitManager previousrUnit = rUnit;

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
                //see and process the objects we have just passed
                processObjectPassed(VehiclePerception.getObjectAtRUnit(rUnit), vehicleState);

                //advance
                rUnit = chooseNext(rUnit, vehicleState);
            }
        }

        //adjust your position in space
        objectInSpace.setX(rUnit.getX());
        objectInSpace.setY(rUnit.getY());
        if (previousrUnit.getId() != rUnit.getId())
            objectInSpace.setDirection(new VehicleDirection(previousrUnit.getX(), previousrUnit.getY(), rUnit.getX(), rUnit.getY()));

        return rUnit;
    }


    private void replan(VehicleMemoryObject nextObject, VehicleState vehicleState) {
        //the only plan the the vehicle can currently have is to choose it's next turn if it has a set destination

        //if you have a destination
        if (!destination.isEmpty()) {
            //if the next object is a direction sign
            if (nextObject.getObject() instanceof DirectionSign) {
                //if the destination on the sign matches your destination
                if (((DirectionSign) nextObject.getObject()).getLocation() == destination) {
                    //set your next direction to the one from the sign
                    vehicleState.setNextDirectionAtDecisionPoint(((DirectionSign) nextObject.getObject()).getDirectionSignType());
                }
            }

            //if next object is a decision point
            if (nextObject.getObject() instanceof DecisionPoint) {
                //if you have next direction set
                if (vehicleState.getNextDirectionAtDecisionPoint() != null) {
                    //set your nextRUnit
                    vehicleState.setNextRUnitAfterDecisionPoint(getNextForDirection(nextObject.getrUnit(), vehicleState));
                }
            }
        }
    }

    private IRUnitManager react(IRUnitManager currentRUnit, VehicleMemoryObject nextObject, VehicleMemoryObject slowestObject,
                                Driver driver, double slipperinessOffset, boolean isChangeableClear) {
        //this function forms a response to a moving or stationary object

        //distance whether you match the speed on the next Object or the Slowest object
        double decelerationDistanceToNextObject = nextObject.getDistance() -
                driver.getDecelerationSafeDistance(
                        currentVelocity,
                        nextObject.getVelocity(),
                        nextObject.getDistance() - additionalDistances(),
                        slipperinessOffset,
                        nextObject);
        double decelerationDistanceToSlowestObject = slowestObject.getDistance() -
                driver.getDecelerationSafeDistance(
                        currentVelocity,
                        slowestObject.getVelocity(),
                        slowestObject.getDistance() - additionalDistances(),
                        slipperinessOffset,
                        slowestObject);

        //focus on the object which speed you need to match sooner
        VehicleMemoryObject objectToMatch = (decelerationDistanceToNextObject > decelerationDistanceToSlowestObject ?
                slowestObject : nextObject);


        String nextData = nextObject.getObject() + (nextObject.getObject() instanceof Vehicle ? "(" + ((Vehicle) nextObject.getObject()).getVehID() + ")" : "") + " v: " + Common.round(nextObject.getVelocity(), 2) +
                " dis: " + Common.round(nextObject.getDistance(), 2) +
                " sdec: " + Common.round(driver.getDecelerationSafeDistance(currentVelocity, nextObject.getVelocity(), nextObject.getDistance() - additionalDistances(), slipperinessOffset, nextObject), 2) +
                " decDist: " + Common.round(decelerationDistanceToNextObject, 2);
        String slowestData = slowestObject.getObject() + (slowestObject.getObject() instanceof Vehicle ? "(" + ((Vehicle) slowestObject.getObject()).getVehID() + ")" : "") + " v: " + Common.round(slowestObject.getVelocity(), 2) +
                " dis: " + Common.round(slowestObject.getDistance(), 2) +
                " sdec: " + Common.round(driver.getDecelerationSafeDistance(currentVelocity, slowestObject.getVelocity(), slowestObject.getDistance() - additionalDistances(), slipperinessOffset, slowestObject), 2) +
                " decDist: " + Common.round(decelerationDistanceToSlowestObject, 2);

        currentStrategy = "next {" + nextData + "} slowest {" + slowestData + "} objectToMatch: " + objectToMatch.getObject();

        double speedToMatch = maxSpeedLimit;

        //if you are within the distance to start slowing down
        if (objectToMatch.getDistance() <= driver.getDecelerationSafeDistance(
                currentVelocity,
                objectToMatch.getVelocity(),
                objectToMatch.getDistance() - additionalDistances(),
                slipperinessOffset,
                objectToMatch))
            if (isChangeableClear)//change lane if possible
                return currentRUnit.getChangeAbleRUnit();
            else {
                speedToMatch = Math.min(maxSpeedLimit, objectToMatch.getVelocity());
            }

        //aim to match the object speed
        currentAcceleration = aimForSpeed(
                driver.getSpeed(slipperinessOffset, speedToMatch),
                driver.getStopDistance(slipperinessOffset, objectToMatch),
                objectToMatch.getDistance() - (objectToMatch.isPassable() ? 0 : additionalDistances()));

        currentStrategy += "[gS:" + Common.round(driver.getSpeed(slipperinessOffset, speedToMatch), 2) +
                ", gSD:" + Common.round(driver.getStopDistance(slipperinessOffset, objectToMatch), 2) +
                " D:" + Common.round(objectToMatch.getDistance() - additionalDistances(), 2) +
                " a:" + Common.round(currentAcceleration, 2);

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
                    (2 * ((distance - safeStopDistance) == 0 ? 1 : (distance - safeStopDistance)));

        return Math.max(Math.min(acceleration, maxAcceleration), maxDeceleration);
    }


    private void updateVelocity(double timePassed) {
        //velocity can't be below 0
        currentVelocity = Math.max(0,
                currentVelocity + currentAcceleration * timePassed);
    }

    private void processObjectPassed(Object obj, VehicleState vehicleState) {
        //if you have just passed a speed sign update your maxSpeedLimit
        if (obj instanceof SpeedLimitSign) {
            maxSpeedLimit = (((SpeedLimitSign) obj).getSpeedLimit() * 1000) / 3600;//convert km/h to m/s
        }

        //if you have just passed a decision Point
        if (obj instanceof DecisionPoint) {
            vehicleState.setNextRUnitAfterDecisionPoint(null);
            vehicleState.setNextDirectionAtDecisionPoint(null);
        }
    }

    public double getCurrentAcceleration() { //ADDED BY LORENA TO TEST ACCELERATION IN THE REPORTS
        return currentAcceleration;
    }


    public double getDepthInCurrentRUnit() {
        return depthInCurrentRUnit;
    }

    private IRUnitManager getNextForDirection(IRUnitManager rUnit, VehicleState vehicleState) {
        //go through all nexts and compare their angle with the decision point node
        //choose the one that is the biggest

        VehicleDirection directionPriorToTurn = new VehicleDirection(
                rUnit.getPrevsRUnitList().get(0).getX(),
                rUnit.getPrevsRUnitList().get(0).getY(),
                rUnit.getX(),
                rUnit.getY()
        );

        IRUnitManager chosenUnit = rUnit.getNextRUnitList().get(0);
        for (IRUnitManager currentUnit : rUnit.getNextRUnitList()) {
            VehicleDirection chosenAngle = new VehicleDirection(rUnit.getX(), rUnit.getY(), chosenUnit.getX(), chosenUnit.getY());
            VehicleDirection currentAngle = new VehicleDirection(rUnit.getX(), rUnit.getY(), currentUnit.getX(), currentUnit.getY());

            //angle of last
            switch (vehicleState.getNextDirectionAtDecisionPoint()) {
                case left:
                    //to the left means going up in degrees
                    if (directionPriorToTurn.getDifference(currentAngle.getAngle()) >
                            directionPriorToTurn.getDifference(chosenAngle.getAngle()))
                        chosenUnit = currentUnit;
                    break;
                case right:
                    //to the right means going down in degrees
                    if (directionPriorToTurn.getDifference(currentAngle.getAngle()) <
                            directionPriorToTurn.getDifference(chosenAngle.getAngle()))
                        chosenUnit = currentUnit;
                    break;
                case straight:
                    //closer to 0 difference is straight
                    if (Math.abs(directionPriorToTurn.getDifference(currentAngle.getAngle())) <
                            Math.abs(directionPriorToTurn.getDifference(chosenAngle.getAngle())))
                        chosenUnit = currentUnit;
                    break;
                default:
                    break;
            }
        }

        return chosenUnit;
    }

    public static IRUnitManager chooseNext(IRUnitManager rUnit, VehicleState vehicleState) {
        if (rUnit.getNextRUnitList().size() > 0) {
            if (rUnit.getNextRUnitList().size() > 1) {
                if (vehicleState.getNextRUnitAfterDecisionPoint() != null) {
                    if (rUnit.getNextRUnitList().contains(vehicleState.getNextRUnitAfterDecisionPoint()))
                        return vehicleState.getNextRUnitAfterDecisionPoint();
                }
            } else {
                return rUnit.getNextRUnitList().get(0);
            }
        }
        return rUnit;
    }

    private double additionalDistances() {
        return depthInCurrentRUnit + objectInSpace.getLength();
    }

}
