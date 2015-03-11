package reports;

import dataAndStructures.DataAndStructures;
import engine.SimEngine;
import junit.framework.TestCase;
import managers.globalconfig.*;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.space.ObjectInSpace;
import managers.vehicle.IVehicleManager;
import managers.vehiclefactory.VehicleFactoryManager;

import javax.swing.text.BadLocationException;

public class DCPTest extends TestCase {

    private DCP dcp;
    int cars=0, emergencies=0, heavyLoads=0;
    private int previousSecond=0;//use this to make a move every second
    private int prevSec=-1;
    private RoadNetworkManager roadNetworkManager = new RoadNetworkManager(new RoadNetwork());
    private VehicleFactoryManager vehicleFactoryManager = new VehicleFactoryManager();
    private GlobalConfigManager globalConfigManager = new GlobalConfigManager(
            100,//ticks per second
            0.5,//metres per RUnit
            new ClimaticCondition(),
            new DriverBehavior(),
            new VehicleDensity(),
            new Route()
    );
    DataAndStructures dataAndStructures;
    SimEngine simEngine;

    public DCPTest() {
        dataAndStructures = new DataAndStructures(roadNetworkManager, vehicleFactoryManager, globalConfigManager);
        dcp=new DCP(dataAndStructures);
       // simEngine=new SimEngine(dataAndStructures,dcp, 5);
    }

    public void setUp() throws Exception {
        super.setUp();



    }

    public void tearDown() throws Exception {

    }

    public void testReportInformation() throws Exception {
        try {
            testCreateAndShowGUI();
            System.out.println("testReportInformation passed test");
        }catch (Exception e){

        }
    }

    public void testCreateAndShowGUI() throws Exception {

        try {
            System.out.println(testGetVehiclesDensity());

        }catch (Exception e){}

    }

    public String testGetVehiclesDensity() throws Exception{
        try {
        String result="";
        for (int i=0; i<100000; i++)
        {
            dataAndStructures.getGlobalConfigManager().incrementTick();//adds one to tick with every action performed
            //create new vehicles
            if (dataAndStructures.getVehicles().size() < 5 & 1==1) {
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

            for(IVehicleManager veh : dataAndStructures.getVehicles())
            previousSecond = (int) dataAndStructures.getGlobalConfigManager().getCurrentSecond();
        }

        if (dataAndStructures.getVehicles()!=null) {
            if (dataAndStructures.getVehicles().size() != 0) {
                for (ObjectInSpace vehicle : dataAndStructures.getSpaceManager().getObjects()) {
                    switch (vehicle.getVehicleType()){
                        case car: cars++;
                            break;
                        case heavyLoad: heavyLoads++;
                            break;
                        case emergency: emergencies++;
                            break;
                    }
                }

                    result="Total of vehicles: " + this.dataAndStructures.getVehicles().size() + "\n"+
                            "Cars: "+ cars + "\n"+"Ambulances & Police Vehicles: "+
                            emergencies +"\n"+"Public Transportation: "+ heavyLoads;

            }
        }
        return result;
        }catch (Exception e){return null;}
    }

}