package managers.vehiclefactory;

import managers.globalconfig.Route;
import managers.globalconfig.VehicleDensity;
import managers.runit.RUnit;
import managers.vehicle.Vehicle;

import java.util.List;

/**
 * Created by naveena on 09/02/15.
 */
public class VehicleFactory {

    private RUnit rUnit;
    private VehicleDensity vehicleDensity;
    private List<Route> routes;

    public VehicleFactory(RUnit rUnit) {
        this.rUnit = rUnit;
        addVehicle(rUnit);
    }

    public RUnit getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public Vehicle addVehicle(RUnit rUnit) {
        Vehicle vehicle=new Vehicle(rUnit);
        return vehicle;
    }

    public VehicleDensity getVehicleDensity() {
        return vehicleDensity;
    }

    public void setVehicleDensity(VehicleDensity vehicleDensity) {
        this.vehicleDensity = vehicleDensity;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
