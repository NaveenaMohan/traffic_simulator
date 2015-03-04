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
    private boolean isRight;
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

    @Override
    public void ChangeLight(boolean color, String trafficLightID, int index) {
        //TODO change cycle element for traffic light
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean isLeft) {
        this.isRight = isLeft;
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

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    @Override
    public void addTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public boolean go(){
        if (this.getTrafficLight()!=null) {
            if (this.getTrafficLight().isGreen()) {
                return true; //if traffic Light is green
            }
            else return false; //if traffic Light is red
        }
        else
            return true; //if no traffic Light then vehicles can continue, treated as if it would be green.
    }


    @Override
    public void addZebraCrossing() {

    }

    @Override
    public void addBlockage() {

    }

    @Override
    public void addSpeedLimit() {

    }

    @Override
    public void addStopSign() {

    }

    @Override
    public void addWelcomeSign() {

    }

    @Override
    public void addDirectionSign() {

    }
}
