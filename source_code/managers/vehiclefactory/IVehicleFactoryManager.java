package managers.vehiclefactory;

import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.GlobalConfigManager;
import managers.globalconfig.VehicleDensity;
import managers.runit.RUnit;
import managers.space.SpaceManager;
import managers.vehicle.Vehicle;

/**
 * Created by naveena on 08/02/15.
 */
public interface IVehicleFactoryManager {

    void addVehicleFactory(RUnit rUnit);

    Vehicle createVehicle(Long time, GlobalConfigManager globalConfigManager, SpaceManager spaceManager);
}
