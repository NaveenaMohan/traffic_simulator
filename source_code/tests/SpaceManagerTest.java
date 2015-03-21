package tests;

import junit.framework.Assert;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.space.SpaceManager;
import org.junit.Before;
import org.junit.Test;

public class SpaceManagerTest {

    ISpaceManager spaceManager;

    @Before
    public void setUp() throws Exception {

        spaceManager = new SpaceManager();
        for (int i = 0; i < 20; i++) {
            spaceManager.addObjectToSpace(new ObjectInSpace(i, 10, 10 + i, 1, 1, 1, 1, null, null));
        }

    }

    /*
        This function returns tru if object does not overlap with other objects in space
        CheckFit Normal Cases
         */
    @Test
    public void test1CheckFit() throws Exception {
        int id = 22;
        int x = 10;
        int y = 10;
        int w = 1;
        int l = 1;
        Assert.assertEquals(spaceManager.checkFit(id, x, y, w, l), false);
    }

    @Test
    public void test2CheckFit() throws Exception {
        int id = 0;
        int x = 9;
        int y = 9;
        int w = 1;
        int l = 1;
        Assert.assertEquals(spaceManager.checkFit(id, x, y, w, l), true);
    }

    @Test
    public void test3CheckFit() throws Exception {
        int id = 22;
        int x = 12;
        int y = 10;
        int w = 1;
        int l = 1;
        Assert.assertEquals(spaceManager.checkFit(id, x, y, w, l), true);
    }

    @Test
    public void test4CheckFit() throws Exception {
        int id = 22;
        int x = 10;
        int y = 10;
        int w = 10;
        int l = 1;
        Assert.assertEquals(spaceManager.checkFit(id, x, y, w, l), false);
    }

    /*
    CheckFit Boundary cases
     */
    @Test
    public void test5CheckFit() throws Exception {
        int id = 22;
        int x = 10;
        int y = 10;
        int w = -1;
        int l = -1;
        Assert.assertEquals(spaceManager.checkFit(id, x, y, w, l), true);
    }

    @Test
    public void test6CheckFit() throws Exception {
        int id = 0;
        int x = 0;
        int y = 0;
        int w = 0;
        int l = 0;
        Assert.assertEquals(spaceManager.checkFit(id, x, y, w, l), true);
    }

    /*
    Returns an object with center at x and y that is different from the given ID
    GetObjectAt Normal Cases
     */
    @Test
    public void test1GetObjectAt() throws Exception {
        int id = 1;
        int x = 10;
        int y = 10;

        int expectedID = 0;
        Assert.assertEquals(spaceManager.getObjectAt(id, x, y).getId(), expectedID);

    }

    @Test(expected = NullPointerException.class)
    public void test2GetObjectAt() throws Exception {
        int id = 1;
        int x = 9;
        int y = 9;

        int expectedID = 0;
        Assert.assertEquals(spaceManager.getObjectAt(id, x, y).getId(), expectedID);
    }

    @Test
    public void test3GetObjectAt() throws Exception {
        int id = 2;
        int x = 10;
        int y = 11;

        int expectedID = 1;
        Assert.assertEquals(spaceManager.getObjectAt(id, x, y).getId(), expectedID);
    }

    /*
    Returns an object with center at x and y that is different from the given ID
    GetObjectAt Boundary Cases
     */
    @Test(expected = NullPointerException.class)
    public void test4GetObjectAt() throws Exception {
        int id = 0;
        int x = 0;
        int y = 0;

        int expectedID = 0;
        Assert.assertEquals(spaceManager.getObjectAt(id, x, y).getId(), expectedID);
    }

    @Test
    public void test5GetObjectAt() throws Exception {
        int id = -1;
        int x = 0;
        int y = 0;

        int expectedID = 0;
        Assert.assertEquals(spaceManager.getObjectAt(id, x, y), null);
    }
}