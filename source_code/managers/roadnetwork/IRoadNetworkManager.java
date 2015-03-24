package managers.roadnetwork;

import managers.runit.DirectionSignType;
import managers.runit.IRUnitManager;
import managers.runit.TrafficLight;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRoadNetworkManager extends Serializable {

    public IRUnitManager addSingleLane(int x, int y, IRUnitManager prevIRUnitManager);

    public Map<String, IRUnitManager> addDoubleLane(int x, int y, int changeableX, int changeableY, IRUnitManager prevIRUnitManager, IRUnitManager changeablePrevRunit);

    public void addTrafficLight(IRUnitManager rUnit, TrafficLight trafficLight);

    public void addZebraCrossing(IRUnitManager rUnit, TrafficLight trafficLight);

    public void addBlockage(IRUnitManager rUnit);

    public void addSpeedLimit(IRUnitManager rUnit, int speedLimit);

    public void addStopSign(IRUnitManager rUnit);

    public void addWelcomeSign(IRUnitManager rUnit, String location);

    public void addDirectionSign(IRUnitManager rUnit, String location, DirectionSignType directionSignType);

    public void addTrafficLightBehavior(String trafficLightId, List<Boolean> trafficLightPattern);

    public void changeLight(double currentSecond);

    public RoadNetwork getRoadNetwork();
}
