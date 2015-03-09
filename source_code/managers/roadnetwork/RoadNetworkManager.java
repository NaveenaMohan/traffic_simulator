package managers.roadnetwork;

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
        RUnit currentRUnit = checkIntersection(x,y,prevRUnit,false);
        roadNetwork.getrUnitHashtable().put(String.valueOf(rUnitId), currentRUnit);
        return currentRUnit;
    }

    private RUnit checkIntersectionAndReturnIntersectedRUnit(Collection<RUnit> rUnits,Coordinates intersectionCoordinates){

        for(RUnit rUnit :rUnits ){
            Coordinates coordinates = new Coordinates(rUnit.getX(),rUnit.getY());
            if(rUnit.getX() == intersectionCoordinates.getX() & rUnit.getY() == intersectionCoordinates.getY()){
                System.out.println("INTERSECTION");
                return rUnit;
            }
        }
        return null;
    }

    private RUnit checkIntersection(int x, int y,RUnit prevRUnit,boolean isDoubleLane){
        Coordinates intersectionCoordinates = new Coordinates(x,y);
        //Check for intersection in Single Lane Coordinates
        RUnit currentRUnit = checkIntersectionAndReturnIntersectedRUnit(roadNetwork.getrUnitHashtable().values(),intersectionCoordinates);
        if(currentRUnit == null){
            //Check for intersection in Double Lane Changeable Coordinates
            currentRUnit = checkIntersectionAndReturnIntersectedRUnit(roadNetwork.getChangeableRUnitHashtable().values(),intersectionCoordinates);
        }
        if(currentRUnit == null){
            //Create new RUnit Object if it does not intersect with any existing lane coordinates
            if(isDoubleLane){
                currentRUnit = new RUnit("c" + String.valueOf(rUnitId), x, y);
            }else{
                currentRUnit = new RUnit(String.valueOf(rUnitId), x, y);
            }
            rUnitId++;
        }

        if (prevRUnit != null) {
            //Assigning the current Runit's previous Runitist with previous Runit
            if(!currentRUnit.getPrevsRUnitList().contains(prevRUnit)){
                currentRUnit.getPrevsRUnitList().add(prevRUnit);
            }
            //Assigning the previous Runit's next Runitist with current Runit
            if(!prevRUnit.getNextRUnitList().contains(currentRUnit)){
                prevRUnit.getNextRUnitList().add(currentRUnit);
            }
        }
        return currentRUnit;
    }

    @Override
    public Map<String, RUnit> addDoubleLane(int x, int y, int changeableX, int changeableY, RUnit prevRUnit, RUnit changeablePrevRunit) {
        //Populate Current RUnit
        RUnit currentRUnit = checkIntersection(x,y,prevRUnit,false);
        roadNetwork.getrUnitHashtable().put(currentRUnit.getId(), currentRUnit);

        //Populate changeable Current RUnit
        RUnit currentChangeableRUnit = checkIntersection(changeableX,changeableY,changeablePrevRunit,true);
        roadNetwork.getChangeableRUnitHashtable().put(currentChangeableRUnit.getId(), currentChangeableRUnit);
        currentChangeableRUnit.setLeft(false);

        //Mutually setting the changeable RUnits
        currentRUnit.setChangeAbleRUnit(currentChangeableRUnit);
        currentChangeableRUnit.setChangeAbleRUnit(currentRUnit);

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
        int t=0;
        t= (int) (currentSecond % 120); //to know the corresponding column of the cycle at a particular current second
        if((t>=1) && (t<=12)) t=0;
        if((t>=13) && (t<=24)) t=1;
        if((t>=25) && (t<=36)) t=2;
        if((t>=37) && (t<=48)) t=3;
        if((t>=49) && (t<=60)) t=4;
        if((t>=61) && (t<=72)) t=5;
        if((t>=73) && (t<=84)) t=6;
        if((t>=85) && (t<=96)) t=7;
        if((t>=97) && (t<=108)) t=8;
        if((t>=109) && (t<=120)) t=9;

        for(Map.Entry<String, TrafficLight> obj : roadNetwork.getTrafficLightHashtable().entrySet()){
            currentTL=obj.getValue();
            if (obj.getValue()!=null) {
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
