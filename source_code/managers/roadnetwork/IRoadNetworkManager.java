package managers.roadnetwork;

import managers.runit.DirectionSignType;
import managers.runit.RUnit;
import managers.runit.TrafficLight;

import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRoadNetworkManager {

    RUnit addSingleLane(int x, int y, RUnit prevRUnit);

    RUnit addDoubleLane(int x, int y, RUnit prevRUnit);

    void addTrafficLight(RUnit rUnit, TrafficLight trafficLight);

    void addZebraCrossing(RUnit rUnit, TrafficLight trafficLight);

    void addBlockage(RUnit rUnit);

    void addSpeedLimit(RUnit rUnit, int speedLimit);

    void addStopSign(RUnit rUnit);

    void addWelcomeSign(RUnit rUnit, String location);

    void addDirectionSign(RUnit rUnit, String location, DirectionSignType directionSignType);

    void addVehicleFactory(RUnit rUnit);

    void addTrafficLightBehavior(String trafficLightId, List<Boolean> trafficLightPattern);

    public void changeLight(double currentSecond);
    
    RUnit getRUnitByID(String ID);

    TrafficLight getTrafficLightByID(String ID);

    RoadNetwork getRoadNetwork();
}
