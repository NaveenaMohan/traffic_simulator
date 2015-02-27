package engine;

import common.Common;
import dataAndStructures.DataAndStructures;
import managers.space.VehicleDirection;
import managers.vehicle.IVehicleManager;

import javax.swing.*;
import java.awt.event.ActionListener;


/**
 * Created by Fabians on 12/02/2015.
 */
public class SimEngine {


    private Timer timer;
    private DataAndStructures dataAndStructures;
    private int previousSecond=0;//use this to make a move every second
    private boolean pause;

    int test = 0;
    private int maxCars = 100;

    public SimEngine(DataAndStructures dataAndStructures) {
        this.dataAndStructures = dataAndStructures;
    }

    public void Play(ActionListener actionListener) {

        //initialise timer
        if(timer==null) {
            timer = new Timer(5, actionListener);
            timer.start();
        }
    }

    public void Pause(){
        pause = true;
    }

    public void Unpause(){
        pause=false;
    }

    public void performAction() {

        if(!pause) {
            //FABIAN
            dataAndStructures.getGlobalConfigManager().incrementTick();//adds one to tick with every action performed

            //create new vehicles
            if (dataAndStructures.getVehicles().size() < maxCars) {
                IVehicleManager newVehicle =
                        dataAndStructures.getVehicleFactoryManager().createVehicle(dataAndStructures);

                //newVehicle can be null if the factory decided to not produce the vehicle
                if (newVehicle != null) {

                    dataAndStructures.getVehicles().add(newVehicle);
                    //System.out.println("vehicle Created " + dataAndStructures.getVehicles().size());
                    // dcp=new DCP(dataAndStructures.getVehicles().get(0).getVehicle());

                }
            }

            //move the vehicles
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                vehicle.move(dataAndStructures.getSpaceManager(),
                        dataAndStructures.getGlobalConfigManager().getCurrentSecond(),//time
                        dataAndStructures);//global config
                // dcp.getVehiclesAvgSpeed(vehicle.getVehicle()); //REPORTING VELOCITY
            }

            //change traffic lights
            dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond());


            if ((int) dataAndStructures.getGlobalConfigManager().getCurrentSecond() > previousSecond) {
                System.out.println("--------------------------------- " + dataAndStructures.getGlobalConfigManager().getCurrentSecond());
                for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {//vid, rUnit, x,y, depth, velocity, strategy, distance
                    System.out.println("vid: " + vehicle.getVehID() + " rUnit: " + vehicle.getVehicle().getrUnit()
                            + " x: " + vehicle.getVehicle().getrUnit().getX() + " y: " + vehicle.getVehicle().getrUnit().getY()
                            + " v: " + vehicle.getVehicle().getCurrentVelocity() +
                            " dep: " + vehicle.getVehicle().getDepthInCurrentRUnit() + " dir: " + vehicle.getDirection().getAngle() +
                            " s: " + vehicle.getVehicle().getCurrentStrategy());
                }
            }
            previousSecond = (int) dataAndStructures.getGlobalConfigManager().getCurrentSecond();
        }
   }


    public DataAndStructures getDataAndStructures() {
        return dataAndStructures;
    }
}

