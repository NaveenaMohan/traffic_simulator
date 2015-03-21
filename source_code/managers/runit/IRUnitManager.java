package managers.runit;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRUnitManager extends Serializable {

    void addTrafficLight(TrafficLight trafficLight);

    void addZebraCrossing();

    void addBlockage();

    void addSpeedLimit();

    void addStopSign();

    void addWelcomeSign();

    void addDirectionSign();

    ZebraCrossing getZebraCrossing();

    void setZebraCrossing(ZebraCrossing zebraCrossing);

    TrafficLight getTrafficLight();

    Blockage getBlockage();

    void setBlockage(Blockage blockage);

    TrafficSign getTrafficSign();

    void setTrafficSign(TrafficSign trafficSign);

    int getX();

    int getY();

    int getZ();

    String getId();

    List<IRUnitManager> getNextRUnitList();

    List<IRUnitManager> getPrevsRUnitList();

    boolean isLeft();

    void setLeft(boolean isLeft);

    IRUnitManager getChangeAbleRUnit();

    void setChangeAbleRUnit(IRUnitManager currentChangeableRUnit);

    public boolean go();
}
