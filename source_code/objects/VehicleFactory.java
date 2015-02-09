package objects;

import java.util.List;

/**
 * Created by naveena on 09/02/15.
 */
public class VehicleFactory {

    private RUnit rUnit;
    private VehicleDensity vehicleDensity;
    private List<Route> routes;

    public RUnit getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public Vehicle addVehicle() {
        return null;
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
