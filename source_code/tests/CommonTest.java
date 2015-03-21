package tests;

import common.Common;
import junit.framework.Assert;
import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.IRUnitManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ui.Coordinates;

public class CommonTest {

    @Rule
    private ExpectedException illegalArgument = ExpectedException.none();
    private IRoadNetworkManager roadNetwork;

    @Before
    public void setUp() throws Exception {
        roadNetwork = new RoadNetworkManager(new RoadNetwork());
        IRUnitManager prev = null;

        //build a road of rUnits
        for (int i = 0; i < 20; i++) {
            prev = roadNetwork.addSingleLane(1, 2 + i, prev);
        }

    }

    /*
    GetNextPointFromTo Normal Cases

    This function takes in two Coordinates as parameters and returns the next point on the path from one to another
     */
    @Test
    public void test1GetNextPointFromTo() throws Exception {
        //test going right
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(2, 0))), (new Coordinates(1, 0)));
    }

    @Test
    public void test2GetNextPointFromTo() throws Exception {
        //test going left
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(-2, 0))), (new Coordinates(-1, 0)));
    }

    @Test
    public void test3GetNextPointFromTo() throws Exception {
        //test going up
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(0, 2))), (new Coordinates(0, 1)));
    }

    @Test
    public void test4GetNextPointFromTo() throws Exception {
        //test going down
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(0, -2))), (new Coordinates(0, -1)));
    }

    /*
        GetNextPointFromTo Boundary Cases
    */
    @Test
    public void test5GetNextPointFromTo() throws Exception {
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(0, 0)), (new Coordinates(2, 2))), (new Coordinates(1, 1)));
    }

    @Test
    public void test6GetNextPointFromTo() throws Exception {
        Assert.assertEquals(Common.getNextPointFromTo((new Coordinates(-1, -1)), (new Coordinates(1, 1))), (new Coordinates(0, 0)));
    }

    /*
            NormalizeAngle - Normal cases
            This function transforms angles of 0 to 360 to -179 to 180
    */
    @Test
    public void test1NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(0), 0.0);
    }

    @Test
    public void test2NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(20), 20.0);
    }

    @Test
    public void test3NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(180), 180.0);
    }

    @Test
    public void test4NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(181), -179.0);
    }

    @Test
    public void test5NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(360), 0.0);
    }

    @Test
    public void test6NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(200), -160.0);
    }

    @Test
    public void test7NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(300), -60.0);
    }

    /*
    normalizeAngle Test boundary cases
     */
    @Test
    public void test8NormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(-100), -100.0);
    }

    @Test
    public void testNormalizeAngle() throws Exception {
        Assert.assertEquals(Common.normalizeAngle(-1000), 80.0);
    }


    /*
        This function returns the nth next rUnit. It should not go our of range

        GetNthNextRUnit Test normal cases
         */
    @Test
    public void test1GetNthNextRUnit() throws Exception {
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("1"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("11"));
    }

    @Test
    public void test2GetNthNextRUnit() throws Exception {
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("2"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("12"));
    }

    /*
    GetNthNextRUnit Test boundary cases
     */
    @Test
    public void test3GetNthNextRUnit() throws Exception {

        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("11"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"));
    }

    @Test
    public void test4GetNthNextRUnit() throws Exception {
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"));
    }

    @Test
    public void test5GetNthNextRUnit() throws Exception {
        Assert.assertEquals(Common.getNthNextRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("notThere"), 10),
                null);
    }

    /*
    This function returns the nth prev rUnit. It should not go our of range
      Here we are going to use the roadNetwork rUnits created in setup.
        GetNthPrevRUnit Test normal cases
         */
    @Test
    public void test1GetNthPrevRUnit() throws Exception {
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("20"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("10"));
    }

    @Test
    public void test2GetNthPrevRUnit() throws Exception {
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("15"), 5),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("10"));
    }

    /*
        GetNthPrevRUnit Test boundary cases
         */
    @Test
    public void test3GetNthPrevRUnit() throws Exception {
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("10"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("1"));
    }

    @Test
    public void test4GetNthPrevRUnit() throws Exception {
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("5"), 10),
                roadNetwork.getRoadNetwork().getrUnitHashtable().get("1"));
    }

    @Test
    public void test5GetNthPrevRUnit() throws Exception {
        Assert.assertEquals(Common.getNthPrevRUnit(roadNetwork.getRoadNetwork().getrUnitHashtable().get("notThere"), 10),
                null);
    }

    /*
        This function is used for creating double lanes. It takes two points and returns a third point that is at some defined distance and angle to the second point

        GetAdjacentPointToB Test normal cases
    */
    @Test
    public void test1GetAdjacentPointToB() throws Exception {
        //test going right
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(1, 1)), (new Coordinates(2, 1)), 1, 90), (new Coordinates(2, 2)));
    }

    @Test
    public void test2GetAdjacentPointToB() throws Exception {
        //test going right different bearing
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(1, 1)), (new Coordinates(2, 1)), 1, -90), (new Coordinates(2, 0)));
    }

    @Test
    public void test3GetAdjacentPointToB() throws Exception {
        //test going right different distance
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(1, 1)), (new Coordinates(2, 1)), 2, 90), (new Coordinates(2, 3)));
    }

    @Test
    public void test4GetAdjacentPointToB() throws Exception {
        //test going left
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(2, 1)), (new Coordinates(1, 1)), 1, 90), (new Coordinates(1, 0)));
    }

    @Test
    public void test5GetAdjacentPointToB() throws Exception {
        //test going up
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0, 0)), (new Coordinates(0, 1)), 1, 90), (new Coordinates(-1, 1)));
    }

    @Test
    public void test6GetAdjacentPointToB() throws Exception {
        //test going down
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0, 0)), (new Coordinates(0, -1)), 1, 90), (new Coordinates(1, -1)));
    }

    /*
GetAdjacentPointToB Test boundary cases
 */
    @Test
    public void test7GetAdjacentPointToB() throws Exception {
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0, 0)), (new Coordinates(0, 0)), 1, 90), (new Coordinates(0, 1)));
    }

    @Test
    public void test8GetAdjacentPointToB() throws Exception {
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0, 0)), (new Coordinates(1, 0)), -1, 90), (new Coordinates(1, -1)));
    }

    @Test
    public void test9GetAdjacentPointToB() throws Exception {
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0, 0)), (new Coordinates(1, 0)), 0, 90), (new Coordinates(1, 0)));
    }

    @Test
    public void test10GetAdjacentPointToB() throws Exception {
        Assert.assertEquals(Common.getAdjacentPointToB((new Coordinates(0, 0)), (new Coordinates(1, 0)), 0, 0), (new Coordinates(1, 0)));
    }

    /*

    This function returns the angle between two points
    GetAngle Normal Cases
     */

    @Test
    public void testGetAngle() throws Exception {
        Assert.assertEquals(Common.getAngle(1, 1, 2, 2), 45.0);

    }

    @Test
    public void test1GetAngle() throws Exception {
        Assert.assertEquals(Common.getAngle(10, -50, 10, -100), -90.0);

    }

    @Test
    public void test2GetAngle() throws Exception {
        Assert.assertEquals(Common.getAngle(1, 1, 0, 0), -135.0);

    }

    /*
    GetAngle Boundary Cases
     */
    @Test
    public void test3GetAngle() throws Exception {
        Assert.assertEquals(Common.getAngle(0, 0, 0, 0), 0.0);

    }


    /*
        This function returns the difference between the angle and bearing

        GetAngleDifference Normal Cases
         */
    @Test
    public void testGetAngleDifference() throws Exception {
        Assert.assertEquals(Common.getAngleDifference(10, 20), 10.0);
    }

    @Test
    public void test2GetAngleDifference() throws Exception {
        Assert.assertEquals(Common.getAngleDifference(0, 20), 20.0);
    }

    @Test
    public void test3GetAngleDifference() throws Exception {
        Assert.assertEquals(Common.getAngleDifference(20, 10), -10.0);
    }

    /*
        GetAngleDifference Boundary Cases
         */
    @Test
    public void test4GetAngleDifference() throws Exception {
        Assert.assertEquals(Common.getAngleDifference(0, -1), -1.0);
    }

    public void test5GetAngleDifference() throws Exception {
        Assert.assertEquals(Common.getAngleDifference(160, -120), -80.0);
    }
}