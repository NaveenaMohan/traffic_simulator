package dataAndStructures;

import managers.globalconfig.IGlobalConfigManager;
import managers.roadnetwork.IRoadNetworkManager;
import managers.space.ISpaceManager;
import managers.space.SpaceManager;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.IVehicleFactoryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class DataAndStructures implements IDataAndStructures {

    private IRoadNetworkManager roadNetworkManager;
    private List<IVehicleManager> vehicleManagerList = new ArrayList<IVehicleManager>();
    private IVehicleFactoryManager vehicleFactoryManager;
    private IGlobalConfigManager globalConfigManager;
    private ISpaceManager spaceManager;

    public ISpaceManager getSpaceManager() {
        return spaceManager;
    }

    @Override
    public void setVehicleManagerList(List<IVehicleManager> vehicleManagerList) {
        this.vehicleManagerList = vehicleManagerList;
    }

    public DataAndStructures() {
    }

    public DataAndStructures(IRoadNetworkManager roadNetworkManager, IVehicleFactoryManager vehicleFactoryManager, IGlobalConfigManager globalConfigManager) {
        this.roadNetworkManager = roadNetworkManager;
        this.vehicleFactoryManager = vehicleFactoryManager;
        this.globalConfigManager = globalConfigManager;
        spaceManager=new SpaceManager();
    }

    @Override
    public IRoadNetworkManager getRoadNetworkManager() {
        return roadNetworkManager;
    }

    @Override
    public IGlobalConfigManager getGlobalConfigManager() {
        return globalConfigManager;
    }

    @Override
    public IVehicleFactoryManager getVehicleFactoryManager() {
        return vehicleFactoryManager;
    }

    @Override
    public List<IVehicleManager> getVehicles() {
        return vehicleManagerList;
    }
}
