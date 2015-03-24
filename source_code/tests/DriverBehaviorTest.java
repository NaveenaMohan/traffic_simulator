package tests;

import junit.framework.Assert;
import managers.globalconfig.DriverBehavior;
import managers.globalconfig.DriverBehaviorType;
import org.junit.Before;
import org.junit.Test;

public class DriverBehaviorTest {

    private DriverBehavior driverBehavior;

    @Before
    public void setUp() throws Exception {
        driverBehavior = new DriverBehavior();
    }

    @Test
    public void test1GetRandomSpeedOffsetForDriverType() throws Exception {
        //checking Random speed off set for a reckless driver
        double minSpeedOffSet = 1.01;
        double maxSpeedOffSet = 1.02;
        double actual = driverBehavior.getRandomSpeedOffsetForDriverType(DriverBehaviorType.reckless);
        Assert.assertEquals(minSpeedOffSet <= actual & actual <= maxSpeedOffSet, true);
    }

    @Test
    public void test2GetRandomSpeedOffsetForDriverType() throws Exception {
        //checking Random speed off set for a cautious driver
        double minSpeedOffSet = 0.97;
        double maxSpeedOffSet = 0.99;
        double actual = driverBehavior.getRandomSpeedOffsetForDriverType(DriverBehaviorType.cautious);
        Assert.assertEquals(minSpeedOffSet <= actual & actual <= maxSpeedOffSet, true);
    }

    @Test
    public void test3GetRandomSpeedOffsetForDriverType() throws Exception {
        //checking Random speed off set for a normal driver
        double minSpeedOffSet = 0.99;
        double maxSpeedOffSet = 1.01;
        double actual = driverBehavior.getRandomSpeedOffsetForDriverType(DriverBehaviorType.normal);
        Assert.assertEquals(minSpeedOffSet <= actual & actual <= maxSpeedOffSet, true);
    }

    @Test
    public void test1GetRandomVisibilityOffsetForDriverType() throws Exception {
        //checking Random Visibility off set for a reckless driver
        double minVisOffSet = 0.95;
        double maxVisOffSet = 0.99;
        double actual = driverBehavior.getRandomVisibilityOffsetForDriverType(DriverBehaviorType.reckless);
        Assert.assertEquals(minVisOffSet <= actual & actual <= maxVisOffSet, true);
    }

    @Test
    public void test2GetRandomVisibilityOffsetForDriverType() throws Exception {
        //checking Random Visibility off set for a cautious driver
        double minAndMaxVisOffSet = 1;
        double actual = driverBehavior.getRandomVisibilityOffsetForDriverType(DriverBehaviorType.cautious);
        Assert.assertEquals(minAndMaxVisOffSet == actual, true);
    }

    @Test
    public void test3GetRandomVisibilityOffsetForDriverType() throws Exception {
        //checking Random Visibility off set for a normal driver
        double minVisOffSet = 0.99;
        double maxVisOffSet = 1.01;
        double actual = driverBehavior.getRandomVisibilityOffsetForDriverType(DriverBehaviorType.normal);
        Assert.assertEquals(minVisOffSet <= actual & actual <= maxVisOffSet, true);
    }

    @Test
    public void test1GetRandomReactionTimeOffsetForDriverType() throws Exception {
        //checking Random Reaction off set for a reckless driver
        double minReactOffSet = 0.95;
        double maxReactOffSet = 0.99;
        double actual = driverBehavior.getRandomReactionTimeOffsetForDriverType(DriverBehaviorType.reckless);
        Assert.assertEquals(minReactOffSet <= actual & actual <= maxReactOffSet, true);
    }

    @Test
    public void test2GetRandomReactionTimeOffsetForDriverType() throws Exception {
        //checking Random Reaction off set for a cautious driver
        double minReactOffSet = 1.01;
        double maxReactOffSet = 1.02;
        double actual = driverBehavior.getRandomReactionTimeOffsetForDriverType(DriverBehaviorType.cautious);
        Assert.assertEquals(minReactOffSet <= actual & actual <= maxReactOffSet, true);
    }

    @Test
    public void test3GetRandomReactionTimeOffsetForDriverType() throws Exception {
        //checking Random Reaction off set for a normal driver
        double minReactOffSet = 0.99;
        double maxReactOffSet = 1.01;
        double actual = driverBehavior.getRandomReactionTimeOffsetForDriverType(DriverBehaviorType.normal);
        Assert.assertEquals(minReactOffSet <= actual & actual <= maxReactOffSet, true);
    }
}