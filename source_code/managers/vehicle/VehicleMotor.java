package managers.vehicle;

import common.Common;
import dataAndStructures.IDataAndStructures;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.*;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.space.VehicleDirection;

import java.io.Serializable;

/**
 * Created by Fabians on 18/02/2015.
 */
public class VehicleMotor implements Serializable {
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
        this.nextCheckTime = 0;
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

    public IRUnitManager prepareAction(IRUnitManager rUnit, Driver driver, VehicleState vehicleState
            , double slipperinessOffset, ISpaceManager spaceManager, double time) {
        if (nextCheckTime == 0 || nextCheckTime < time) {
            nextCheckTime = time + 2;
            //choose your lane
            rUnit = chooseLane(rUnit, vehicleState, spaceManager, driver.getSpeed(slipperinessOffset, (isEmergency ? maximumVelocity : maxSpeedLimit)));
        }

        //get the slowest and next objects within x metres
        VehicleMemoryObject slowestObject = vehicleState.getSlowestWithin(200, isEmergency, rUnit.isLeft());
        VehicleMemoryObject nextObject = vehicleState.nextObjectWithin(200, isEmergency, rUnit.isLeft());

        //control speed and distance based on the situation ahead
        ControlSpeedAndDistance(nextObject, slowestObject, driver, slipperinessOffset);

        //replan direction
        if (nextObject != null)
            replan(nextObject, vehicleState);

        return rUnit;
    }

    public boolean isMadeDestination() {
        return madeDestination;
    }

    public double getArrivalDestTime() {
        return arrivalDestTime;
    }

    private IRUnitManager chooseLane(IRUnitManager rUnit, VehicleState vehicleState, ISpaceManager spaceManager, double desiredSpeed) {
        IRUnitManager temp = rUnit;

        if (temp.isLeft()) {

            //if there are no direction instructions change lanes according to your speed
            if (vehicleState.getNextDirectionAtDecisionPoint() == null) {
                VehicleMemoryObject vehicleInLeft = vehicleState.getNextVehicleObject(20, true);
                VehicleMemoryObject vehicleInRight = vehicleState.getNextVehicleObject(20, false);
                VehicleMemoryObject speedAffectingRoadElement =
                        (isEmergency ? null : vehicleState.getNextSpeedAffectingRoadElement(100, true));

                double achievableSpeed = Math.min(
                        (speedAffectingRoadElement != null ? speedAffectingRoadElement.getVelocity() : desiredSpeed),
                        desiredSpeed
                );


                if (vehicleInLeft != null) {
                    if (vehicleInLeft.getVelocity() < achievableSpeed) {
                        if (VehiclePerception.isChangeableClear(temp, spaceManager, objectInSpace, 10, 15)) {
                            if (vehicleInRight == null) {
                                temp = rUnit.getChangeAbleRUnit();
                            } else {
                                if (vehicleInRight.getVelocity() > vehicleInLeft.getVelocity()) {
                                    temp = rUnit.getChangeAbleRUnit();
                                }
                            }
                        }
                    }
                }
            } else {
                //if there are direction instructions change lanes accordingly
                switch (vehicleState.getNextDirectionAtDecisionPoint()) {
                    case left:
                        //stay in your lane
                        break;
                    case right:
                        //go to the right
                        if (VehiclePerception.isChangeableClear(temp, spaceManager, objectInSpace, 10, 15)) {
                            temp = rUnit.getChangeAbleRUnit();
                        }
                        break;
                    case straight:
                        //stay in your lane
                        break;
                }
            }


        } else {//if you are in the right lane
            //if there are no direction instructions change lanes according to your speed
            if (vehicleState.getNextDirectionAtDecisionPoint() == null) {
                VehicleMemoryObject vehicleInLeft = vehicleState.getNextVehicleObject(100, true);
                if (vehicleInLeft == null) {
                    if (VehiclePerception.isChangeableClear(temp, spaceManager, objectInSpace, 40, 10)) {
                        temp = temp.getChangeAbleRUnit();
                    }
                }
            } else {//if there are direction signs to worry about
                //if there are direction instructions change lanes accordingly
                switch (vehicleState.getNextDirectionAtDecisionPoint()) {
                    case left:
                        //go to the left lane
                        if (VehiclePerception.isChangeableClear(temp, spaceManager, objectInSpace, 10, 15)) {
                            temp = rUnit.getChangeAbleRUnit();
                        }
                        break;
                    case right:
                        //stay in your lane
                        break;
                    case straight:
                        //stay in your lane
                        break;
                }
            }
        }


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
            rUnit = processObjectPassed(rUnit, vehicleState, dataAndStructures.getGlobalConfigManager().getCurrentSecond());
        }

