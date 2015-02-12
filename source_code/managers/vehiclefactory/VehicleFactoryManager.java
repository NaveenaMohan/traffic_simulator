package managers.vehiclefactory;

import managers.runit.RUnit;
import managers.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class VehicleFactoryManager implements IVehicleFactoryManager {

    private List<VehicleFactory> vehicleFactoryList = new ArrayList<VehicleFactory>();

    public VehicleFactoryManager() {
        //hardcoding vehicle factory - to be deleted

    }

    @Override
    public void addVehicleFactory(RUnit rUnit) {
        vehicleFactoryList.add(new VehicleFactory(rUnit));
    }

    @Override
    public Vehicle createVehicle(Long tick) {
        //at the moment create one vehicle on every tick
        return vehicleFactoryList.get(0).addVehicle();
    }
}
