package managers.vehicle;

import managers.globalconfig.ClimaticCondition;
import managers.runit.RUnit;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;

/**
 * Created by naveena on 08/02/15.
 */
public class Vehicle implements IVehicleManager {

    private RUnit rUnit;
    private Driver driver;
    private int maxSpeedLimit;
    private VehicleType vehicleType;
    private String destination;
    private ObjectInSpace objectInSpace;

    public Vehicle(RUnit rUnit, Driver driver, int maxSpeedLimit, VehicleType vehicleType, String destination, ObjectInSpace objectInSpace) {
        this.rUnit = rUnit;
        this.driver = driver;
        this.maxSpeedLimit = maxSpeedLimit;
        this.vehicleType = vehicleType;
        this.destination = destination;
        this.objectInSpace = objectInSpace;
    }

    public RUnit getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getMaxSpeedLimit() {
        return maxSpeedLimit;
    }

    public void setMaxSpeedLimit(int maxSpeedLimit) {
        this.maxSpeedLimit = maxSpeedLimit;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void move(ISpaceManager spaceManager, Long time, ClimaticCondition climaticCondition) {

        //if there is anywhere to go - if there is a next
        if(this.rUnit.getNextRUnitList().size()>0) {

            //if you fit there
//            if(spaceManager.checkFit(objectInSpace)) {
//                //progress to the next rUnit
//                this.rUnit = this.rUnit.getNextRUnitList().get(0);
//
//                //adjust your position in space
//                objectInSpace.setX(rUnit.getX());
//                objectInSpace.setY(rUnit.getY());
//            }

            //progress to the next rUnit
            this.rUnit = this.rUnit.getNextRUnitList().get(0);

            //adjust your position in space
            objectInSpace.setX(rUnit.getX());
            objectInSpace.setY(rUnit.getY());
        }

    }

    @Override
    public boolean isVisible(int minX, int maxX, int minY, int maxY) {
        return false;
    }

    @Override
    public Vehicle getVehicle() {
        return this;
    }
}
