package engine;

import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.IVehicleFactoryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class Engine {

    //attributes
    private IRoadNetworkManager roadNetworkManager;
    private List<IVehicleManager> vehicleManagerList = new ArrayList<IVehicleManager>();
    private IVehicleFactoryManager vehicleFactoryManager;
    //add space and others


    public Engine(IRoadNetworkManager roadNetworkManager, IVehicleFactoryManager vehicleFactoryManager) {
        this.roadNetworkManager = roadNetworkManager;
        this.vehicleFactoryManager = vehicleFactoryManager;
    }

    public void Play(){
        //initialise timer
    }


    public List<IVehicleManager> getVehicleManagerList() {
        return vehicleManagerList;
    }

    public IRoadNetworkManager getRoadNetworkManager() {
        return roadNetworkManager;
    }
//actionPerformed
}
