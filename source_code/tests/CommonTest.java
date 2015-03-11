package tests;

import common.Common;
import junit.framework.Assert;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.RUnit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ui.Coordinates;

public class CommonTest {

    private IRoadNetworkManager roadNetwork;

    @Rule
    public ExpectedException illegalArgument = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        roadNetwork = new RoadNetworkManager(new RoadNetwork());
        RUnit prev=null;

        //build a road of rUnits
        for (int i = 0; i < 20; i++) {
            prev = roadNetwork.addSingleLane(1, 2+i, prev);
        }

    }

    @Test
    public void testGetNextPointFromTo() throws Exception {
        /*
        This function takes in two Coordinates as parameters and returns the next point on the path from one to another
         */
        /*
        Test normal cases
         */

        //test going right
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(2, 0))), (new Coordinates(1,0)));
        //test going left
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(-2, 0))), (new Coordinates(-1,0)));
        //test going up
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(0, 2))), (new Coordinates(0,1)));
        //test going down
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(0, -2))), (new Coordinates(0,-1)));

        /*
        Test boundary cases
         */
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(2, 2))), (new Coordinates(1,1)));
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(-1, -1)), (new Coordinates(1, 1))), (new Coordinates(0,0)));
   }

    @Test
    public void testNormalizeAngle() throws Exception {
        /*
        This function transforms angles of 0 to 360 to -179 to 180
         */
        /*
        Test normal cases
         */
        Assert.assertEquals(Common.normalizeAngle(0), 0.0);
        Assert.assertEquals(Common.normalizeAngle(20), 20.0);
        Assert.assertEquals(Common.normalizeAngle(180), 180.0);
        Assert.assertEquals(Common.normalizeAngle(181), -179.0);
        Assert.assertEquals(Common.normalizeAngle(360), 0.0);
        Assert.assertEquals(Common.normalizeAngle(200), -160.0);
        Assert.assertEquals(Common.normalizeAngle(300), -60.0);
        /*
        Test boundary cases
         */
        Assert.assertEquals(Common.normalizeAngle(-100), -100.0);
        Assert.assertEquals(Common.normalizeAngle(-1000), 80.0);

    }

    @Test
    public void testGetNthNextRUnit() throws Exception {
        /*
        This function returns the nth next rUnit. It should not go our of range
         */
        /*
        Here we are going to use the roadNetwork rUnits created in setup.
         */
        /*
        Test normal cases
         */
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("1"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("11"));
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("2"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("12"));
        /*
        Test boundary cases
         */
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("11"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"));
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"));
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("notThere"), 10),
                null);


    }

    @Test
    public void testGetNthPrevRUnit() throws Exception {
/*
        This function returns the nth prev rUnit. It should not go our of range
         */
        /*
        Here we are going to use the roadNetwork rUnits created in setup.
         */
        /*
        Test normal cases
         */
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("10"));
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("15"), 5),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("10"));
        /*
        Test boundary cases
         */
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("10"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("1"));
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("5"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("1"));
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("notThere"), 10),
                null);

    }

    @Test
    public void testGetAdjacentPointToB() throws Exception {

        /*
        This function is used for creating double lanes. It takes two points and returns a third point that is at some defined distance and angle to the second point
         */
        /*
        Test normal cases
         */

        //test going right
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(1,1)),(new Coordinates(2,1)), 1, 90), (new Coordinates(2,2)));
        //test going right different bearing
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(1,1)),(new Coordinates(2,1)), 1, -90), (new Coordinates(2,0)));
        //test going right different distance
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(1,1)),(new Coordinates(2,1)), 2, 90), (new Coordinates(2,3)));


        //test going left
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(2,1)),(new Coordinates(1,1)), 1, 90), (new Coordinates(1,0)));

        //test going up
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0,0)),(new Coordinates(0,1)), 1, 90), (new Coordinates(-1,1)));

        //test going down
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0,0)),(new Coordinates(0,-1)), 1, 90), (new Coordinates(1,-1)));

        /*
        Test boundary cases
         */
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0,0)),(new Coordinates(0,0)), 1, 90), (new Coordinates(0,1)));
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0,0)),(new Coordinates(1,0)), -1, 90), (new Coordinates(1,-1)));
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0,0)),(new Coordinates(1,0)), 0, 90), (new Coordinates(1,0)));
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0,0)),(new Coordinates(1,0)), 0, 0), (new Coordinates(1,0)));

    }
}