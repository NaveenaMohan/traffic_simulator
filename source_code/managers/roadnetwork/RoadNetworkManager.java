package managers.roadnetwork;

import common.Common;
import managers.runit.*;
import ui.Coordinates;

import java.util.*;

/**
 * Created by Guera_000 on 12/02/2015.
 */
public class RoadNetworkManager implements IRoadNetworkManager {

    private RoadNetwork roadNetwork;

    private int rUnitId = 0;

    public RoadNetworkManager(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }


    @Override
    public RUnit addSingleLane(int x, int y, RUnit prevRUnit) {
        //Populate Current RUnit
        RUnit currentRUnit = getNext(x, y, prevRUnit, false);
        if (!roadNetwork.getrUnitHashtable().contains(currentRUnit)) {
            roadNetwork.getrUnitHashtable().put(String.valueOf(rUnitId), currentRUnit);
            manageIntersections(currentRUnit);
        }



        return currentRUnit;
    }

    private RUnit checkIntersectionAndReturnIntersectedRUnit(Collection<RUnit> rUnits, Coordinates intersectionCoordinates, String id) {

        for (RUnit rUnit : rUnits) {
            if (rUnit.getX() == intersectionCoordinates.getX() & rUnit.getY() == intersectionCoordinates.getY() & rUnit.getId()!=id) {
                // System.out.println("INTERSECTION " + rUnit.getX() + " " + rUnit.getY() + " old: " + rUnit.getId());
                return rUnit;
            }
        }
        return null;
    }

    public static boolean checkForRoadDensityCollisions(RUnit currentRUnit, RUnit prevRUnit) {
        /*
        This function rejects an intersection if it was created by accidental mouse drags.
        It does that by checking if currentRUnit and prevRUnit are within x of ID distance from each other
         */

        int collisionDistance = 30;

        int currentID = Integer.parseInt(currentRUnit.getId().replace("c", ""));
        int previousID = Integer.parseInt(prevRUnit.getId().replace("c", ""));

        if (Math.abs(currentID - previousID) < collisionDistance)
            return false;
        else
            return true;
    }

    public static boolean checkIntersectionIsLawful(RUnit currentRUnit, RUnit prevRUnit) {
        /*
        This function checks whether creating an intersection is with accordance to UK road rules
        -if the intersected road is going right you can intersect it if
            -you are either a single lane or right lane AND
            -it is either a single lane or right lane

        -if a road is going left you can intersect it if
            -you are a single or left lane AND
            -it is a single or left lane
         */

        boolean canIntersect = false;
        //check if the road is going right
        double difference = Common.getAngleDifference(
                Common.getRoadBackwardDirection(prevRUnit),
                Common.getRoadBackwardDirection(currentRUnit)
        );

//        System.out.println("(" + Common.getNthPrevRUnit(prevRUnit, 5).getX() + "," + Common.getNthPrevRUnit(prevRUnit, 5).getY() +
//                " . " + prevRUnit.getX() + "," + prevRUnit.getY() + ") " +
//                Common.getRoadBackwardDirection(prevRUnit) + "+" +
//                "(" + Common.getNthPrevRUnit(currentRUnit, 5).getX() + "," + Common.getNthPrevRUnit(currentRUnit, 5).getY() + " . " +
//                " . " + currentRUnit.getX() + "," + currentRUnit.getY() + ") " +
//                Common.getRoadBackwardDirection(currentRUnit) + "=" +
//                difference);
        boolean isCurrentSingleLane = (currentRUnit.getChangeAbleRUnit() == null ? true : false);
        boolean isPrevSingleLane = (prevRUnit.getChangeAbleRUnit() == null ? true : false);
        //if difference is positive it is going left
        if (difference >= 0) {

            boolean isPrevLeftLane = (prevRUnit.isLeft());
            boolean isCurrentLeftLane = (currentRUnit.isLeft());
            System.out.println(isPrevSingleLane + " " + isPrevLeftLane+ " " +isCurrentSingleLane + " " +isCurrentLeftLane);
            if ((isPrevSingleLane | isPrevLeftLane) & (isCurrentSingleLane | isCurrentLeftLane)) {
                System.out.println("current: " + currentRUnit.getId() + " left: " + currentRUnit.isLeft() + " x: " +
                        currentRUnit.getX() + " y: " + currentRUnit.getY() + " intersected with prev: " +
                        prevRUnit.getId() + " left: " + prevRUnit.isLeft() + " x: " +
                        prevRUnit.getX() + " y: " + prevRUnit.getY() + " BECAUSE LEFT");
                canIntersect = true;
            }
        } else {//going right
            boolean isPrevRightLane = (!prevRUnit.isLeft());
            boolean isCurrentRightLane = (!currentRUnit.isLeft());
            System.out.println(isPrevSingleLane + " " + isPrevRightLane+ " " +isCurrentSingleLane + " " +isCurrentRightLane);
            if ((isPrevSingleLane | isPrevRightLane) & (isCurrentSingleLane | isCurrentRightLane)) {
                System.out.println("current: " + currentRUnit.getId() + " left: " + currentRUnit.isLeft() + " x: " +
                        currentRUnit.getX() + " y: " + currentRUnit.getY() + " intersected with prev: " +
                        prevRUnit.getId() + " left: " + prevRUnit.isLeft() + " x: " +
                        prevRUnit.getX() + " y: " + prevRUnit.getY() + " BECAUSE RIGHT");
                canIntersect = true;
            }
        }

        return canIntersect;
    }

