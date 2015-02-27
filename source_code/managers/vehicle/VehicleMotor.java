package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import jdk.nashorn.internal.ir.Block;
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
    private int vehID;
    public String currentStrategy;

    public VehicleMotor(double maxAcceleration, double maxDeceleration, int maxSpeedLimit, int vehID) {
        this.maxAcceleration = maxAcceleration;
        this.maxDeceleration = maxDeceleration;
        this.currentVelocity = 0;
        this.currentAcceleration = 0;
        this.depthInCurrentRUnit = 0;
        this.maxSpeedLimit = maxSpeedLimit;
        this.vehID = vehID;
    }

    public double getCurrentVelocity() {
        return currentVelocity;
    }

    public IRUnitManager PrepareAction(IRUnitManager rUnit, Driver driver, VehicleState vehicleState
            , double timePassed) {
        //this function applies various strategies depending on the types of objects ahead
        //it changes vehicle acceleration and returns proposed vehicle position

        //get the slowest object within x metres
        VehicleMemoryObject vehicleMemoryObject = vehicleState.getSlowestWithin(200);
        ;

        //initially aim for the speed limit
        currentAcceleration = aimForSpeed(maxSpeedLimit, 1);

        if (vehicleMemoryObject != null) {


            int safeDistance = 0;
            if (vehicleMemoryObject.getObject() instanceof Blockage)//check for blockage and apply strategy
                rUnit = StrategyBlockageAhead(rUnit, vehicleMemoryObject, vehicleState.isChangeableClear());
            else if (vehicleMemoryObject.getObject() instanceof Vehicle)//check for vehicle and apply strategy
                rUnit = StrategyVehicleAhead(rUnit, vehicleMemoryObject
                        , vehicleState.isChangeableClear());
            else if (vehicleMemoryObject.getObject() instanceof EndOfRoad)//check for end of road and apply strategy
                StrategyEndOfRoadAhead(vehicleMemoryObject);
            else if (vehicleMemoryObject.getObject() instanceof TrafficLight)//check for traffic light and apply strategy
                StrategyTrafficLightAhead(vehicleMemoryObject);
            else if (vehicleMemoryObject.getObject() instanceof ZebraCrossing)//check for zebra crossing and apply strategy
                StrategyZebraCrossingAhead(vehicleMemoryObject);
            else if (vehicleMemoryObject.getObject() instanceof SpeedLimitSign)//check for traffic sign and apply strategy
                StrategySpeedLimitSignAhead(vehicleMemoryObject);
            else if (vehicleMemoryObject.getObject() instanceof StopSign)//check for traffic sign and apply strategy
                StrategyStopSignAhead(vehicleMemoryObject);

            //TODO implement other strategies
        }
        return rUnit;
    }

    public IRUnitManager performAction(double timePassed, IDataAndStructures dataAndStructures,
                                       ISpaceManager spaceManager, IRUnitManager rUnit, ObjectInSpace objectInSpace) {
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
                // if (spaceManager.checkFit(vehID, rUnit.getX(), rUnit.getY(), objectInSpace.getWidth(), objectInSpace.getLength())
                if (rUnit.getNextRUnitList().get(0).getBlockage() == null) {

                    //see and process the objects we have just passed
                    processObjectPassed(VehiclePerception.getObjectAtRUnit(rUnit));

                    //advance
                    rUnit = rUnit.getNextRUnitList().get(0);



//                } else {
//                    //System.out.println("Collision");
//                    //if you encounter an obstacle then at least you reach the end of the current RUnit
//                    depthInCurrentRUnit = dataAndStructures.getGlobalConfigManager().getMetresPerRUnit();
//                    //you hit it so your velocity is now 0
//                    currentVelocity = 0;
                }


            }
            break;

        }

        //System.out.println("rUnit: " + rUnit.getId() + " v: " + currentVelocity + " a: " + currentAcceleration + " d: " + depthInCurrentRUnit);

        return rUnit;
    }

    private IRUnitManager StrategyBlockageAhead(IRUnitManager currentRUnit, VehicleMemoryObject obj, boolean isChangeableClear) {
        //if the blockage is closer than your safe distance change lane if possible
        //System.out.println("StrategyBlockageAhead");
        currentStrategy = "StrategyBlockageAhead";
        if (obj.getDistance() <= getDecelerationStartDistance(currentVelocity, obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit)) {
            if (isChangeableClear)//if the changeable unit is clear
            {
                return currentRUnit.getChangeAbleRUnit();
            } else {
                currentStrategy += " matching (0) d: " + (obj.getDistance() - depthInCurrentRUnit);
                currentAcceleration = aimForSpeed(0, obj.getDistance() - depthInCurrentRUnit);
            }
        }
        return currentRUnit;
    }

    private IRUnitManager StrategyVehicleAhead(IRUnitManager currentRUnit, VehicleMemoryObject obj
            , boolean isChangeableClear) {
        currentStrategy = "StrategyVehicleAhead travelling at: " + obj.getVelocity() + " d: " + (obj.getDistance() - depthInCurrentRUnit);
        currentAcceleration = aimForSpeed(maxSpeedLimit, 1);//may be overwritten

        //if the vehicle speed is slower than yours
        if (obj.getVelocity() < currentVelocity) {
            //if the vehicle is within your safe distance
            if (obj.getDistance() <= getDecelerationStartDistance(currentVelocity, obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit - 5)) {
                if (isChangeableClear) {
                    currentStrategy += " changeable Clear";
                    return currentRUnit.getChangeAbleRUnit();
                } else {
                    //if there is nowhere to run, match his speed
                    currentStrategy += " matching (" + obj.getVelocity() + ") ";
                    currentAcceleration = aimForSpeed(obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit);
                }
            } else
                currentStrategy += "not matching d: " + obj.getDistance() + " sd: " + getDecelerationStartDistance(currentVelocity, obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit - 5);
        }
        return currentRUnit;
    }

    private void StrategyWelcomeSignAhead() {

    }

    private void StrategyDirectionSignAhead() {

    }


    private void StrategyStopSignAhead(VehicleMemoryObject obj) {
        currentStrategy = "StrategyStopSignAhead";
        currentStrategy += " matching (" + obj.getVelocity() + ") d: " + (obj.getDistance() - depthInCurrentRUnit);
        currentAcceleration = aimForSpeed(obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit);
        // System.out.println("StrategyStopSignAhead");
    }

    private void StrategySpeedLimitSignAhead(VehicleMemoryObject obj) {
        currentStrategy = "StrategySpeedLimitSignAhead for: " + obj.getVelocity() + " d: " + (obj.getDistance() - depthInCurrentRUnit);
        currentAcceleration = aimForSpeed(maxSpeedLimit, 1);//may be overwritten

        //if the vehicle speed is slower than yours
        if (obj.getVelocity() < currentVelocity) {
            //if the vehicle is within your safe distance
            if (obj.getDistance() <= getDecelerationStartDistance(currentVelocity, obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit - 5)) {

                //if there is nowhere to run, match his speed
                currentStrategy += " matching (" + obj.getVelocity() + ") ";
                currentAcceleration = aimForSpeed(obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit);
            } else
                currentStrategy += "not matching d: " + obj.getDistance() + " sd: " + getDecelerationStartDistance(currentVelocity, obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit - 5);
        }
    }

    private void StrategyTrafficLightAhead(VehicleMemoryObject obj) {
        currentStrategy = "StrategyTrafficLightAhead";
        if (obj.getDistance() < getDecelerationStartDistance(currentVelocity, obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit)) {
            if (!((TrafficLight) obj.getObject()).isGreen()) {
                currentStrategy += " matching (" + obj.getVelocity() + ") d: " + (obj.getDistance() - depthInCurrentRUnit);
                currentAcceleration = aimForSpeed(obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit);
            }

        }
    }

    private void StrategyZebraCrossingAhead(VehicleMemoryObject obj) {
        currentStrategy = "StrategyZebraCrossingAhead";
        if (obj.getDistance() < getDecelerationStartDistance(currentVelocity, obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit)) {
            if (!((ZebraCrossing) obj.getObject()).getTrafficLight().isGreen()) {
                currentStrategy += " matching (" + obj.getVelocity() + ") d: " + (obj.getDistance() - depthInCurrentRUnit);
                currentAcceleration = aimForSpeed(obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit);
            }
        }
    }

    private void StrategyDecisionPointAhead(IRUnitManager rUnit) {

    }

    private void StrategyEndOfRoadAhead(VehicleMemoryObject obj) {
        currentStrategy = "StrategyEndOfRoadAhead";
        //System.out.println("StrategyEndOfRoadAhead");
        currentStrategy += " matching (" + obj.getVelocity() + ") d: " + (obj.getDistance() - depthInCurrentRUnit);
        currentAcceleration = aimForSpeed(obj.getVelocity(), obj.getDistance() - depthInCurrentRUnit);
    }

    private double aimForSpeed(double requiredVelocity, double distance)//returns the acceleration
    {
        //this function gradually changes the vehicle speed to match the requiredSpeed in distance metres

        //to get the acceleration we will use a kinematic equation
        //finalVelocity^2=initialVelocity^2 + 2*acceleration*distance
        //Hence acceleration=(finalVelocity^2-initialVelocity^2)/2*distance

        double acceleration;

//        if(distance<0)
//            throw new IllegalArgumentException(vehID + " distance is " + distance);

        //if the object is very close drop down your acceleration to -100 in order to come to a full stop
        if (requiredVelocity < currentVelocity & distance < 20)
            acceleration = -100;
        else
            acceleration = ((requiredVelocity * requiredVelocity) - (currentVelocity * currentVelocity)) / (2 * distance);
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

    private double getDecelerationStartDistance(double currentVelocity, double requiredVelocity, double distance) {
        //the distance by which the car should start decelerating before an obstacle

        return Math.max((currentVelocity - requiredVelocity), distance) + 50;
    }

    public double getDepthInCurrentRUnit() {
        return depthInCurrentRUnit;
    }
}
