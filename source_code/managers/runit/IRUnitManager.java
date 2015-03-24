package managers.runit;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IRUnitManager extends Serializable {

    public void addTrafficLight(TrafficLight trafficLight);

    public ZebraCrossing getZebraCrossing();

    public void setZebraCrossing(ZebraCrossing zebraCrossing);

    public TrafficLight getTrafficLight();

    public Blockage getBlockage();

    public void setBlockage(Blockage blockage);

    public TrafficSign getTrafficSign();

    public void setTrafficSign(TrafficSign trafficSign);

    public int getX();

    public int getY();

    public int getZ();

    public String getId();

    public List<IRUnitManager> getNextRUnitList();

    public List<IRUnitManager> getPrevsRUnitList();

    public boolean isLeft();

    public void setLeft(boolean isLeft);

    public IRUnitManager getChangeAbleRUnit();

    public void setChangeAbleRUnit(IRUnitManager currentChangeableRUnit);
}
