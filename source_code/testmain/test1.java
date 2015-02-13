package testmain;

import dataAndStructures.DataAndStructures;
import engine.SimEngine;
import managers.globalconfig.GlobalConfigManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehiclefactory.VehicleFactoryManager;

/**
 * Created by Fabians on 12/02/2015.
 */
public class test1 {
    public static void main(String[] args) {

        RoadNetworkManager roadNetworkManager = new RoadNetworkManager(new RoadNetwork());
        VehicleFactoryManager vehicleFactoryManager=new VehicleFactoryManager();
        GlobalConfigManager globalConfigManager= new GlobalConfigManager(100);

        vehicleFactoryManager.addVehicleFactory(roadNetworkManager.getRoadNetwork().getrUnitHashtable().get("0"));

        DataAndStructures dataAndStructures = new DataAndStructures(roadNetworkManager, vehicleFactoryManager, globalConfigManager);



        SimEngine engine = new SimEngine(dataAndStructures);
        engine.Play();
    }
}
