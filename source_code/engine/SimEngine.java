package engine;

import dataAndStructures.DataAndStructures;
import managers.runit.RUnit;
import managers.runit.TrafficLight;
import managers.space.ObjectInSpace;
import managers.vehicle.IVehicleManager;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Fabians on 12/02/2015.
 */
public class SimEngine {


    private Timer timer;
    private DataAndStructures dataAndStructures;
    private Long previousSecond=0L;//use this to make a move every second

    private int maxCars = 20;

    public SimEngine(DataAndStructures dataAndStructures) {
        this.dataAndStructures = dataAndStructures;
    }

    public void Play(ActionListener actionListener) {

        //initialise timer
        timer = new Timer(5, actionListener);
        timer.start();
    }



    public void performAction() {
        dataAndStructures.getGlobalConfigManager().incrementTick();//adds one to tick with every action performed

        dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond());

        if(dataAndStructures.getGlobalConfigManager().getCurrentSecond()>previousSecond)//move once a second
        {

            //create new vehicles
            if(dataAndStructures.getVehicles().size() < maxCars) {
                IVehicleManager newVehicle =
                        dataAndStructures.getVehicleFactoryManager().createVehicle(
                                dataAndStructures.getGlobalConfigManager(),
                                dataAndStructures.getSpaceManager());

                //newVehicle can be null if the factory decided to not produce the vehicle
                if (newVehicle != null)
                    dataAndStructures.getVehicles().add(newVehicle);
            }

            //move the vehicles
            for(IVehicleManager vehicle : dataAndStructures.getVehicles())
            {

                vehicle.move(dataAndStructures.getSpaceManager(),
                        dataAndStructures.getGlobalConfigManager().getCurrentSecond(),//time
                        null);//climatic condition
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


            previousSecond = dataAndStructures.getGlobalConfigManager().getCurrentSecond();//update previous second
        }
    }


    public DataAndStructures getDataAndStructures() {
        return dataAndStructures;
    }
}
