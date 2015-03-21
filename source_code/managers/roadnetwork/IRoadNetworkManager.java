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

    IRUnitManager addSingleLane(int x, int y, IRUnitManager prevIRUnitManager);

    Map<String, IRUnitManager> addDoubleLane(int x, int y, int changeableX, int changeableY, IRUnitManager prevIRUnitManager, IRUnitManager changeablePrevRunit);

    void addTrafficLight(IRUnitManager rUnit, TrafficLight trafficLight);

    void addZebraCrossing(IRUnitManager rUnit, TrafficLight trafficLight);

    void addBlockage(IRUnitManager rUnit);

    void addSpeedLimit(IRUnitManager rUnit, int speedLimit);

    void addStopSign(IRUnitManager rUnit);

    void addWelcomeSign(IRUnitManager rUnit, String location);

    void addDirectionSign(IRUnitManager rUnit, String location, DirectionSignType directionSignType);

    void addVehicleFactory(IRUnitManager rUnit);

    void addTrafficLightBehavior(String trafficLightId, List<Boolean> trafficLightPattern);

    public void changeLight(double currentSecond);

    IRUnitManager getIRUnitManagerByID(String ID);

    TrafficLight getTrafficLightByID(String ID);

    RoadNetwork getRoadNetwork();
}