        //adjust your position in space
        objectInSpace.setX(rUnit.getX());
        objectInSpace.setY(rUnit.getY());
        if (previousrUnit.getId() != rUnit.getId())
            objectInSpace.setDirection(new VehicleDirection(
                    Common.getNthPrevRUnit(previousrUnit, 5).getX(),
                    Common.getNthPrevRUnit(previousrUnit, 5).getY(),
                    Common.getNthNextRUnit(rUnit, 5).getX(),
                    Common.getNthNextRUnit(rUnit, 5).getY()));

        return rUnit;
    }


    private void replan(VehicleMemoryObject nextObject, VehicleState vehicleState) {
        //the only plan the the vehicle can currently have is to choose it's next turn if it has a set destination
        //if you have a destination
        if (!destination.isEmpty()) {
            //if the next object is a direction sign
            if (nextObject.getObject() instanceof DirectionSign) {
                //if the destination on the sign matches your destination
                if (((DirectionSign) nextObject.getObject()).getLocation().equals(destination)) {
                    //set your next direction to the one from the sign
                    vehicleState.setNextDirectionAtDecisionPoint(((DirectionSign) nextObject.getObject()).getDirectionSignType());
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

        if (nextObject == null) {
            currentAcceleration = aimForSpeed(
                    driver.getSpeed(slipperinessOffset, (isEmergency ? maximumVelocity : maxSpeedLimit)),
                    0,//safe stop distance
                    1//distance to
            );
        } else {
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

            double speedToMatch = (isEmergency ? maximumVelocity : maxSpeedLimit);

            //if you are within the distance to start slowing down
            if (objectToMatch.getDistance() <= driver.getDecelerationSafeDistance(
                    currentVelocity,
                    objectToMatch.getVelocity(),
                    objectToMatch.getDistance() - additionalDistances(),
                    slipperinessOffset,
                    objectToMatch)) {
                speedToMatch = Math.min((isEmergency ? maximumVelocity : maxSpeedLimit), objectToMatch.getVelocity());

            }


            //aim to match the object speed
            currentAcceleration = aimForSpeed(
                    driver.getSpeed(slipperinessOffset, speedToMatch),
                    driver.getStopDistance(slipperinessOffset, objectToMatch),
                    objectToMatch.getDistance() - (objectToMatch.isPassable() ? 0 : additionalDistances()));

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

    private IRUnitManager getPreviousIntersection(IRUnitManager rUnit, int backwardsMagnitude)
    {
        //this function looks backwardsMagnitude steps ahead and returns true if there was an intersection
        IRUnitManager temp = rUnit.getPrevsRUnitList().get(0);
        for(int i=0; i<backwardsMagnitude; i++)
        {
            //if this was an intersection
            if(temp.getNextRUnitList().size()>1)
                return temp;
            if(temp.getChangeAbleRUnit()!=null && temp.getChangeAbleRUnit().getNextRUnitList().size()>1)
                return temp.getChangeAbleRUnit();
            if(temp.getPrevsRUnitList().size() > 0)
            {
                temp = temp.getPrevsRUnitList().get(0);
            }
        }
        return null;
    }
    private IRUnitManager retakeIntersection(IRUnitManager intersectedRUnit, IRUnitManager currentRUnit)
    {
        //this function looks at the intersection and returns the exit that does not lead to currentRUnit
        for(IRUnitManager exit : intersectedRUnit.getNextRUnitList())
        {
            if(RoadNetworkManager.checkForRoadDensityCollisions(exit, currentRUnit))
                return exit;
        }

        //if no other rUnit found return the one that you came in with
        return currentRUnit;
    }
    private IRUnitManager processObjectPassed(IRUnitManager rUnit, VehicleState vehicleState, double currentTime) {
        Object obj = VehiclePerception.getObjectForDoubleLane(rUnit);
        //if you have just passed a speed sign update your maxSpeedLimit
        if (obj instanceof SpeedLimitSign) {
            maxSpeedLimit = (((SpeedLimitSign) obj).getSpeedLimit() * 1000) / 3600;//convert km/h to m/s
        }

        //if you have just passed end of road - disappear
        if (!(rUnit.getNextRUnitList().size() > 0)) {
            //a workaround for intersection disappearing cars
            //get the last intersection within 10 rUnits
            IRUnitManager lastIntersection = getPreviousIntersection(rUnit, 20);
            //if there was an intersection
            if(lastIntersection!=null)
                return retakeIntersection(lastIntersection, rUnit);
            else
            objectInSpace.setVisible(false);
        }

        //if you have just passed a decision Point

        if ((rUnit.getPrevsRUnitList().get(0).getNextRUnitList().size() > 1)) {
            vehicleState.setNextRUnitAfterDecisionPoint(null);
            vehicleState.setNextDirectionAtDecisionPoint(null);
        }


        //if you have just passed your destination
        if (obj instanceof WelcomeSign) {
            if (((WelcomeSign) obj).getLocation().equals(destination)) {
                objectInSpace.setVisible(false);
                madeDestination = true;
                arrivalDestTime = currentTime;
            }
        }

        return rUnit;
    }

    public double getCurrentAcceleration() { //ADDED BY LORENA TO TEST ACCELERATION IN THE REPORTS
        return currentAcceleration;
    }


    public double getDepthInCurrentRUnit() {
        return depthInCurrentRUnit;
    }

    private IRUnitManager getNextForDirection(IRUnitManager rUnit, VehicleState vehicleState) {
        /*
        This function chooses the nextRUnit at an intersection if you have a direction set
         */
        //go through all nexts and compare their angle with the decision point node
        //choose the one that is the biggest
        VehicleDirection directionPriorToTurn = new VehicleDirection(
                Common.getNthPrevRUnit(rUnit, 5).getX(),
                Common.getNthPrevRUnit(rUnit, 5).getY(),
                rUnit.getX(),
                rUnit.getY()
        );

//        System.out.println("{directionPriorToTurn("+directionPriorToTurn.getAngle() + ")]");
//        System.out.println("rUnit:"+rUnit.getId());
        IRUnitManager chosenUnit = rUnit.getNextRUnitList().get(0);
        for (IRUnitManager currentUnit : rUnit.getNextRUnitList()) {


            double chosenAngle = Common.getRoadForwardDirection(chosenUnit, 10);
            double currentAngle = Common.getRoadForwardDirection(currentUnit, 10);
//            System.out.println("chosenAngle:"+chosenAngle + " id: "+ chosenUnit.getId() + " x1: " + chosenUnit.getX()+ " y1: " + chosenUnit.getY() +
//                    " id:" + Common.getNthNextRUnit(chosenUnit, 10).getId() + " x2: " +Common.getNthNextRUnit(chosenUnit, 10).getX() + " y2:" + Common.getNthNextRUnit(chosenUnit, 10).getY());
//            System.out.println("currentAngle:"+currentAngle + " id: "+ currentUnit.getId() + " x1: " + currentUnit.getX()+ " y1: " + currentUnit.getY() +
//                    " id:" + Common.getNthNextRUnit(currentUnit, 10).getId() + " x2: " +Common.getNthNextRUnit(currentUnit, 10).getX() + " y2:" + Common.getNthNextRUnit(currentUnit, 10).getY());         //angle of last
            switch (vehicleState.getNextDirectionAtDecisionPoint()) {
                case left:

                    //to the left means going up in degrees
                    if (directionPriorToTurn.getDifference(currentAngle) <
                            directionPriorToTurn.getDifference(chosenAngle))
                        chosenUnit = currentUnit;
                    break;
                case right:
                    //to the right means going down in degrees
                    if (directionPriorToTurn.getDifference(currentAngle) >
                            directionPriorToTurn.getDifference(chosenAngle))
                        chosenUnit = currentUnit;
                    break;
                case straight:
                    //closer to 0 difference is straight
                    if (Math.abs(directionPriorToTurn.getDifference(currentAngle)) <=
                            Math.abs(directionPriorToTurn.getDifference(chosenAngle)))
                        chosenUnit = currentUnit;
                    break;
                default:
                    break;
            }
        }

        return chosenUnit;
    }

    public static IRUnitManager chooseNext(IRUnitManager rUnit, VehicleState vehicleState) {
        /*
        This function chooses the nextRUnit in the intersection
         */

        IRUnitManager temp = rUnit;
        if (rUnit.getNextRUnitList().size() > 0) {
            temp = rUnit.getNextRUnitList().get(Common.randIntegerBetween(0, rUnit.getNextRUnitList().size() - 1));
            if (rUnit.getNextRUnitList().size() > 1) {
                if (vehicleState.getNextRUnitAfterDecisionPoint() != null) {
                    if (rUnit.getNextRUnitList().contains(vehicleState.getNextRUnitAfterDecisionPoint())) {
                        temp = vehicleState.getNextRUnitAfterDecisionPoint();
                    }
                }
            }
        }
        return temp;
    }

    private double additionalDistances() {
        return depthInCurrentRUnit + objectInSpace.getLength();
    }

    public double getMaximumVelocity() {
        return maximumVelocity;
    }
}
