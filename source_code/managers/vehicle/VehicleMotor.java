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
    private boolean madeDestination;
    private double arrivalDestTime;
    private double depthInCurrentRUnit;//if the RUnit is say 100m long we need to keep track of how far in the vehicle is
    private int maxSpeedLimit;//the speed limit as according to the signs
    private double maximumVelocity;
    private ObjectInSpace objectInSpace;
    private String destination;
    private boolean isEmergency;
private double nextCheckTime;

    //TODO: delete this
    public String currentStrategy;

    public VehicleMotor(double maxAcceleration, double maxDeceleration, int initialSpeed, String destination
            , ObjectInSpace objectInSpace, double maximumVelocity, boolean isEmergency) {
        this.maxAcceleration = maxAcceleration;
        this.maxDeceleration = maxDeceleration;
        this.currentVelocity = initialSpeed;
        this.currentAcceleration = 0;
        this.depthInCurrentRUnit = 0;
        maxSpeedLimit = initialSpeed;
        this.destination = destination;
        this.objectInSpace = objectInSpace;
        this.isEmergency = isEmergency;
        this.maximumVelocity = maximumVelocity;
        this.nextCheckTime=0;
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
            , double slipperinessOffset, ISpaceManager spaceManager, double time) {

        currentStrategy = "=";

        if(nextCheckTime==0 || nextCheckTime<time) {
            nextCheckTime=time+4;
            //choose your lane
            rUnit = chooseLane(rUnit, vehicleState, spaceManager, driver.getSpeed(slipperinessOffset, (isEmergency ? maximumVelocity : maxSpeedLimit)));
        }

        //get the slowest and next objects within x metres
        VehicleMemoryObject slowestObject = vehicleState.getSlowestWithin(200, isEmergency, rUnit.isLeft());
        VehicleMemoryObject nextObject = vehicleState.nextObjectWithin(200, isEmergency, rUnit.isLeft());

        //control speed and distance based on the situation ahead
        ControlSpeedAndDistance(nextObject, slowestObject, driver, slipperinessOffset);

        //replan direction
        if(nextObject!=null)
            replan(nextObject,vehicleState);

        return rUnit;
    }

    public boolean isMadeDestination() {
        return madeDestination;
    }

    public double getArrivalDestTime() {
        return arrivalDestTime;
    }

    private IRUnitManager chooseLane(IRUnitManager rUnit, VehicleState vehicleState, ISpaceManager spaceManager, double desiredSpeed)
    {
        IRUnitManager temp = rUnit;



        if(temp.isLeft())
        {
            VehicleMemoryObject vehicleInLeft = vehicleState.getNextVehicleObject(20,true);
            VehicleMemoryObject vehicleInRight = vehicleState.getNextVehicleObject(20,false);
            VehicleMemoryObject speedAffectingRoadElement = (isEmergency ? null :vehicleState.getNextSpeedAffectingRoadElement(100,true));

            currentStrategy+="LHS("+(vehicleInLeft==null ? "null)" : Common.round(vehicleInLeft.getVelocity(), 2)+"ms,"+Common.round(vehicleInLeft.getDistance(), 2)+"m) ") +
                    "RHS("+(vehicleInRight==null ? "null)" :Common.round(vehicleInRight.getVelocity(), 2) + "ms," + Common.round(vehicleInRight.getDistance(), 2) + "m) ") +
                    "Sp(" + (speedAffectingRoadElement==null ? "null)" :Common.round(speedAffectingRoadElement.getVelocity(), 2) + "ms," + Common.round(speedAffectingRoadElement.getDistance(), 2) + "m)");

            double achievableSpeed = Math.min(
                    (speedAffectingRoadElement != null ? speedAffectingRoadElement.getVelocity() : desiredSpeed),
                    desiredSpeed
            );

            currentStrategy+=" achievableSpeed["+(speedAffectingRoadElement != null ? speedAffectingRoadElement.getVelocity() : desiredSpeed)+"" +
                    ","+desiredSpeed+"]=" + achievableSpeed;

            currentStrategy+=" [isLeft]";
            if(vehicleInLeft!=null)
            {currentStrategy+=" [vehicleInLeft!=null]";
                if(vehicleInLeft.getVelocity()<achievableSpeed)
                {currentStrategy+=" [vehicleInLeft.getVelocity()<achievableSpeed]";
                    if(VehiclePerception.isChangeableClear(temp,spaceManager,objectInSpace, 10, 15)) {
                        currentStrategy+=" [fits]";
                        if (vehicleInRight == null) {
                            currentStrategy+=" [vehicleInRight == null] CHANGING TO RIGHT";
                            temp = rUnit.getChangeAbleRUnit();
                        }
                        else {
                            if (vehicleInRight.getVelocity() > vehicleInLeft.getVelocity()) {
                                currentStrategy+=" [vehicleInRight.getVelocity() > vehicleInLeft.getVelocity()] CHANGING TO RIGHT";
                                temp = rUnit.getChangeAbleRUnit();
                            }
                        }
                    }
                }
            }
        }
        else {
            VehicleMemoryObject vehicleInLeft = vehicleState.getNextVehicleObject(100,true);
            currentStrategy+=" [isRight]";
            if(vehicleInLeft==null)
            {
                if(VehiclePerception.isChangeableClear(temp,spaceManager,objectInSpace, 30, 5)) {
                    currentStrategy += " [vehicleInLeft==null] CHANGING TO LEFT";
                    temp = temp.getChangeAbleRUnit();
                }
            }
        }

        currentStrategy="";
        return temp;

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

            //advance
            rUnit = chooseNext(rUnit, vehicleState);

            //see and process the objects we have just passed
            processObjectPassed(rUnit, vehicleState, dataAndStructures.getGlobalConfigManager().getCurrentSecond());
        }

        //adjust your position in space
        objectInSpace.setX(rUnit.getX());
        objectInSpace.setY(rUnit.getY());
        if (previousrUnit.getId() != rUnit.getId())
            objectInSpace.setDirection(new VehicleDirection(
                    Common.getNthPrevRUnit(previousrUnit, 1).getX(),
                    Common.getNthPrevRUnit(previousrUnit, 1).getY(),
                    Common.getNthNextRUnit(rUnit, 3).getX(),
                    Common.getNthNextRUnit(rUnit, 3).getY()));

        return rUnit;
    }


    private void replan(VehicleMemoryObject nextObject, VehicleState vehicleState) {
        //the only plan the the vehicle can currently have is to choose it's next turn if it has a set destination

        //if you have a destination
        if (!destination.isEmpty()) {
            currentStrategy+="[dest not empty]";
            //if the next object is a direction sign
            if (nextObject.getObject() instanceof DirectionSign) {
                currentStrategy+="[NextObj("+((DirectionSign) nextObject.getObject()).getLocation()+")vs("+destination+")="+((DirectionSign) nextObject.getObject()).getLocation().equals(destination)+"]";
                //if the destination on the sign matches your destination
                if (((DirectionSign) nextObject.getObject()).getLocation().equals(destination)) {
                    //set your next direction to the one from the sign
                    vehicleState.setNextDirectionAtDecisionPoint(((DirectionSign) nextObject.getObject()).getDirectionSignType());
                    currentStrategy+="[SAME DIRECTION. Next direction: " + vehicleState.getNextDirectionAtDecisionPoint()+"]";
                }
            }

            //if next object is a decision point
            if (nextObject.getObject() instanceof RoadDecisionPoint) {
                //if you have next direction set
                if (vehicleState.getNextDirectionAtDecisionPoint() != null) {
                    //set your nextRUnit
                    vehicleState.setNextRUnitAfterDecisionPoint(getNextForDirection(nextObject.getrUnit(), vehicleState));
                }
            }
        }
    }

    private void ControlSpeedAndDistance(VehicleMemoryObject nextObject, VehicleMemoryObject slowestObject,
                                Driver driver, double slipperinessOffset) {
        //this function forms a response to a moving or stationary object

        if(nextObject==null)
        {
            currentAcceleration = aimForSpeed(
                    driver.getSpeed(slipperinessOffset, (isEmergency ? maximumVelocity : maxSpeedLimit)),
                    0,//safe stop distance
                    1//distance to
            );
        }
        else {
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

            currentStrategy += " next(" + nextObject.getObject() + "," + nextObject.getDistance() + "m," + decelerationDistanceToNextObject +
                    "dd," + nextObject.getVelocity()
                    + "ms) vs";
            currentStrategy += " slowest(" + slowestObject.getObject() + "," + slowestObject.getDistance() + "m," + decelerationDistanceToSlowestObject +
                    "dd," + "m," + slowestObject.getVelocity()
                    + "ms)";
            //focus on the object which speed you need to match sooner
            VehicleMemoryObject objectToMatch = (decelerationDistanceToNextObject > decelerationDistanceToSlowestObject ?
                    slowestObject : nextObject);

//            currentStrategy += "=" + objectToMatch.getObject();
            double speedToMatch = (isEmergency ? maximumVelocity : maxSpeedLimit);

            //if you are within the distance to start slowing down
            if (objectToMatch.getDistance() <= driver.getDecelerationSafeDistance(
                    currentVelocity,
                    objectToMatch.getVelocity(),
                    objectToMatch.getDistance() - additionalDistances(),
                    slipperinessOffset,
                    objectToMatch)) {
//                currentStrategy += "[within sdd]";

                speedToMatch = Math.min((isEmergency ? maximumVelocity : maxSpeedLimit), objectToMatch.getVelocity());

            }


            //aim to match the object speed
            currentAcceleration = aimForSpeed(
                    driver.getSpeed(slipperinessOffset, speedToMatch),
                    driver.getStopDistance(slipperinessOffset, objectToMatch),
                    objectToMatch.getDistance() - (objectToMatch.isPassable() ? 0 : additionalDistances()));

            currentStrategy="";
        }
    }


    private double aimForSpeed(double requiredVelocity, double safeStopDistance, double distance)//returns the acceleration
    {
        //this function gradually changes the vehicle speed to match the requiredSpeed in distance metres

        //to get the acceleration we will use a kinematic equation
        //finalVelocity^2=initialVelocity^2 + 2*acceleration*distance
        //Hence acceleration=(finalVelocity^2-initialVelocity^2)/2*distance

        double acceleration;

        //if the object is very isFrameClosed drop down your acceleration to -100 in order to come to a full stop

        if (requiredVelocity < currentVelocity & distance < safeStopDistance)
            acceleration = maxDeceleration;
        else
            acceleration = ((requiredVelocity * requiredVelocity) - (currentVelocity * currentVelocity)) /
                    (2 * ((distance - safeStopDistance) == 0 ? 1 : (distance - safeStopDistance)));

        return Math.max(Math.min(acceleration, maxAcceleration), maxDeceleration);
    }


    private void updateVelocity(double timePassed) {
        //velocity can't be below 0
        currentVelocity = Math.min(maximumVelocity, Math.max(0,
                currentVelocity + currentAcceleration * timePassed));
    }

    private void processObjectPassed(IRUnitManager rUnit, VehicleState vehicleState, double currentTime) {

        Object obj = VehiclePerception.getObjectForDoubleLane(rUnit);
        //if you have just passed a speed sign update your maxSpeedLimit
        if (obj instanceof SpeedLimitSign) {
            maxSpeedLimit = (((SpeedLimitSign) obj).getSpeedLimit() * 1000) / 3600;//convert km/h to m/s
        }

        //if you have just passed end of road - disappear
        if (!(rUnit.getNextRUnitList().size() > 0)) {
            objectInSpace.setVisible(false);
        }

        //if you have just passed a decision Point
        if (obj instanceof RoadDecisionPoint) {
            vehicleState.setNextRUnitAfterDecisionPoint(null);
            vehicleState.setNextDirectionAtDecisionPoint(null);
        }

        //if you have just passed your destination
        if (obj instanceof WelcomeSign) {
            if(((WelcomeSign)obj).getLocation().equals(destination)) {
                objectInSpace.setVisible(false);
                madeDestination=true;
                arrivalDestTime=currentTime;
            }
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
                //return a random direction
                return rUnit.getNextRUnitList().get(Common.randIntegerBetween(0, rUnit.getNextRUnitList().size() - 1));
            }
        }
        return rUnit;
    }

    private double additionalDistances() {
        return depthInCurrentRUnit + objectInSpace.getLength();
    }

    public double getMaximumVelocity() {
        return maximumVelocity;
    }
}
