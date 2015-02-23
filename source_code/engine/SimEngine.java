package engine;

import dataAndStructures.DataAndStructures;
import managers.runit.RUnit;
import managers.runit.TrafficSign;
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

    private int maxCars = 1;

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

        //FABIAN
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
                    dataAndStructures);//global config
        }

        //change traffic lights
        dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond());

        for(Map.Entry<String, TrafficLight> tl : dataAndStructures.getRoadNetworkManager().getRoadNetwork().getTrafficLightHashtable().entrySet())
        {
            if(tl.getValue().isGreen())
                System.out.println("Green");
            else
                System.out.println("Red");
        }
    }


    public DataAndStructures getDataAndStructures() {
        return dataAndStructures;
    }
}

