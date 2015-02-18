package managers.runit;

import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRUnitManager {

    void addTrafficLight(TrafficLight trafficLight);

    void addZebraCrossing();

    void addBlockage();

    void addSpeedLimit();

    void addStopSign();

    void addWelcomeSign();

    void addDirectionSign();

    public void ChangeLight(boolean color, String trafficLightID, int index);

    public boolean go();

}