    private void connectNext(RUnit prev, RUnit next)
    {
        //Assigning the current Runit's previous Runitist with previous Runit
        if (!next.getPrevsRUnitList().contains(prev)) {
            next.getPrevsRUnitList().add(prev);
        }

        //Assigning the previous Runit's next Runitist with current Runit
        if (!prev.getNextRUnitList().contains(next)) {
            prev.getNextRUnitList().add(next);
        }
    }
    private void manageIntersections(RUnit currentRUnit)
    {
        String m = "";
        m+=currentRUnit.getId() + "[manageIntersections]";
        Coordinates intersectionCoordinates = new Coordinates(currentRUnit.getX(), currentRUnit.getY());
        //Check for intersection in Single Lane Coordinates
        RUnit intersected = checkIntersectionAndReturnIntersectedRUnit(roadNetwork.getrUnitHashtable().values(),intersectionCoordinates, currentRUnit.getId());
        m+=(intersected!=null ? "[SingleIntersected]" : "[NOT SingleIntersected]");
        if(intersected == null){
            m+=(intersected!=null ? "[DoubleIntersected]" : "[NOT DoubleIntersected]");
            //Check for intersection in Double Lane Changeable Coordinates
            intersected = checkIntersectionAndReturnIntersectedRUnit(roadNetwork.getChangeableRUnitHashtable().values(),intersectionCoordinates, currentRUnit.getId());
        }

        m+="[1gotach"+(intersected!=null ? intersected.getId() : "null")+"]";
        //checks if the intersection was caused by accidental mouse drag
        if(intersected!=null)
            if(!checkForRoadDensityCollisions(intersected, currentRUnit))
                intersected=null;
        m+="[2gotach"+(intersected!=null ? intersected.getId() : "null")+"]";
        if(intersected!=null)
        {
            //once you have found an intersected coordinate connect it up

            //connect incoming to intersected
            if(checkIntersectionIsLawful(currentRUnit, intersected))
            {
                m+="[koko]";
                connectNext(currentRUnit, intersected);
            }

            //connect intersected to incoming
            if(checkIntersectionIsLawful(intersected, currentRUnit))
            {
                m+="[kuku]";
                connectNext(intersected, currentRUnit);
            }

        }
        m+="[manageIntersections-END]";
        System.out.println(m);

    }
    private RUnit getNext(int x, int y, RUnit prevRUnit, boolean isDoubleLane) {

        RUnit currentRUnit = null;

        //Create new RUnit Object if it does not intersect with any existing lane coordinates
        if (isDoubleLane) {
            currentRUnit = new RUnit("c" + String.valueOf(rUnitId), x, y);
        } else {
            currentRUnit = new RUnit(String.valueOf(rUnitId), x, y);
        }
        rUnitId++;


        if (prevRUnit != null) {
            connectNext(prevRUnit, currentRUnit);
        }

        return currentRUnit;

    }

