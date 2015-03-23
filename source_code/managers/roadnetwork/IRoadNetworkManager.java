package managers.roadnetwork;

import managers.runit.DirectionSignType;
import managers.runit.RUnit;
import managers.runit.TrafficLight;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRoadNetworkManager extends Serializable {

    RUnit addSingleLane(int x, int y, RUnit prevRUnit);

    Map<String, RUnit> addDoubleLane(int x, int y, int changeableX, int changeableY, RUnit prevRUnit, RUnit changeablePrevRunit);

    void addTrafficLight(RUnit rUnit, TrafficLight trafficLight);

    void addZebraCrossing(RUnit rUnit, TrafficLight trafficLight);

    void addBlockage(RUnit rUnit);

    void addSpeedLimit(RUnit rUnit, int speedLimit);

    void addStopSign(RUnit rUnit);

    void addWelcomeSign(RUnit rUnit, String location);

    void addDirectionSign(RUnit rUnit, String location, DirectionSignType directionSignType);

    void addTrafficLightBehavior(String trafficLightId, List<Boolean> trafficLightPattern);

    public void changeLight(double currentSecond);

    RoadNetwork getRoadNetwork();
}
