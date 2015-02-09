package objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public class RUnit {

    private int x;
    private int y;
    private int z;
    private List<RUnit> nextRUnitList = new ArrayList<RUnit>();
    private List<RUnit> prevsRUnitList = new ArrayList<RUnit>();
    private RUnit changeAbleRUnit;
    private int id;
    private boolean isLeft;
    private TrafficSign trafficSign;
    private ZebraCrossing zebraCrossing;
    private Blockage blockage;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public List<RUnit> getNextRUnitList() {
        return nextRUnitList;
    }

    public void setNextRUnitList(List<RUnit> nextRUnitList) {
        this.nextRUnitList = nextRUnitList;
    }

    public List<RUnit> getPrevsRUnitList() {
        return prevsRUnitList;
    }

    public void setPrevsRUnitList(List<RUnit> prevsRUnitList) {
        this.prevsRUnitList = prevsRUnitList;
    }

    public RUnit getChangeAbleRUnit() {
        return changeAbleRUnit;
    }

    public void setChangeAbleRUnit(RUnit changeAbleRUnit) {
        this.changeAbleRUnit = changeAbleRUnit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public TrafficSign getTrafficSign() {
        return trafficSign;
    }

    public void setTrafficSign(TrafficSign trafficSign) {
        this.trafficSign = trafficSign;
    }

    public ZebraCrossing getZebraCrossing() {
        return zebraCrossing;
    }

    public void setZebraCrossing(ZebraCrossing zebraCrossing) {
        this.zebraCrossing = zebraCrossing;
    }

    public Blockage getBlockage() {
        return blockage;
    }

    public void setBlockage(Blockage blockage) {
        this.blockage = blockage;
    }

    public VehicleFactory addVehicleFactory() {
        return null;
    }
}
