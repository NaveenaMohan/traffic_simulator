package managers.runit;

import managers.vehiclefactory.VehicleFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public class RUnit implements IRUnitManager{

    private int x;
    private int y;
    private int z;
    private List<RUnit> nextRUnitList = new ArrayList<RUnit>();
    private List<RUnit> prevsRUnitList = new ArrayList<RUnit>();
    private RUnit changeAbleRUnit;
    private String id;
    private boolean isLeft = true;
    private TrafficSign trafficSign;
    private ZebraCrossing zebraCrossing;
    private Blockage blockage;
    private TrafficLight trafficLight;

    public RUnit(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public RUnit(String id, List<RUnit> prevsRUnitList, List<RUnit> nextRUnitList) {
        this.id = id;
        this.prevsRUnitList = prevsRUnitList;
        this.nextRUnitList = nextRUnitList;
    }

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

    public List<RUnit> getPrevsRUnitList() {
        return prevsRUnitList;
    }

    @Override
    public boolean isLeft() {
        return isLeft;
    }

    @Override
    public void setLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public RUnit getChangeAbleRUnit() {
        return changeAbleRUnit;
    }

    public void setChangeAbleRUnit(RUnit changeAbleRUnit) {
        this.changeAbleRUnit = changeAbleRUnit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    @Override
    public void addTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }


}