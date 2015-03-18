package managers.roadnetwork;

import managers.runit.RUnit;
import managers.runit.TrafficLight;

import java.util.Hashtable;

/**
 * Created by naveena on 09/02/15.
 */
public class RoadNetwork {


    private Hashtable<String, RUnit> rUnitHashtable = new Hashtable<String, RUnit>();

    private Hashtable<String, RUnit> changeableRUnitHashtable = new Hashtable<String, RUnit>();

    private Hashtable<String, TrafficLight> trafficLightHashtable = new Hashtable<String, TrafficLight>();

    public Hashtable<String, RUnit> getrUnitHashtable() {
        return rUnitHashtable;
    }

    public void setrUnitHashtable(Hashtable<String, RUnit> rUnitHashtable) {
        this.rUnitHashtable = rUnitHashtable;
    }

    public Hashtable<String, TrafficLight> getTrafficLightHashtable() {
        return trafficLightHashtable;
    }

    public void setTrafficLightHashtable(Hashtable<String, TrafficLight> trafficLightHashtable) {
        this.trafficLightHashtable = trafficLightHashtable;
    }

    public Hashtable<String, RUnit> getChangeableRUnitHashtable() {
        return changeableRUnitHashtable;
    }

    public void setChangeableRUnitHashtable(Hashtable<String, RUnit> changeableRUnitHashtable) {
        this.changeableRUnitHashtable = changeableRUnitHashtable;
    }


}