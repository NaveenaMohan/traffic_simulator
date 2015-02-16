package managers.roadnetwork;

import managers.runit.DirectionSignType;
import managers.runit.RUnit;
import managers.runit.TrafficLight;

import java.util.List;

/**
 * Created by Guera_000 on 12/02/2015.
 */
public class RoadNetworkManager implements IRoadNetworkManager {

    private RoadNetwork roadNetwork;
    private int rUnitId = 0;

    public RoadNetworkManager(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
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
    public boolean addTrafficLight(RUnit rUnit) {
        return false;
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
    public List<TrafficLight> getAllTrafficLights() {
        return null;
    }
}
