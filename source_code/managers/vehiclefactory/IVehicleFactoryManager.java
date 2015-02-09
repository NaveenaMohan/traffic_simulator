package managers.vehiclefactory;

import managers.runit.RUnit;
import managers.vehicle.Vehicle;

/**
 * Created by naveena on 08/02/15.
 */
public interface IVehicleFactoryManager {

    void addVehicleFactory(RUnit rUnit);

    Vehicle createVehicle(Long tick);
}
