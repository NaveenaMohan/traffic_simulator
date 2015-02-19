package testmain;

import dataAndStructures.DataAndStructures;
import engine.SimEngine;
import managers.globalconfig.GlobalConfigManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.TrafficLight;
import managers.vehiclefactory.VehicleFactoryManager;
import ui.draw.Coordinates;
import ui.draw.DrawingBoard;

import java.util.ArrayList;

/**
 * Created by Fabians on 12/02/2015.
 */
public class test1 {
    public static void main(String[] args) {

        TrafficLight trafficLight=new TrafficLight();
        RoadNetwork roadNetwork=new RoadNetwork();
        RoadNetworkManager roadNetworkManager = new RoadNetworkManager(roadNetwork);
        VehicleFactoryManager vehicleFactoryManager=new VehicleFactoryManager();
        GlobalConfigManager globalConfigManager= new GlobalConfigManager(100, 5);

        vehicleFactoryManager.addVehicleFactory(roadNetworkManager.getRUnitByID("0"));
        roadNetworkManager.addTrafficLight(roadNetworkManager.getRUnitByID("5"), trafficLight);//TODO to be done by UI
        roadNetwork.printTrafficLights();

        DataAndStructures dataAndStructures = new DataAndStructures(roadNetworkManager, vehicleFactoryManager, globalConfigManager);



        SimEngine engine = new SimEngine(dataAndStructures);
        engine.Play(new DrawingBoard(new ArrayList<Coordinates>(), roadNetworkManager, engine));
    }
}
