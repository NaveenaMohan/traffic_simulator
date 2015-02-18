package engine;

import dataAndStructures.DataAndStructures;
import managers.runit.RUnit;
import managers.space.ObjectInSpace;
import managers.vehicle.IVehicleManager;
import managers.runit.TrafficLight;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by Fabians on 12/02/2015.
 */
public class SimEngine {


    private Timer timer;
    private DataAndStructures dataAndStructures;
    private Long previousSecond=0L;//use this to make a move every second

    private int maxCars = 10;

    private int maxSeconds = 220;

    public SimEngine(DataAndStructures dataAndStructures) {
        this.dataAndStructures = dataAndStructures;
    }

    public void Play(ActionListener actionListener) {

        //initialise timer
        timer = new Timer(5, actionListener);
        timer.start();
    }

    public void performAction() {
        //LORENA TESTING TRAFFIC LIGHTS
        dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond());
        double currentSecond=dataAndStructures.getGlobalConfigManager().getCurrentSecond();
        RUnit rUnitTrafficLight;
        dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond()); //changing all traffic lights every second

        //if an RUnit has a Traffic Light, prints it
        if (dataAndStructures.getRoadNetworkManager().getRoadNetwork()!=null) {
            if(dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().size()>0) {
                for (int i = 0; i < dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().size(); i++) {
                    rUnitTrafficLight = dataAndStructures.getRoadNetworkManager().getRUnitByID(i + ""); //get RUnit
                    System.out.print("second: " + currentSecond + " Traffic Light ID: " + (rUnitTrafficLight.getTrafficLight() != null ? rUnitTrafficLight.getTrafficLight().getTrafficLightID() + " X: " + rUnitTrafficLight.getX() + " Y: " + rUnitTrafficLight.getY() : " No Traffic Light "));
                    System.out.println(" Traffic Light Color: " + (rUnitTrafficLight.getTrafficLight() != null ? (rUnitTrafficLight.go() ? "Green" : "Red") : "Continue, No Traffic Light"));
                }
            }
        }
        //ENDS LORENA TESTING

        //FABIAN
        System.out.println("In rd");
        int highest = 0;
        if (dataAndStructures.getRoadNetworkManager().getRoadNetwork()!=null) {
            if (dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().size()>0) {
                for (Map.Entry<String, RUnit> entry : dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().entrySet()) {
                    if (highest < entry.getValue().getId())
                        highest = entry.getValue().getId();

                }
            }
        }
        System.out.println(highest);
        System.out.println("In chain");
        highest=0;
        if (dataAndStructures.getRoadNetworkManager().getRoadNetwork()!=null) {
            RUnit nnn = dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().get("0");
            while (nnn.getNextRUnitList().size() > 0) {

                nnn = nnn.getNextRUnitList().get(0);
                if (highest < nnn.getId())
                    highest = nnn.getId();
            }
        }
        System.out.println(highest);
        dataAndStructures.getGlobalConfigManager().incrementTick();//adds one to tick with every action performed

        //create new vehicles
        if (dataAndStructures.getVehicles().size() < maxCars) {
            IVehicleManager newVehicle =
                    dataAndStructures.getVehicleFactoryManager().createVehicle(dataAndStructures);

            //newVehicle can be null if the factory decided to not produce the vehicle
            if (newVehicle != null) {

                dataAndStructures.getVehicles().add(newVehicle);
                System.out.println("vehicle Created " + dataAndStructures.getVehicles().size());
            }
        }

        //move the vehicles
        for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
            vehicle.move(dataAndStructures.getSpaceManager(),
                    dataAndStructures.getGlobalConfigManager().getCurrentSecond(),//time
                    dataAndStructures.getGlobalConfigManager());//global config
        }
    }


    public DataAndStructures getDataAndStructures() {
        return dataAndStructures;
    }
}

