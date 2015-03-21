package tests;

import junit.framework.Assert;
import managers.globalconfig.DriverBehaviorType;
import managers.vehicle.Driver;
import managers.vehicle.VehicleMemoryObject;
import org.junit.Before;
import org.junit.Test;

public class DriverTest {

    private Driver normalDriver;
    private Driver recklessDriver;
    private Driver cautiousDriver;

    @Before
    public void setUp() throws Exception {
        normalDriver = new Driver(1, 1, 1, DriverBehaviorType.normal);
        recklessDriver = new Driver(1.02, 0.95, 0.95, DriverBehaviorType.reckless);
        cautiousDriver = new Driver(0.97, 1, 1.02, DriverBehaviorType.cautious);
    }

    /*
    Test Normal Driver
    Normal Cases
     */

    @Test
    public void test1GetDecelerationSafeDistance() throws Exception {

        //object same velocity but very close
        double currentVelocity = 10;
        double requiredVelocity = 10;
        double distance = 10;
        double slipperinessOffset = 0.2;
        //set the object to be not passable so that getStopDistance returns a safe stop distance
        VehicleMemoryObject obstacle = new VehicleMemoryObject(null, null, 0, 0, false, false);

        //accurate measurements are impossible. We will check for boundaries
        double minExpectedDistance = 10.0;
        double maxExpectedDistance = 16.0;
        double actual = normalDriver.getDecelerationSafeDistance(currentVelocity, requiredVelocity, distance, slipperinessOffset, obstacle);
        Assert.assertEquals(minExpectedDistance < actual & actual < maxExpectedDistance,
                true);
    }

    @Test
    public void test2GetDecelerationSafeDistance() throws Exception {

        //object same velocity but far
        double currentVelocity = 10;
        double requiredVelocity = 10;
        double distance = 100;
        double slipperinessOffset = 0.2;
        //set the object to be not passable so that getStopDistance returns a safe stop distance
        VehicleMemoryObject obstacle = new VehicleMemoryObject(null, null, 0, 0, false, false);

        //accurate measurements are impossible. We will check for boundaries
        double minExpectedDistance = 100.0;
        double maxExpectedDistance = 120.0;

        double actual = normalDriver.getDecelerationSafeDistance(currentVelocity, requiredVelocity, distance, slipperinessOffset, obstacle);
        Assert.assertEquals(minExpectedDistance < actual & actual < maxExpectedDistance,
                true);
    }

    @Test
    public void test3GetDecelerationSafeDistance() throws Exception {

        //object slower and close
        double currentVelocity = 20;
        double requiredVelocity = 10;
        double distance = 20;
        double slipperinessOffset = 0.2;
        //set the object to be not passable so that getStopDistance returns a safe stop distance
        VehicleMemoryObject obstacle = new VehicleMemoryObject(null, null, 0, 0, false, false);

        //accurate measurements are impossible. We will check for boundaries
        double minExpectedDistance = 20.0;
        double maxExpectedDistance = 30.0;

        double actual = normalDriver.getDecelerationSafeDistance(currentVelocity, requiredVelocity, distance, slipperinessOffset, obstacle);
        Assert.assertEquals(minExpectedDistance < actual & actual < maxExpectedDistance,
                true);
    }

    /*

    Test Boundary cases
     */

    @Test
    public void test4GetDecelerationSafeDistance() throws Exception {

        double currentVelocity = 20;
        double requiredVelocity = 10;
        double distance = -20;
        double slipperinessOffset = 0.2;
        //set the object to be not passable so that getStopDistance returns a safe stop distance
        VehicleMemoryObject obstacle = new VehicleMemoryObject(null, null, 0, 0, false, false);

        //accurate measurements are impossible. We will check for boundaries
        double minExpectedDistance = 0.0;
        double maxExpectedDistance = 30.0;

        double actual = normalDriver.getDecelerationSafeDistance(currentVelocity, requiredVelocity, distance, slipperinessOffset, obstacle);
        Assert.assertEquals(minExpectedDistance < actual & actual < maxExpectedDistance,
                true);
    }

    @Test
    public void test5GetDecelerationSafeDistance() throws Exception {

        double currentVelocity = 20;
        double requiredVelocity = 0;
        double distance = 0;
        double slipperinessOffset = 0.2;
        //set the object to be not passable so that getStopDistance returns a safe stop distance
        VehicleMemoryObject obstacle = new VehicleMemoryObject(null, null, 0, 0, false, false);

        //accurate measurements are impossible. We will check for boundaries
        double minExpectedDistance = 0.0;
        double maxExpectedDistance = 30.0;

        double actual = normalDriver.getDecelerationSafeDistance(currentVelocity, requiredVelocity, distance, slipperinessOffset, obstacle);
        Assert.assertEquals(minExpectedDistance < actual & actual < maxExpectedDistance,
                true);
    }
}