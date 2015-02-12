package dataAndStructures;

import managers.roadnetwork.IRoadNetworkManager;
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

    public DataAndStructures(IRoadNetworkManager roadNetworkManager, IVehicleFactoryManager vehicleFactoryManager) {
        this.roadNetworkManager = roadNetworkManager;
        this.vehicleFactoryManager = vehicleFactoryManager;
    }

    @Override
    public IRoadNetworkManager getRoadNetworkManager() {
        return roadNetworkManager;
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
