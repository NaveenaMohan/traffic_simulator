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

    TrafficLight getTrafficLight();

    Blockage getBlockage();

    TrafficSign getTrafficSign();

     int getX();

     int getY();

     int getZ();

    String getId();

    List<IRUnitManager> getNextRUnitList();

    List<IRUnitManager> getPrevsRUnitList();

    boolean isLeft();

    void setLeft(boolean isLeft);

    public void ChangeLight(boolean color, String trafficLightID, int index);

    IRUnitManager getChangeAbleRUnit();

    public boolean go();

    void setChangeAbleRUnit(IRUnitManager currentChangeableRUnit);

    void setZebraCrossing(ZebraCrossing zebraCrossing);

    void setBlockage(Blockage blockage);

    void setTrafficSign(TrafficSign trafficSign);
}
