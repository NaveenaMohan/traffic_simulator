package tests;

import junit.framework.Assert;
import managers.globalconfig.DriverBehaviorType;
import managers.globalconfig.VehicleType;
import managers.runit.RUnit;
import managers.space.SpaceManager;
import managers.vehicle.Driver;
import managers.vehiclefactory.VehicleFactory;
import org.junit.Before;
import org.junit.Test;

public class VehicleFactoryTest {
    private VehicleFactory vehicleFactory;
    private SpaceManager spaceManager;

    @Before
    public void setUp() throws Exception {
        vehicleFactory = new VehicleFactory(new RUnit("0", 1, 1));
        spaceManager = new SpaceManager();
    }

    @Test
    public void test1AddVehicle() throws Exception {
        //checking if vehicles are created
        boolean created = false;
        if (vehicleFactory.addVehicle(1, VehicleType.car, new Driver(1, 1, 1, DriverBehaviorType.normal), "London", spaceManager, 0.123) != null)
            ;
        created = true;
        Assert.assertEquals(created, true);
    }
}