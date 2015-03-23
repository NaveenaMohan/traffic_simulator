package managers.roadnetwork;

import managers.runit.RUnit;
import managers.runit.TrafficLight;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by naveena on 09/02/15.
 */
public class RoadNetwork implements Serializable {


    private Hashtable<String, RUnit> rUnitHashtable = new Hashtable<String, RUnit>();

    private Hashtable<String, RUnit> changeableRUnitHashtable = new Hashtable<String, RUnit>();

    private Hashtable<String, TrafficLight> trafficLightHashtable = new Hashtable<String, TrafficLight>();

    public Hashtable<String, RUnit> getrUnitHashtable() {
        return rUnitHashtable;
    }

    public Hashtable<String, TrafficLight> getTrafficLightHashtable() {
        return trafficLightHashtable;
    }

    public Hashtable<String, RUnit> getChangeableRUnitHashtable() {
        return changeableRUnitHashtable;
    }
}