package managers.roadnetwork;

import objects.DirectionSignType;
import objects.RUnit;
import objects.TrafficLight;

import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRoadNetworkManager {

    boolean addSingleLan(int x, int y, RUnit prevRUnit);

    boolean addDoubleLane(int x, int y, RUnit prevRUnit);

    boolean addTrafficLight(RUnit rUnit);

    boolean addZebraCrossing(RUnit rUnit);

    boolean addBlockage(RUnit rUnit);

    boolean addSpeedLimit(RUnit rUnit, int speedLimit);

    boolean addStopSign(RUnit rUnit);

    boolean addWelcomeSign(RUnit rUnit, String location);

    boolean addDirectionSign(RUnit rUnit, String location, DirectionSignType directionSignType);

    boolean addVehicleFactory(RUnit rUnit);

    void addTrafficLightBehavior(TrafficLight trafficLight, List<Boolean> trafficLightPattern);

    List<TrafficLight> getAllTrafficLights();
}
