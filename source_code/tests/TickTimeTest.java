package tests;

import junit.framework.Assert;
import managers.globalconfig.TickTime;
import org.junit.Before;
import org.junit.Test;

public class TickTimeTest {

    TickTime tickTime;
    @Before
    public void setUp() throws Exception {

        tickTime=new TickTime(1);
    }

    /*
    IncrementTick test normal cases
     */
    @Test
    public void testIncrementTick() throws Exception {
        int ratio = 1;
        tickTime.setRatio(ratio);
        tickTime.incrementTick();
        Assert.assertEquals(tickTime.getCurrentSecond(), 1.0);
    }
    @Test
    public void test2IncrementTick() throws Exception {
        int ratio = 2;
        tickTime.setRatio(ratio);
        tickTime.incrementTick();
        Assert.assertEquals(tickTime.getCurrentSecond(), 0.5);
    }
    @Test
    public void test3IncrementTick() throws Exception {
        int ratio = 2;
        tickTime.setRatio(ratio);
        tickTime.incrementTick();
        tickTime.incrementTick();
        Assert.assertEquals(tickTime.getCurrentSecond(), 1.0);
    }
    /*
    IncrementTick test boundary cases
     */
    @Test
    public void test4IncrementTick() throws Exception {
        int ratio = 2;
        tickTime.setRatio(ratio);
        Assert.assertEquals(tickTime.getCurrentSecond(), 0.0);
    }

}