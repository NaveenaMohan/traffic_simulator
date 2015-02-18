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

    ZebraCrossing getZebraCrossing();

    TrafficLight getTrafficLight();

    Blockage getBlockage();

    TrafficSign getTrafficSign();

     int getX();

     int getY();

     int getZ();

    int getId();

    List<RUnit> getNextRUnitList();

    List<RUnit> getPrevsRUnitList();

    public void ChangeLight(boolean color, String trafficLightID, int index);

    RUnit getChangeAbleRUnit();
    public boolean go();

}
