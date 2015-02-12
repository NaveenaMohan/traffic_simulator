package engine;

import dataAndStructures.DataAndStructures;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.IVehicleFactoryManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class SimEngine implements ActionListener {

    //attributes
    private Timer timer;
    private DataAndStructures dataAndStructures;
    //add space and others


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
        System.out.println("koko");
        for(IVehicleManager vehicle : dataAndStructures.getVehicles())
        {
            vehicle.move(null);
        }
    }
//actionPerformed
}