    @Override
    public Map<String, RUnit> addDoubleLane(int x, int y, int changeableX, int changeableY, RUnit prevRUnit, RUnit changeablePrevRunit) {
        //Populate Current RUnit
        RUnit currentRUnit = getNext(x, y, prevRUnit, false);

        if (!roadNetwork.getrUnitHashtable().contains(currentRUnit)) {
            roadNetwork.getrUnitHashtable().put(currentRUnit.getId(), currentRUnit);
        }

        //Populate changeable Current RUnit
        RUnit currentChangeableRUnit = getNext(changeableX, changeableY, changeablePrevRunit, true);
        if (!roadNetwork.getChangeableRUnitHashtable().contains(currentChangeableRUnit)) {
            roadNetwork.getChangeableRUnitHashtable().put(currentChangeableRUnit.getId(), currentChangeableRUnit);
            currentChangeableRUnit.setLeft(false);
        }


        //Mutually setting the changeable RUnits
        currentRUnit.setChangeAbleRUnit(currentChangeableRUnit);
        currentChangeableRUnit.setChangeAbleRUnit(currentRUnit);

        manageIntersections(currentRUnit);
        manageIntersections(currentChangeableRUnit);

        Map<String, RUnit> prevRUnitMap = new HashMap<String, RUnit>();
        prevRUnitMap.put("runit", currentRUnit);
        prevRUnitMap.put("changeableRunit", currentChangeableRUnit);
        return prevRUnitMap;
    }


    @Override
    public void addTrafficLight(RUnit rUnit, TrafficLight trafficLight) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).addTrafficLight(trafficLight);
        roadNetwork.getTrafficLightHashtable().put(trafficLight.getTrafficLightID(), trafficLight);
    }


    @Override
    public void changeLight(double currentSecond) {
        TrafficLight currentTL;
        int t = 0;
        t = (int) (currentSecond % 120); //to know the corresponding column of the cycle at a particular current second
        if ((t >= 1) && (t <= 12)) t = 0;
        if ((t >= 13) && (t <= 24)) t = 1;
        if ((t >= 25) && (t <= 36)) t = 2;
        if ((t >= 37) && (t <= 48)) t = 3;
        if ((t >= 49) && (t <= 60)) t = 4;
        if ((t >= 61) && (t <= 72)) t = 5;
        if ((t >= 73) && (t <= 84)) t = 6;
        if ((t >= 85) && (t <= 96)) t = 7;
        if ((t >= 97) && (t <= 108)) t = 8;
        if ((t >= 109) && (t <= 120)) t = 9;

        for (Map.Entry<String, TrafficLight> obj : roadNetwork.getTrafficLightHashtable().entrySet()) {
            currentTL = obj.getValue();
            if (obj.getValue() != null) {
                obj.getValue().setTrafficLightCurrentColor(currentTL.getCycle().get(t));//depending on the current second
                //it sets the corresponding color(true or false) from the cycle

            }

        }
    }

    @Override
    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    @Override
    public void addZebraCrossing(RUnit rUnit, TrafficLight trafficLight) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setZebraCrossing(new ZebraCrossing(trafficLight));
        roadNetwork.getTrafficLightHashtable().put(trafficLight.getTrafficLightID(), trafficLight);
    }

    @Override
    public void addBlockage(RUnit rUnit) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setBlockage(new Blockage());
    }

    @Override
    public void addSpeedLimit(RUnit rUnit, int speedLimit) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new SpeedLimitSign(speedLimit));
    }

    @Override
    public void addStopSign(RUnit rUnit) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new StopSign());
    }

    @Override
    public void addWelcomeSign(RUnit rUnit, String location) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new WelcomeSign(location));
    }

    @Override
    public void addDirectionSign(RUnit rUnit, String location, DirectionSignType directionSignType) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new DirectionSign(location, directionSignType));
    }

    @Override
    public void addVehicleFactory(RUnit rUnit) {
    }

    @Override
    public void addTrafficLightBehavior(String trafficLightId, List<Boolean> trafficLightPattern) {
        TrafficLight trafficLight = roadNetwork.getTrafficLightHashtable().get(trafficLightId);
        if (trafficLight != null) {
            trafficLight.setCycle(trafficLightPattern);
        }
    }

    @Override
    public RUnit getRUnitByID(String ID) {
        return roadNetwork.getrUnitHashtable().get(ID);
    }

    @Override
    public TrafficLight getTrafficLightByID(String ID) {
        return roadNetwork.getTrafficLightHashtable().get(ID);
    }
}
