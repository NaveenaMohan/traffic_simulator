package tests;

import dataAndStructures.DataAndStructures;
import dataAndStructures.IDataAndStructures;
import junit.framework.Assert;
import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.GlobalConfigManager;
import managers.globalconfig.IGlobalConfigManager;
import managers.globalconfig.VehicleType;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.Blockage;
import managers.runit.IRUnitManager;
import managers.space.ObjectInSpace;
import managers.space.SpaceManager;
import managers.space.VehicleDirection;
import managers.vehicle.VehicleMotor;
import managers.vehicle.VehiclePerception;
import managers.vehicle.VehicleState;
import org.junit.Before;
import org.junit.Test;

public class VehiclePerceptionTest {

    private IRoadNetworkManager roadNetwork;
    private VehicleState vehicleState = new VehicleState();
    private VehiclePerception vehiclePerception = new VehiclePerception();
    private SpaceManager spaceManager = new SpaceManager();
    private VehicleMotor vehicleMotor;
    private ObjectInSpace objectInSpace =new ObjectInSpace(1, 1, 1, 1, 1, 1, 1,
            new VehicleDirection(1,1,2,2), VehicleType.car);
    private IDataAndStructures dataAndStructures;
    private IGlobalConfigManager globalConfigManager;

    @Before
    public void setUp() throws Exception {

        globalConfigManager = new GlobalConfigManager(1,1,new ClimaticCondition(), null, null, null);
        roadNetwork = new RoadNetworkManager(new RoadNetwork());
        dataAndStructures = new DataAndStructures(roadNetwork,null,globalConfigManager);
        vehicleMotor= new VehicleMotor(1, 1, 1, "", objectInSpace, 1,false);


        IRUnitManager prev = null;

        //build a road of rUnits
        for (int i = 0; i < 30; i++) {
            prev = roadNetwork.addSingleLane(10, 10 + i, prev);
        }



    }

    @Test
    public void testSee() throws Exception {
        String currentRUnitNumber = "2";
        String objectRUnit = "10";

        IRUnitManager currentRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(currentRUnitNumber);

        roadNetwork.addBlockage(roadNetwork.getRoadNetwork().getrUnitHashtable().get(objectRUnit));

        vehiclePerception.see(1,currentRUnit,100,vehicleState,spaceManager,dataAndStructures,objectInSpace);
        Assert.assertEquals(vehicleState.nextObjectWithin(100, false, true).getDistance(), 7.0);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test2See() throws Exception {
        String currentRUnitNumber = "2";

        IRUnitManager currentRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(currentRUnitNumber);

        vehiclePerception.see(1,currentRUnit,100,vehicleState,spaceManager,dataAndStructures,objectInSpace);

        Assert.assertEquals(vehicleState.nextObjectWithin(100, false, true), null);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test3See() throws Exception {
        String currentRUnitNumber = "2";
        String objectRUnit = "2";

        IRUnitManager currentRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(currentRUnitNumber);
        int projectedDistance = Integer.parseInt(objectRUnit) - Integer.parseInt(currentRUnitNumber);
        roadNetwork.addBlockage(roadNetwork.getRoadNetwork().getrUnitHashtable().get(objectRUnit));

        vehiclePerception.see(1,currentRUnit,100,vehicleState,spaceManager,dataAndStructures,objectInSpace);

        Assert.assertEquals(vehicleState.nextObjectWithin(100, false, true), null);
        vehicleState.cleanObjectsAhead();

    }

    @Test
    public void test4See() throws Exception {
        String currentRUnitNumber = "2";
        String objectRUnit = "5";

        IRUnitManager currentRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(currentRUnitNumber);

        roadNetwork.addBlockage(roadNetwork.getRoadNetwork().getrUnitHashtable().get(objectRUnit));

        vehiclePerception.see(1,currentRUnit,100,vehicleState,spaceManager,dataAndStructures,objectInSpace);

        Object projectedObject = vehicleState.nextObjectWithin(100, false,  true).getObject();
        Assert.assertEquals(projectedObject instanceof Blockage, true);
        vehicleState.cleanObjectsAhead();
    }


}