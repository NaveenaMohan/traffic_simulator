package managers.vehicle;

import managers.globalconfig.ClimaticCondition;
import managers.runit.RUnit;
import managers.space.Space;

/**
 * Created by naveena on 08/02/15.
 */
public class Vehicle {

    private RUnit rUnit;
    private Driver driver;
    private int maxSpeedLimit;
    private VehicleType vehicleType;
    private String destination;
    private ClimaticCondition climaticCondition;

    public Vehicle(RUnit rUnit) { //needed to add the RUnit of the vehicleFactory to the Vehicle
        this.rUnit = rUnit;
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

    public ClimaticCondition getClimaticCondition() {
        return climaticCondition;
    }

    public void setClimaticCondition(ClimaticCondition climaticCondition) {
        this.climaticCondition = climaticCondition;
    }

    public void move(Space space) {

        if(this.rUnit.getNextRUnitList().size()>0)
            this.rUnit = this.rUnit.getNextRUnitList().get(0);

    }
}
