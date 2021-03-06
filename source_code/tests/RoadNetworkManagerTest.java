package tests;

import managers.roadnetwork.IRoadNetworkManager;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.TrafficLight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RoadNetworkManagerTest {

    private TrafficLight trafficLight, trafficLight2;
    private RoadNetwork roadNetwork;
    private List<Boolean> cycle = new ArrayList<Boolean>();
    private List<Boolean> cycle2 = new ArrayList<Boolean>();
    private IRoadNetworkManager roadNetworkManager;


    @Before
    public void setUp() throws Exception {
        trafficLight = new TrafficLight();
        trafficLight2 = new TrafficLight();
        roadNetwork = new RoadNetwork();

        //create 2 Traffic Lights wit their cycle and adding them to roadNetwork
        cycle.add(true);
        cycle.add(false);
        cycle.add(true);
        cycle.add(false);
        cycle.add(true);
        cycle.add(false);
        cycle.add(true);
        cycle.add(false);
        cycle.add(true);
        cycle.add(false);
        trafficLight.setCycle(cycle);
        roadNetwork.getTrafficLightHashtable().put("0", trafficLight);


        cycle2.add(false);
        cycle2.add(false);
        cycle2.add(true);
        cycle2.add(false);
        cycle2.add(true);
        cycle2.add(true);
        cycle2.add(true);
        cycle2.add(false);
        cycle2.add(false);
        cycle2.add(true);
        trafficLight2.setCycle(cycle2);
        roadNetwork.getTrafficLightHashtable().put("1", trafficLight2);

        //adding roadNetwork to roadNetworkManager
        roadNetworkManager = new RoadNetworkManager(roadNetwork);
    }

    @Test //testing when currentSecond is 73 4537
    public void test1ChangeLight() throws Exception {
        roadNetworkManager.changeLight(73);
        Assert.assertEquals(true, trafficLight.getTrafficLightCurrentColor());
        Assert.assertEquals(true, trafficLight2.getTrafficLightCurrentColor());
    }

    @Test//testing when currentSecond is 4537
    public void test2ChangeLight() throws Exception {
        roadNetworkManager.changeLight(4537);
        Assert.assertEquals(true, trafficLight.getTrafficLightCurrentColor());
        Assert.assertEquals(false, trafficLight2.getTrafficLightCurrentColor());
    }

}