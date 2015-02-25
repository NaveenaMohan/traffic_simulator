import dataAndStructures.DataAndStructures;
import engine.SimEngine;
import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.GlobalConfigManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.TrafficLight;
import managers.vehiclefactory.VehicleFactoryManager;
import reports.DCP;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Fabians on 12/02/2015.
 */
public class test1 {
    public static void main(String[] args) {

        TrafficLight trafficLight=new TrafficLight();
        RoadNetwork roadNetwork=new RoadNetwork();
        RoadNetworkManager roadNetworkManager = new RoadNetworkManager(roadNetwork);
        VehicleFactoryManager vehicleFactoryManager=new VehicleFactoryManager();
        ClimaticCondition climaticCondition=new ClimaticCondition();
        GlobalConfigManager globalConfigManager= new GlobalConfigManager(100, 5, climaticCondition);

        vehicleFactoryManager.addVehicleFactory(roadNetworkManager.getRUnitByID("0"));
        roadNetworkManager.addTrafficLight(roadNetworkManager.getRUnitByID("5"), trafficLight);//TODO to be done by UI
        roadNetwork.printTrafficLights();

        DataAndStructures dataAndStructures = new DataAndStructures(roadNetworkManager, vehicleFactoryManager, globalConfigManager);



        SimEngine engine = new SimEngine(dataAndStructures);
        engine.Play(new DrawingBoard(new DefaultTableModel(), roadNetworkManager, engine));
    }
}
