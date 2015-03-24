package dataAndStructures;

import managers.globalconfig.IGlobalConfigManager;
import managers.roadnetwork.IRoadNetworkManager;
import managers.space.ISpaceManager;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.IVehicleFactoryManager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface IDataAndStructures extends Serializable {

    public IRoadNetworkManager getRoadNetworkManager();

    public IVehicleFactoryManager getVehicleFactoryManager();

    public List<IVehicleManager> getVehicles();

    public IGlobalConfigManager getGlobalConfigManager();

    public ISpaceManager getSpaceManager();

    public void setVehicleManagerList(List<IVehicleManager> vehicleManagerList);

}
