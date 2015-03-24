package tests;

import junit.framework.Assert;
import managers.space.VehicleDirection;
import org.junit.Before;
import org.junit.Test;

public class VehicleDirectionTest {

    VehicleDirection vehicleDirection;

    @Before
    public void setUp() throws Exception {
        int x1 = 12, x2 = 13, y1 = 65, y2 = 66;
        vehicleDirection = new VehicleDirection(x1, y1, x2, y2);
    }

    @Test
    public void testGetDifference() throws Exception {
        double dif = 0;
        dif = vehicleDirection.getDifference(30);
        Assert.assertEquals(dif == -15, true);
    }
}