package managers.vehiclefactory;

import dataAndStructures.IDataAndStructures;
import managers.globalconfig.VehicleType;
import managers.runit.RUnit;
import managers.vehicle.Vehicle;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class VehicleFactoryManager implements IVehicleFactoryManager {

    private List<VehicleFactory> vehicleFactoryList = new ArrayList<VehicleFactory>();

    @Override
    public void addVehicleFactory(RUnit rUnit) {
        vehicleFactoryList.add(new VehicleFactory(rUnit));
    }

    @Override
    public Vehicle createVehicle(IDataAndStructures dataAndStructures) {
        //create vehicle is consulting globalConfig to look at destinations, driver behaviour, climatic conditions, etc...

        if (vehicleFactoryList.get(0) != null) {
            return vehicleFactoryList.get(0).addVehicle(
                    dataAndStructures.getVehicles().size() + 1,
                    VehicleType.car,//vehicle Type
                    null,//driver
                    null,//destination
                    dataAndStructures.getSpaceManager(),
                    dataAndStructures.getGlobalConfigManager().getCurrentSecond());
        }
        return null;
    }
}
