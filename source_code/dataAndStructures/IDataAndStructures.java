package dataAndStructures;

import managers.globalconfig.DriverBehaviorType;
import managers.roadnetwork.IRoadNetworkManager;
import managers.vehicle.IVehicleManager;
import managers.vehicle.VehicleType;
import managers.vehiclefactory.IVehicleFactoryManager;

import java.util.List;
import java.util.Map;

/**
 * Created by naveena on 08/02/15.
 */
public interface IDataAndStructures {

    public IRoadNetworkManager getRoadNetworkManager();
    public IVehicleFactoryManager getVehicleFactoryManager();
    public List<IVehicleManager> getVehicles();
}
