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

import java.io.Serializable;

/**
 * Created by naveena on 08/02/15.
 */
public interface IVehicleFactoryManager extends Serializable {

    void addVehicleFactory(RUnit rUnit);

    Vehicle createVehicle(IDataAndStructures dataAndStructures);
}
