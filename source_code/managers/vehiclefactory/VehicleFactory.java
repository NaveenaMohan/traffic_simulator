package managers.vehiclefactory;

import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.Route;
import managers.globalconfig.VehicleDensity;
import managers.runit.RUnit;
import managers.space.ObjectInSpace;
import managers.space.SpaceManager;
import managers.vehicle.Driver;
import managers.vehicle.Vehicle;
import managers.vehicle.VehicleType;

import java.util.List;

/**
 * Created by naveena on 09/02/15.
 */
public class VehicleFactory{

    private RUnit rUnit;

    public VehicleFactory(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public RUnit getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public Vehicle addVehicle(VehicleType vehicleType, Driver driver, String destination, SpaceManager spaceManager) {
        //vehicle factory creates vehicles, it sets their driver and objectInSpace

        //create object in space based on the vehicle type

        ObjectInSpace objectInSpace;
        switch(vehicleType)
        {
            case car:
                objectInSpace = spaceManager.createVehicleSpace(rUnit.getX(), rUnit.getY(), rUnit.getZ(), 2,2,2,null);
                break;
            case emergency:
                objectInSpace = spaceManager.createVehicleSpace(rUnit.getX(), rUnit.getY(), rUnit.getZ(), 2,2,2,null);
                break;
            case heavyLoad:
                objectInSpace = spaceManager.createVehicleSpace(rUnit.getX(), rUnit.getY(), rUnit.getZ(), 4,4,4,null);
                break;
            default:
                objectInSpace = spaceManager.createVehicleSpace(rUnit.getX(), rUnit.getY(), rUnit.getZ(), 2,2,2,null);
        }

        Vehicle vehicle=new Vehicle(rUnit,//vehicle starting point
                driver,
                100,//maximum speed
                vehicleType,
                destination,
                objectInSpace
                );
        return vehicle;
    }

}
