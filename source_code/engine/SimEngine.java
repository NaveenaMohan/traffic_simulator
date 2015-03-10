package engine;

import common.Common;
import dataAndStructures.DataAndStructures;
import managers.globalconfig.*;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.VehicleFactoryManager;
import reports.DCP;

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
    private DCP dcp;

    int test = 0;
    private int maxCars =10000 ;

    public SimEngine(DataAndStructures dataAndStructures, DCP dcp) {
        this.dataAndStructures = dataAndStructures;
        this.dcp = dcp;
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

    public void SpeedUp() {
        dataAndStructures.getGlobalConfigManager().setTicksPerSecond(dataAndStructures.getGlobalConfigManager().getTicksPerSecond() / 2);
    }

    public void SlowDown() {
        dataAndStructures.getGlobalConfigManager().setTicksPerSecond(dataAndStructures.getGlobalConfigManager().getTicksPerSecond() * 2);
    }

    public void CleanAll() {
        RoadNetworkManager roadNetworkManager = new RoadNetworkManager(new RoadNetwork());
        VehicleFactoryManager vehicleFactoryManager = new VehicleFactoryManager();
        GlobalConfigManager globalConfigManager = new GlobalConfigManager(
                dataAndStructures.getGlobalConfigManager().getTicksPerSecond(),//ticks per second
                dataAndStructures.getGlobalConfigManager().getMetresPerRUnit(),//metres per RUnit
                new ClimaticCondition(),
                new DriverBehavior(),
                new VehicleDensity(),
                new Route()
        );
        dataAndStructures = new DataAndStructures(roadNetworkManager, vehicleFactoryManager, globalConfigManager);

        timer.stop();
    }

    //TODO do clear vehicles

    public void performAction() {

        if(!pause) {

            //FABIAN
            dataAndStructures.getGlobalConfigManager().incrementTick();//adds one to tick with every action performed

            //Report Update
            dcp.updateReportingInfo(dataAndStructures);

            //create new vehicles
            if (dataAndStructures.getVehicles().size() < maxCars & 1==1) {
                IVehicleManager newVehicle =
                        dataAndStructures.getVehicleFactoryManager().createVehicle(dataAndStructures);

                //newVehicle can be null if the factory decided to not produce the vehicle
                if (newVehicle != null) {
                    dataAndStructures.getVehicles().add(newVehicle);
                }
            }

            //move the vehicles
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                if(vehicle.isVisible())
                    vehicle.move(dataAndStructures.getSpaceManager(),
                            dataAndStructures.getGlobalConfigManager().getCurrentSecond(),//time
                            dataAndStructures);//global config
            }

            //change traffic lights
            dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager().getCurrentSecond());

//            if((int)dataAndStructures.getGlobalConfigManager().getCurrentSecond()>previousSecond)
//            {
                for(IVehicleManager veh : dataAndStructures.getVehicles())
//               // if(veh.getVehicle().getObjectInSpace().getVehicleType() == VehicleType.emergency)
                //if(veh.getVehID()==1)
                //if(veh.getVehicle().getCurrentStrategy().length()>10)
                    System.out.println(dataAndStructures.getGlobalConfigManager().getCurrentSecond() + " " +
                            veh.getVehID() + " " + " s: " + Common.round(veh.getVehicle().getCurrentVelocity(), 2)
                            + " v:" + veh.isVisible() + " rUnit:"
                            + veh.getVehicle().getrUnit().getId() + " dest: " + veh.getVehicle().getDestination() + "" + veh.getVehicle().getCurrentStrategy());
//            //}
            previousSecond = (int) dataAndStructures.getGlobalConfigManager().getCurrentSecond();
        }
   }


    public DataAndStructures getDataAndStructures() {
        return dataAndStructures;
    }
}

