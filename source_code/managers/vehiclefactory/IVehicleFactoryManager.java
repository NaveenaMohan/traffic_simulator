package managers.vehiclefactory;

import dataAndStructures.IDataAndStructures;
import managers.runit.IRUnitManager;
import managers.vehicle.IVehicleManager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IVehicleFactoryManager extends Serializable {

    void addVehicleFactory(IRUnitManager rUnit);

    IVehicleManager createVehicle(IDataAndStructures dataAndStructures);

    public List<VehicleFactory> getVehicleFactoryList();
}
