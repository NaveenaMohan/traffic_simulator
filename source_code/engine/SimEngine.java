package engine;

import dataAndStructures.DataAndStructures;
import managers.globalconfig.*;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.VehicleFactoryManager;
import reports.DCP;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Created by Fabians on 12/02/2015.
 */
public class SimEngine {


    private Timer timer;
    private DataAndStructures dataAndStructures;
    private boolean pause;
    private DCP dcp;


    public SimEngine(DataAndStructures dataAndStructures, DCP dcp) {
        this.dataAndStructures = dataAndStructures;
        this.dcp = dcp;
    }

    public void Play(ActionListener actionListener) {

//
//        for (IRUnitManager rUnit : dataAndStructures.getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
//            String ns = "";
//            for (IRUnitManager nexts : rUnit.getNextRUnitList())
//                ns += nexts.getId() + " ";
//            String ps = "";
//            for (IRUnitManager prevs : rUnit.getNextRUnitList())
//                ps = prevs.getId() + " ";
//            System.out.println("id: " + rUnit.getId() + " x: " + rUnit.getX() + " y: " + rUnit.getY() + " N(" + ns + ") P(" + ps + ")");
//        }
        //initialise timer
        if (timer == null) {
            timer = new Timer(5, actionListener);
            timer.start();
        }
    }

    public void Pause() {
        pause = true;
    }

    public void Unpause() {
        pause = false;
    }

    public void SpeedUp() {
        dataAndStructures.getGlobalConfigManager().setTicksPerSecond(Math.max(5,
                dataAndStructures.getGlobalConfigManager().getTicksPerSecond() / 2));
    }

    public void SlowDown() {
        dataAndStructures.getGlobalConfigManager().setTicksPerSecond(dataAndStructures.getGlobalConfigManager().getTicksPerSecond() * 2);
    }

    public void CleanAll() {
        /*
    this function cleans all the data structures and sets them to their initial state
     */
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

        if (timer != null) {
            timer.stop();
        }
    }

    public void CleanVehicles() {
        dataAndStructures.setVehicleManagerList((new ArrayList<IVehicleManager>()));
        if (timer != null) {
            timer.stop();
        }
    }

    public void performAction() {
        if (!pause) {
            //increment tick
            dataAndStructures.getGlobalConfigManager().incrementTick();
            //update Report data
            dcp.updateReportingInfo(dataAndStructures);

            //create a new vehicle
            IVehicleManager newVehicle =
                    dataAndStructures.getVehicleFactoryManager().createVehicle(dataAndStructures);
            //newVehicle can be null if the factory decided to not produce the vehicle
            if (newVehicle != null) {
                dataAndStructures.getVehicles().add(newVehicle);
            }

            //move the vehicles
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                if (vehicle.isVisible())
                    vehicle.move(dataAndStructures.getSpaceManager(),
                            dataAndStructures.getGlobalConfigManager().getCurrentSecond(),//time
                            dataAndStructures);//global config
            }
            //change traffic lights
            dataAndStructures.getRoadNetworkManager().changeLight(dataAndStructures.getGlobalConfigManager()
                    .getCurrentSecond());
        }
    }


    public DataAndStructures getDataAndStructures() {
        return dataAndStructures;
    }
}

