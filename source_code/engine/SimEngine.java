package engine;

import dataAndStructures.DataAndStructures;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.IVehicleFactoryManager;
import managers.vehiclefactory.VehicleFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class SimEngine extends JFrame implements ActionListener {

    //attributes
    private Timer timer;
    private DataAndStructures dataAndStructures;
    //add space and others
private int maxCars = 3;

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

        if(dataAndStructures.getVehicles().size() <= maxCars) {
            dataAndStructures.getVehicles().add(dataAndStructures.getVehicleFactoryManager().createVehicle(null, null, dataAndStructures.getSpaceManager()));
        }
        for(IVehicleManager vehicle : dataAndStructures.getVehicles())
        {

            vehicle.move(null, //space
                    null,//time
                    null);//climatic condition
            System.out.println("Vehicle: " + vehicle + " currentRUnit: " + vehicle.getVehicle().getrUnit().getId() +
            " x: " + vehicle.getVehicle().getrUnit().getX() + " y: " + vehicle.getVehicle().getrUnit().getY());
        }
    }
}
