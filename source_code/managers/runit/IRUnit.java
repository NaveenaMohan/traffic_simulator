package managers.runit;

import objects.RUnit;

import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRUnit {

    void addRUnit(int x, int y, List<RUnit> prevRUnits, List<RUnit> changeableRUnits, boolean isLeft);

    void addTrafficLight();

    void addZebraCrossing();

    void addBlockage();

    void addSpeedLimit();

    void addStopSign();

    void addWelcomeSign();

    void addDirectionSign();

    void addTrafficLightBehavior();

}
