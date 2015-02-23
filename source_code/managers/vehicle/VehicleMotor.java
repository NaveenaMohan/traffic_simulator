package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import jdk.nashorn.internal.ir.Block;
import managers.runit.*;
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

        //fetch the next object to look at
        //TODO here we are only looking at the next object and nothing after that. We might want to change that later on
        VehicleMemoryObject vehicleMemoryObject = vehicleState.NextObject();

        //initially aim for the speed limit
        currentAcceleration = aimForSpeed(maxSpeedLimit, 1);

        if(vehicleMemoryObject!=null) {

            if (vehicleMemoryObject.getObject() instanceof Blockage)//check for blockage and apply strategy
                rUnit = StrategyBlockageAhead(rUnit, vehicleMemoryObject.getDistance(), 500, vehicleState.isChangeableClear());
            else if (vehicleMemoryObject.getObject() instanceof Vehicle)//check for vehicle and apply strategy
                rUnit = StrategyVehicleAhead(rUnit, (Vehicle) vehicleMemoryObject.getObject(), vehicleMemoryObject.getDistance()
                        , 500, vehicleState.isChangeableClear());
            else if (vehicleMemoryObject.getObject() instanceof EndOfRoad)//check for end of road and apply strategy
                StrategyEndOfRoadAhead(vehicleMemoryObject.getDistance());
            else if (vehicleMemoryObject.getObject() instanceof TrafficLight)//check for traffic light and apply strategy
                StrategyTrafficLightAhead((TrafficLight)vehicleMemoryObject.getObject(), vehicleMemoryObject.getDistance(), 100);
            else if (vehicleMemoryObject.getObject() instanceof ZebraCrossing)//check for zebra crossing and apply strategy
                StrategyZebraCrossingAhead((ZebraCrossing)vehicleMemoryObject.getObject(), vehicleMemoryObject.getDistance(), 100);

            //TODO implement other strategies
        }
        return rUnit;
    }

    public IRUnitManager performAction(double timePassed, IDataAndStructures dataAndStructures,
                                       ISpaceManager spaceManager, IRUnitManager rUnit, ObjectInSpace objectInSpace)
    {
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
                if (spaceManager.checkFit(vehID, rUnit.getX(), rUnit.getY(), objectInSpace.getWidth(), objectInSpace.getLength())
                        & rUnit.getNextRUnitList().get(0).getBlockage()==null) {
                    rUnit = rUnit.getNextRUnitList().get(0);
                } else {
                    System.out.println("Collision");
                    //if you encounter an obstacle then at least you reach the end of the current RUnit
                    depthInCurrentRUnit = dataAndStructures.getGlobalConfigManager().getMetresPerRUnit();
                    //you hit it so your velocity is now 0
                    currentVelocity = 0;
                }


            }
            break;

        }

        System.out.println("rUnit: " + rUnit.getId() + " v: " + currentVelocity + " a: " + currentAcceleration + " d: " + depthInCurrentRUnit);

        return rUnit;
    }

    private IRUnitManager StrategyBlockageAhead(IRUnitManager currentRUnit
            , double distance, double safeDistance, boolean isChangeableClear) {
        //if the blockage is closer than your safe distance change lane if possible
        //System.out.println("StrategyBlockageAhead");
        if (distance < safeDistance) {
            if (isChangeableClear)//if the changeable unit is clear
            {
                return currentRUnit.getChangeAbleRUnit();
            } else {
                currentAcceleration = aimForSpeed(0, distance-depthInCurrentRUnit);
            }
        }
        return currentRUnit;
    }

    private IRUnitManager StrategyVehicleAhead(IRUnitManager currentRUnit, IVehicleManager vehicle, double distance, double safeDistance, boolean isChangeableClear) {
        //System.out.println("StrategyVehicleAhead");
        currentAcceleration = aimForSpeed(maxSpeedLimit, 1);//may be overwritten

        //if the vehicle speed is slower than yours
        if(vehicle.getCurrentVelocity() < currentVelocity)
        {
            //if the vehicle is within your safe distance
            if(distance<safeDistance)
            {
                if(isChangeableClear)
                {
                    return currentRUnit.getChangeAbleRUnit();
                }
                else
                {
                    //if there is nowhere to run, match his speed
                    currentAcceleration = aimForSpeed(vehicle.getCurrentVelocity(), distance-depthInCurrentRUnit);
                }
            }
        }
        return currentRUnit;
    }

    private void StrategyWelcomeSignAhead(IRUnitManager rUnit) {

    }

    private void StrategyDirectionSignAhead(IRUnitManager rUnit) {

    }


    private void StrategyStopSignAhead(IRUnitManager rUnit, double distance) {
        currentAcceleration = aimForSpeed(0, distance-depthInCurrentRUnit);
       // System.out.println("StrategyStopSignAhead");
    }

    private void StrategySpeedLimitSignAhead(IRUnitManager rUnit) {

    }

    private void StrategyTrafficLightAhead(TrafficLight trafficLight, double distance, double safeDistance) {
        System.out.println("StrategyTrafficLightAhead");
        if(distance<safeDistance) {
            if (!trafficLight.isGreen()) {System.out.println("TrafficLight - Slow");
                currentAcceleration = aimForSpeed(0, distance - depthInCurrentRUnit);
            }
        }
    }

    private void StrategyZebraCrossingAhead(ZebraCrossing zebraCrossing, double distance, double safeDistance) {
        System.out.println("StrategyZebraCrossingAhead");
        if(distance<safeDistance) {
            if (!zebraCrossing.getTrafficLight().isGreen())
                currentAcceleration = aimForSpeed(0, distance - depthInCurrentRUnit);
        }
    }

    private void StrategyDecisionPointAhead(IRUnitManager rUnit) {

    }

    private void StrategyEndOfRoadAhead(double distance) {
        //System.out.println("StrategyEndOfRoadAhead");
        currentAcceleration = aimForSpeed(0,distance-depthInCurrentRUnit);
    }

    private double aimForSpeed(double requiredVelocity, double distance)//returns the acceleration
    {
        //this function gradually changes the vehicle speed to match the requiredSpeed in distance metres

        //to get the acceleration we will use a kinematic equation
        //finalVelocity^2=initialVelocity^2 + 2*acceleration*distance
        //Hence acceleration=(finalVelocity^2-initialVelocity^2)/2*distance

        double acceleration = ((requiredVelocity*requiredVelocity) - (currentVelocity*currentVelocity)) / (2 * distance);

        System.out.println("----- r:"+requiredVelocity + " d: " + distance + " a: " +acceleration);

        return Math.max(Math.min(acceleration, maxAcceleration), maxDeceleration);
    }


    private void updateVelocity(double timePassed) {
        //velocity can't be below 0
        currentVelocity =  Math.max(0,
                currentVelocity + currentAcceleration * timePassed);
    }


}
