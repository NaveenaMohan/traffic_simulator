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
    private List<IRUnitManager> nextRUnitList = new ArrayList<IRUnitManager>();
    private List<IRUnitManager> prevsRUnitList = new ArrayList<IRUnitManager>();
    private IRUnitManager changeAbleRUnit;
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

    public RUnit(String id, List<IRUnitManager> prevsRUnitList, List<IRUnitManager> nextRUnitList) {
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

    public List<IRUnitManager> getNextRUnitList() {
        return nextRUnitList;
    }

    public void setNextRUnitList(List<IRUnitManager> nextRUnitList) {
        this.nextRUnitList = nextRUnitList;
    }

    public List<IRUnitManager> getPrevsRUnitList() {
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

    public void setPrevsRUnitList(List<IRUnitManager> prevsRUnitList) {
        this.prevsRUnitList = prevsRUnitList;
    }

    public IRUnitManager getChangeAbleRUnit() {
        return changeAbleRUnit;
    }

    public void setChangeAbleRUnit(IRUnitManager changeAbleRUnit) {
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
