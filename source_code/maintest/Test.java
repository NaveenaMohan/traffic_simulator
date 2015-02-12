package maintest;

import com.sun.xml.internal.ws.api.pipe.Engine;
import dataAndStructures.DataAndStructures;
import engine.SimEngine;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehiclefactory.VehicleFactoryManager;


/**
 * Created by Guera_000 on 12/02/2015.
 */
public class Test {
    public static void main(String[] args) {


        RoadNetworkManager roadNetworkManager = new RoadNetworkManager(new RoadNetwork());
        VehicleFactoryManager vehicleFactoryManager=new VehicleFactoryManager();

        vehicleFactoryManager.addVehicleFactory(roadNetworkManager.getRoadNetwork().getrUnitHashtable().get("0"));
        DataAndStructures dataAndStructures = new DataAndStructures(roadNetworkManager, vehicleFactoryManager);

        SimEngine engine = new SimEngine(dataAndStructures);
        engine.Play();
    }

}