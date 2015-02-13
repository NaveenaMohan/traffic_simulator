package engine;

import dataAndStructures.DataAndStructures;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetworkManager;
import managers.space.ObjectInSpace;
import managers.vehicle.IVehicleManager;
import managers.vehicle.Vehicle;
import managers.vehiclefactory.IVehicleFactoryManager;
import managers.vehiclefactory.VehicleFactory;
import managers.vehiclefactory.VehicleFactoryManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class SimEngine extends JFrame implements ActionListener {


    private Timer timer;
    private DataAndStructures dataAndStructures;
    private Long previousSecond=0L;//use this to make a move every second

private int maxCars = 20;

    public SimEngine(DataAndStructures dataAndStructures) {
        this.dataAndStructures = dataAndStructures;
    }

    public void Play(){

        //initialise timer
        timer = new Timer(5, this);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        dataAndStructures.getGlobalConfigManager().incrementTick();//adds one to tick with every action performed

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

            //print out all objects in space progress
            for(ObjectInSpace obj : dataAndStructures.getSpaceManager().getObjects())
            {
                System.out.println("second: " + dataAndStructures.getGlobalConfigManager().getCurrentSecond() + " object: " + obj + " x: "+obj.getX()+" y: "+obj.getY());
            }

            previousSecond = dataAndStructures.getGlobalConfigManager().getCurrentSecond();//update previous second
        }
    }
}
