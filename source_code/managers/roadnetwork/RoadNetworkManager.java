package managers.roadnetwork;

import managers.runit.DirectionSignType;
import managers.runit.RUnit;
import managers.runit.TrafficLight;

import java.util.*;

/**
 * Created by Guera_000 on 12/02/2015.
 */
public class RoadNetworkManager implements IRoadNetworkManager {

    private RoadNetwork roadNetwork;

    private int rUnitId = 0;

    public RoadNetworkManager(RoadNetwork roadNetwork){
        this.roadNetwork =roadNetwork;
    }


    @Override
    public RUnit addSingleLane(int x, int y, RUnit prevRUnit) {
        RUnit currentRUnit = new RUnit(rUnitId, x, y);
        if (prevRUnit != null) {
            currentRUnit.getPrevsRUnitList().add(prevRUnit);
            prevRUnit.getNextRUnitList().add(currentRUnit);
        }
        roadNetwork.getrUnitHashtable().put(String.valueOf(rUnitId), currentRUnit);
        rUnitId++;
        return currentRUnit;
    }

    @Override
    public boolean addDoubleLane(int x, int y, RUnit prevRUnit) {
        return false;
    }

    @Override
    public boolean addTrafficLight(RUnit rUnit, TrafficLight trafficLight) {
        //TODO naveena - you can use this code. The main point is retrieve the list of traffic lights
        //TODO here getTrafficLightHashtable.
        //TODO remember to first create the traffic light with its cycle. Then add to RUnit and then to getTrafficLightHashtable

        //Naveena is to retrieve this info from UI
        ArrayList<Boolean> cycle= new ArrayList<Boolean>(Arrays.asList(true, false, true, false, true, true, true, true, true, true));
        trafficLight.setCycle(cycle);
        trafficLight.setTrafficLightID(1);

        rUnit.addTrafficLight(trafficLight); //set the trafficLight to the rUnit

        roadNetwork.getTrafficLightHashtable().put(trafficLight.getTrafficLightID()+ "", trafficLight); //adds trafficLights to Hashtable

        return false;
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
        return null;
    }

    @Override
    public boolean addZebraCrossing(RUnit rUnit) {
        return false;
    }

    @Override
    public boolean addBlockage(RUnit rUnit) {
        return false;
    }

    @Override
    public boolean addSpeedLimit(RUnit rUnit, int speedLimit) {
        return false;
    }

    @Override
    public boolean addStopSign(RUnit rUnit) {
        return false;
    }

    @Override
    public boolean addWelcomeSign(RUnit rUnit, String location) {
        return false;
    }

    @Override
    public boolean addDirectionSign(RUnit rUnit, String location, DirectionSignType directionSignType) {
        return false;
    }

    @Override
    public boolean addVehicleFactory(RUnit rUnit) {
        return false;
    }

    @Override
    public void addTrafficLightBehavior(TrafficLight trafficLight, List<Boolean> trafficLightPattern) {

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
