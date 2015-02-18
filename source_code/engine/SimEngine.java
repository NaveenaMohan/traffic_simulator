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

        dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond());

        System.out.println("In rd");
        int highest = 0;
        for (Map.Entry<String, RUnit> entry : dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().entrySet())
        {
            if(highest < entry.getValue().getId())
                highest = entry.getValue().getId();

        }
        System.out.println(highest);
        System.out.println("In chain");
        highest=0;
        RUnit nnn = dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().get("0");
        while(nnn.getNextRUnitList().size()>0){

            nnn=nnn.getNextRUnitList().get(0);
            if(highest < nnn.getId())
                highest = nnn.getId();
        }System.out.println(highest);
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

        int i=-1;
        RUnit rUnitTrafficLight;

        dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond()); //changing all traffic lights every second

        //print out all objects in space progress


        for(ObjectInSpace obj : dataAndStructures.getSpaceManager().getObjects())
        {   i++;
            rUnitTrafficLight=dataAndStructures.getRoadNetworkManager().getRUnitByID(i+"");
            System.out.println("second: " + dataAndStructures.getGlobalConfigManager().getCurrentSecond() + " object: " + obj + " x: "+obj.getX()+" y: "+obj.getY());

            System.out.print("second: " + dataAndStructures.getGlobalConfigManager().getCurrentSecond() + " Traffic Light ID: " + (rUnitTrafficLight.getTrafficLight()!=null ? rUnitTrafficLight.getTrafficLight().getTrafficLightID() + " TL_x: " + rUnitTrafficLight.getX() + " TL_y: " + rUnitTrafficLight.getY() : " No Traffic Light "));
            System.out.println("second: " + dataAndStructures.getGlobalConfigManager().getCurrentSecond() + " Traffic Light Color: " + (rUnitTrafficLight.getTrafficLight()!=null ? (rUnitTrafficLight.go() ? "Green" : "Red") : "Continue, No Traffic Light"));
        }

    }


    public DataAndStructures getDataAndStructures() {
        return dataAndStructures;
    }
}

