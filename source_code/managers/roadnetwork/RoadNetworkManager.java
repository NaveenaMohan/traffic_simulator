package managers.roadnetwork;

import common.Common;
import managers.runit.*;
import ui.Coordinates;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guera_000 on 12/02/2015.
 */
public class RoadNetworkManager implements IRoadNetworkManager {

    private RoadNetwork roadNetwork;

    private int rUnitId = 0;

    public RoadNetworkManager(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public static boolean checkForRoadDensityCollisions(IRUnitManager currentRUnit, IRUnitManager prevRUnit) {
        /*
        This function rejects an intersection if it was created by accidental mouse drags.
        It does that by checking if currentRUnit and prevRUnit are within x of ID distance from each other
         */

        if (prevRUnit == null)
            return true;

        int collisionDistance = 30;

        int currentID = Integer.parseInt(currentRUnit.getId().replace("c", ""));
        int previousID = Integer.parseInt(prevRUnit.getId().replace("c", ""));

        return Math.abs(currentID - previousID) >= collisionDistance;
    }

    public static boolean checkIntersectionIsLawful(IRUnitManager currentRUnit, IRUnitManager prevRUnit) {
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
                Common.getRoadBackwardDirection(prevRUnit, 5),
                Common.getRoadBackwardDirection(currentRUnit, 5)
        );

        boolean isCurrentSingleLane = (currentRUnit.getChangeAbleRUnit() == null);
        boolean isPrevSingleLane = (prevRUnit.getChangeAbleRUnit() == null);
        //if difference is positive it is going left
        if (difference >= 0) {

            boolean isPrevLeftLane = (prevRUnit.isLeft());
            boolean isCurrentLeftLane = (currentRUnit.isLeft());
            if ((isPrevSingleLane | isPrevLeftLane) & (isCurrentSingleLane | isCurrentLeftLane)) {
                canIntersect = true;
            }
        } else {//going right
            boolean isPrevRightLane = (!prevRUnit.isLeft());
            boolean isCurrentRightLane = (!currentRUnit.isLeft());
            if ((isPrevSingleLane | isPrevRightLane) & (isCurrentSingleLane | isCurrentRightLane)) {
                canIntersect = true;
            }
        }

        return canIntersect;
    }

    @Override
    public IRUnitManager addSingleLane(int x, int y, IRUnitManager prevRUnit) {
        //Populate Current RUnit
        IRUnitManager currentRUnit = getNext(x, y, prevRUnit, false);
        if (!roadNetwork.getrUnitHashtable().contains(currentRUnit)) {
            roadNetwork.getrUnitHashtable().put(String.valueOf(rUnitId), currentRUnit);
            if (prevRUnit != null) {
                manageIntersections(prevRUnit);
            }
        }


        return currentRUnit;
    }

    private IRUnitManager checkIntersectionAndReturnIntersectedRUnit(Collection<IRUnitManager> rUnits, Coordinates intersectionCoordinates, String id) {

        for (IRUnitManager rUnit : rUnits) {
            if (rUnit.getX() == intersectionCoordinates.getX() & rUnit.getY() == intersectionCoordinates.getY() & !rUnit.getId().equals(id)) {
                return rUnit;
            }
        }
        return null;
    }

    private void connectNext(IRUnitManager prev, IRUnitManager next) {
        //Assigning the previous Runit's next Runitist with current Runit
        if (!prev.getNextRUnitList().contains(next) & !prev.getPrevsRUnitList().contains(next)) {
            prev.getNextRUnitList().add(next);
        }

        //Assigning the current Runit's previous Runitist with previous Runit
        if (!next.getPrevsRUnitList().contains(prev) & !next.getNextRUnitList().contains(prev)) {
            next.getPrevsRUnitList().add(prev);
        }


    }

    private void manageIntersections(IRUnitManager currentRUnit) {
        Coordinates intersectionCoordinates = new Coordinates(currentRUnit.getX(), currentRUnit.getY());
        //Check for intersection in Single Lane Coordinates
        IRUnitManager intersected = checkIntersectionAndReturnIntersectedRUnit(roadNetwork.getrUnitHashtable().values(), intersectionCoordinates, currentRUnit.getId());
        if (intersected == null) {
            //Check for intersection in Double Lane Changeable Coordinates
            intersected = checkIntersectionAndReturnIntersectedRUnit(roadNetwork.getChangeableRUnitHashtable().values(), intersectionCoordinates, currentRUnit.getId());
        }

        if (!checkForRoadDensityCollisions(currentRUnit, intersected))
            intersected = null;

        if (intersected != null) {
            //once you have found an intersected coordinate connect it up

            //connect incoming to intersected
            if (checkIntersectionIsLawful(currentRUnit, intersected)) {
                connectNext(currentRUnit, (intersected.getNextRUnitList().size() > 0 ? intersected.getNextRUnitList().get(0) : intersected));
            }

            //connect intersected to incoming
            if (checkIntersectionIsLawful(intersected, currentRUnit)) {
                connectNext(intersected, (currentRUnit.getNextRUnitList().size() > 0 ? currentRUnit.getNextRUnitList().get(0) : currentRUnit));
            }
        }
    }

