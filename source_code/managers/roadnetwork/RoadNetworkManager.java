package managers.roadnetwork;

import managers.runit.*;

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
    public RUnit addDoubleLane(int x, int y, RUnit prevRUnit) {

        return null;
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
