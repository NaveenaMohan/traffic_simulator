package managers.vehiclefactory;

import dataAndStructures.IDataAndStructures;
import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.GlobalConfigManager;
import managers.globalconfig.IGlobalConfigManager;
import managers.globalconfig.VehicleDensity;
import managers.runit.RUnit;
import managers.space.ISpaceManager;
import managers.space.SpaceManager;
import managers.vehicle.Vehicle;
import managers.vehicle.VehicleType;

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

        return vehicleFactoryList.get(0).addVehicle(
                dataAndStructures.getVehicles().size()+1,
                VehicleType.car,//vehicle Type
                null,//driver
                null,//destination
                dataAndStructures.getSpaceManager(),
                dataAndStructures.getGlobalConfigManager().getCurrentSecond());
    }
}