    private IRUnitManager getNext(int x, int y, IRUnitManager prevRUnit, boolean isDoubleLane) {

        IRUnitManager currentRUnit;

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
    public Map<String, IRUnitManager> addDoubleLane(int x, int y, int changeableX, int changeableY, IRUnitManager prevRUnit, IRUnitManager changeablePrevRunit) {
        //Populate Current RUnit
        IRUnitManager currentRUnit = getNext(x, y, prevRUnit, false);

        if (!roadNetwork.getrUnitHashtable().contains(currentRUnit)) {
            roadNetwork.getrUnitHashtable().put(currentRUnit.getId(), currentRUnit);
        }

        //Populate changeable Current RUnit
        IRUnitManager currentChangeableRUnit = getNext(changeableX, changeableY, changeablePrevRunit, true);
        if (!roadNetwork.getChangeableRUnitHashtable().contains(currentChangeableRUnit)) {
            roadNetwork.getChangeableRUnitHashtable().put(currentChangeableRUnit.getId(), currentChangeableRUnit);
            currentChangeableRUnit.setLeft(false);
        }


        //Mutually setting the changeable RUnits
        currentRUnit.setChangeAbleRUnit(currentChangeableRUnit);
        currentChangeableRUnit.setChangeAbleRUnit(currentRUnit);

        if (prevRUnit != null && changeablePrevRunit != null) {
            manageIntersections(prevRUnit);
            manageIntersections(changeablePrevRunit);
        }

        Map<String, IRUnitManager> prevRUnitMap = new HashMap<String, IRUnitManager>();
        prevRUnitMap.put("runit", currentRUnit);
        prevRUnitMap.put("changeableRunit", currentChangeableRUnit);
        return prevRUnitMap;
    }


    @Override
    public void addTrafficLight(IRUnitManager rUnit, TrafficLight trafficLight) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).addTrafficLight(trafficLight);
        roadNetwork.getTrafficLightHashtable().put(trafficLight.getTrafficLightID(), trafficLight);
    }


    @Override
    public void changeLight(double currentSecond) {
        TrafficLight currentTL;
        int t;
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
    public void addZebraCrossing(IRUnitManager rUnit, TrafficLight trafficLight) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setZebraCrossing(new ZebraCrossing(trafficLight));
        roadNetwork.getTrafficLightHashtable().put(trafficLight.getTrafficLightID(), trafficLight);
    }

    @Override
    public void addBlockage(IRUnitManager rUnit) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setBlockage(new Blockage());
    }

    @Override
    public void addSpeedLimit(IRUnitManager rUnit, int speedLimit) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new SpeedLimitSign(speedLimit));
    }

    @Override
    public void addStopSign(IRUnitManager rUnit) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new StopSign());
    }

    @Override
    public void addWelcomeSign(IRUnitManager rUnit, String location) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new WelcomeSign(location));
    }

    @Override
    public void addDirectionSign(IRUnitManager rUnit, String location, DirectionSignType directionSignType) {
        roadNetwork.getrUnitHashtable().get(String.valueOf(rUnit.getId())).setTrafficSign(new DirectionSign(location, directionSignType));
    }

    @Override
    public void addTrafficLightBehavior(String trafficLightId, List<Boolean> trafficLightPattern) {
        TrafficLight trafficLight = roadNetwork.getTrafficLightHashtable().get(trafficLightId);
        if (trafficLight != null) {
            trafficLight.setCycle(trafficLightPattern);
        }
    }

    @Override
    public IRUnitManager getIRUnitManagerByID(String ID) {
        return roadNetwork.getrUnitHashtable().get(ID);
    }

    @Override
    public TrafficLight getTrafficLightByID(String ID) {
        return roadNetwork.getTrafficLightHashtable().get(ID);
    }
}
