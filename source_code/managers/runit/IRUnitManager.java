package managers.runit;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRUnitManager extends Serializable {

    void addTrafficLight(TrafficLight trafficLight);

    ZebraCrossing getZebraCrossing();

    TrafficLight getTrafficLight();

    Blockage getBlockage();

    TrafficSign getTrafficSign();

     int getX();

     int getY();

     int getZ();

    String getId();

    List<RUnit> getNextRUnitList();

    List<RUnit> getPrevsRUnitList();

    boolean isLeft();

    void setLeft(boolean isLeft);

    RUnit getChangeAbleRUnit();
}
