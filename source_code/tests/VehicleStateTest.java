package tests;

import dataAndStructures.DataAndStructures;
import dataAndStructures.IDataAndStructures;
import junit.framework.Assert;
import managers.globalconfig.*;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.Blockage;
import managers.runit.RUnit;
import managers.runit.SpeedLimitSign;
import managers.runit.StopSign;
import managers.space.ObjectInSpace;
import managers.space.SpaceManager;
import managers.space.VehicleDirection;
import managers.vehicle.Driver;
import managers.vehicle.VehicleMemoryObject;
import managers.vehicle.VehicleMotor;
import managers.vehicle.VehicleState;
import org.junit.Before;
import org.junit.Test;

public class VehicleStateTest {

    private IRoadNetworkManager roadNetwork;
    private VehicleState vehicleState = new VehicleState();
    private Driver driver = new Driver(0,0,0, DriverBehaviorType.normal);
    private SpaceManager spaceManager = new SpaceManager();
    private VehicleMotor vehicleMotor;
    private IDataAndStructures dataAndStructures;
    private IGlobalConfigManager globalConfigManager;

    @Before
    public void setUp() throws Exception {

        globalConfigManager = new GlobalConfigManager(1,1,new ClimaticCondition(), null, null, null);
        roadNetwork = new RoadNetworkManager(new RoadNetwork());
        dataAndStructures = new DataAndStructures(roadNetwork,null,globalConfigManager);
        vehicleMotor= new VehicleMotor(1, 1, 1, "", new ObjectInSpace(1, 1, 1, 1, 1, 1, 1,
                new VehicleDirection(1,1,2,2), VehicleType.car), 1,false);



        RUnit prev = null;

        //build a road of rUnits
        for (int i = 0; i < 30; i++) {
            prev = roadNetwork.addSingleLane(10, 10 + i, prev);
        }
    }

    @Test
    public void testRegisterObject() throws Exception {
        //assert that no exception is thrown
        String rUnitID="10";
        RUnit rUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(rUnitID);
        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new Blockage(),10,0, false, true));
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void testCleanObjectsAhead() throws Exception {
        String rUnitID="10";
        RUnit rUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(rUnitID);
        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new Blockage(),10,0, false, true));
        vehicleState.cleanObjectsAhead();

        Assert.assertEquals(vehicleState.getSlowestWithin(100, true, true), null);
    }

    @Test
    public void testNextObjectWithin() throws Exception {
        String blockageID="10";

        RUnit blockedRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(blockageID);
        vehicleState.registerObject(new VehicleMemoryObject(blockedRUnit, new Blockage(),10,0, false, true));

        Object receivedObject = vehicleState.nextObjectWithin(100, false, true).getObject();
        Assert.assertEquals(receivedObject instanceof Blockage, true);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test2NextObjectWithin() throws Exception {
        String blockageID="10";

        RUnit blockedRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(blockageID);
        vehicleState.registerObject(new VehicleMemoryObject(blockedRUnit, new Blockage(),10,0, false, true));

        Object receivedObject = vehicleState.nextObjectWithin(2, false, true);
        Assert.assertEquals(receivedObject, null);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test3NextObjectWithin() throws Exception {
        String blockageID="10";

        RUnit blockedRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(blockageID);
        vehicleState.registerObject(new VehicleMemoryObject(blockedRUnit, new Blockage(),10,0, false, true));

        Object receivedObject = vehicleState.nextObjectWithin(100, false, false);
        Assert.assertEquals(receivedObject, null);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test4NextObjectWithin() throws Exception {
        String blockageID="10";

        RUnit blockedRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(blockageID);
        vehicleState.registerObject(new VehicleMemoryObject(blockedRUnit, new Blockage(), 10,0, false, true));

        Object receivedObject = vehicleState.nextObjectWithin(1, false, false);
        Assert.assertEquals(receivedObject instanceof Blockage, false);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void testGetNextSpeedAffectingRoadElement() throws Exception {
        String stopSignRUnitID="10";

        RUnit stopsignRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(stopSignRUnitID);
        vehicleState.registerObject(new VehicleMemoryObject(stopsignRUnit, new StopSign(), 10,0,true, true));

        Object receivedObject = vehicleState.getNextSpeedAffectingRoadElement(20, true).getObject();

        Assert.assertEquals(receivedObject instanceof StopSign, true);
    }

    @Test
    public void test2GetNextSpeedAffectingRoadElement() throws Exception {
        String speedSignRUnitID="10";

        RUnit speedsignRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(speedSignRUnitID);
        vehicleState.registerObject(new VehicleMemoryObject(speedsignRUnit, new SpeedLimitSign(10), 10,0,true, true));

        Object receivedObject = vehicleState.getNextSpeedAffectingRoadElement(20, true).getObject();

        Assert.assertEquals(receivedObject instanceof SpeedLimitSign, true);
    }

    @Test
     public void testGetSlowestWithin() throws Exception {
        RUnit rUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get("10");

        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new Blockage(),10,0, false, true));
        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new SpeedLimitSign(10),12,0, false, true));

        Object receivedObject = vehicleState.getSlowestWithin(100, false, true).getObject();
        Assert.assertEquals(receivedObject instanceof Blockage, true);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test2GetSlowestWithin() throws Exception {
        RUnit rUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get("10");

        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new Blockage(),10,0, false, true));
        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new SpeedLimitSign(10),8,0, false, true));

        Object receivedObject = vehicleState.getSlowestWithin(100, false, true).getObject();
        Assert.assertEquals(receivedObject instanceof Blockage, true);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test3GetSlowestWithin() throws Exception {
        RUnit rUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get("10");

        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new Blockage(),20,0, false, true));
        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new SpeedLimitSign(10),12,0, false, true));

        Object receivedObject = vehicleState.getSlowestWithin(100, false, true).getObject();
        Assert.assertEquals(receivedObject instanceof Blockage, true);
        vehicleState.cleanObjectsAhead();
    }

    @Test
    public void test4GetSlowestWithin() throws Exception {
        RUnit rUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get("10");

        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new SpeedLimitSign(20),10,0, false, true));
        vehicleState.registerObject(new VehicleMemoryObject(rUnit, new SpeedLimitSign(10),12,0, false, true));

        Object receivedObject = vehicleState.getSlowestWithin(100, false, true).getObject();
        Assert.assertEquals(receivedObject instanceof SpeedLimitSign, true);
        vehicleState.cleanObjectsAhead();
    }
}