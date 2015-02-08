package managers.vehiclefactory;

import objects.RUnit;
import objects.Vehicle;

/**
 * Created by naveena on 08/02/15.
 */
public interface IVehicleFactoryManager {

    void addVehicleFactory(RUnit rUnit);

    Vehicle createVehicle(Long tick);
}
