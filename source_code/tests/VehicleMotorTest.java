package tests;

import dataAndStructures.DataAndStructures;
import dataAndStructures.IDataAndStructures;
import junit.framework.Assert;
import managers.globalconfig.*;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.IRUnitManager;
import managers.space.ObjectInSpace;
import managers.space.SpaceManager;
import managers.space.VehicleDirection;
import managers.vehicle.Driver;
import managers.vehicle.VehicleMotor;
import managers.vehicle.VehicleState;
import org.junit.Before;
import org.junit.Test;

public class VehicleMotorTest {

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


        IRUnitManager prev = null;

        //build a road of rUnits
        for (int i = 0; i < 30; i++) {
            prev = roadNetwork.addSingleLane(10, 10 + i, prev);
        }
        prev = null;

        //build a road of rUnits
        for (int i = 0; i < 5; i++) {
            prev = roadNetwork.addSingleLane(8+i, 12, prev);
        }

        //this way rUnits will intersect at point 3 (10, 12) n:4 34  p: 1
    }

    @Test
    public void testChooseNext() throws Exception {
        String startingID="3";
        String requiredID="34";

        vehicleState.setNextRUnitAfterDecisionPoint(roadNetwork.getRoadNetwork().getrUnitHashtable().get(requiredID));
            Assert.assertEquals(VehicleMotor.chooseNext(roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID), vehicleState),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(requiredID));
        vehicleState.setNextRUnitAfterDecisionPoint(null);

    }

    @Test
    public void test2ChooseNext() throws Exception {
        String startingID="3";
        String requiredID="4";

        vehicleState.setNextRUnitAfterDecisionPoint(roadNetwork.getRoadNetwork().getrUnitHashtable().get(requiredID));
        Assert.assertEquals(VehicleMotor.chooseNext(roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID), vehicleState),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(requiredID));
        vehicleState.setNextRUnitAfterDecisionPoint(null);

    }

    @Test
    public void test3ChooseNext() throws Exception {
        String startingID="4";
        String requiredID="3";
        String projectedID="5";

        vehicleState.setNextRUnitAfterDecisionPoint(roadNetwork.getRoadNetwork().getrUnitHashtable().get(requiredID));
        Assert.assertEquals(VehicleMotor.chooseNext(roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID), vehicleState),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(projectedID));
        vehicleState.setNextRUnitAfterDecisionPoint(null);

    }

    @Test
    public void test4ChooseNext() throws Exception {
        String startingID="4";
        String requiredID="a";
        String projectedID="5";

        vehicleState.setNextRUnitAfterDecisionPoint(roadNetwork.getRoadNetwork().getrUnitHashtable().get(requiredID));
        Assert.assertEquals(VehicleMotor.chooseNext(roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID), vehicleState),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(projectedID));
        vehicleState.setNextRUnitAfterDecisionPoint(null);

    }



    @Test
    public void testPrepareAction() throws Exception {
        String startingID="4";
        IRUnitManager startingRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID);
        Assert.assertEquals(vehicleMotor.prepareAction(startingRUnit, driver, vehicleState, 0, spaceManager, 1),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID));
    }

    @Test(expected=NullPointerException.class)
    public void test2PrepareAction() throws Exception {
        String startingID="411";
        IRUnitManager startingRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID);
        Assert.assertEquals(vehicleMotor.prepareAction(startingRUnit, driver, vehicleState, 0, spaceManager, 1),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID));
    }

    @Test
    public void testPerformAction() throws Exception {
        int CurrentSpeed = 1;
        String startingID="10";
        String projectedID="11";
        IRUnitManager startingRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID);
        vehicleMotor= new VehicleMotor(1, 1, CurrentSpeed, "", new ObjectInSpace(1, 1, 1, 1, 1, 1, 1,
                new VehicleDirection(1,1,2,2), VehicleType.car), 1,false);

        Assert.assertEquals(vehicleMotor.performAction(1,dataAndStructures,startingRUnit, vehicleState).getId(),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(projectedID).getId());
        ;

    }

    @Test
    public void test2PerformAction() throws Exception {
        int CurrentSpeed = 3;
        String startingID="10";
        String projectedID="13";
        IRUnitManager startingRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID);
        vehicleMotor= new VehicleMotor(1, 1, CurrentSpeed, "", new ObjectInSpace(1, 1, 1, 1, 1, 1, 1,
                new VehicleDirection(1,1,2,2), VehicleType.car), 1,false);

        Assert.assertEquals(vehicleMotor.performAction(1,dataAndStructures,startingRUnit, vehicleState).getId(),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(projectedID).getId());
        ;

    }

    @Test
     public void test3PerformAction() throws Exception {
        int CurrentSpeed = 0;
        String startingID="10";
        String projectedID="10";
        IRUnitManager startingRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID);
        vehicleMotor= new VehicleMotor(1, 1, CurrentSpeed, "", new ObjectInSpace(1, 1, 1, 1, 1, 1, 1,
                new VehicleDirection(1,1,2,2), VehicleType.car), 1,false);

        Assert.assertEquals(vehicleMotor.performAction(1,dataAndStructures,startingRUnit, vehicleState).getId(),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(projectedID).getId());


    }

    @Test
    public void test4PerformAction() throws Exception {
        int CurrentSpeed = 1;
        String startingID="3";
        String projectedID="34";
        vehicleState.setNextRUnitAfterDecisionPoint(roadNetwork.getRoadNetwork().getrUnitHashtable().get(projectedID));
        IRUnitManager startingRUnit = roadNetwork.getRoadNetwork().getrUnitHashtable().get(startingID);
        vehicleMotor= new VehicleMotor(1, 1, CurrentSpeed, "", new ObjectInSpace(1, 1, 1, 1, 1, 1, 1,
                new VehicleDirection(1,1,2,2), VehicleType.car), 1,false);


        Assert.assertEquals(vehicleMotor.performAction(1,dataAndStructures,startingRUnit, vehicleState).getId(),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get(projectedID).getId());


    }

}