package managers.roadnetwork;

import managers.runit.IRUnitManager;
import managers.runit.TrafficLight;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by naveena on 09/02/15.
 */
public class RoadNetwork implements Serializable {


    private Hashtable<String, IRUnitManager> rUnitHashtable = new Hashtable<String, IRUnitManager>();

    private Hashtable<String, IRUnitManager> changeableRUnitHashtable = new Hashtable<String, IRUnitManager>();

    private Hashtable<String, TrafficLight> trafficLightHashtable = new Hashtable<String, TrafficLight>();

    public Hashtable<String, IRUnitManager> getrUnitHashtable() {
        return rUnitHashtable;
    }

    public void setrUnitHashtable(Hashtable<String, IRUnitManager> rUnitHashtable) {
        this.rUnitHashtable = rUnitHashtable;
    }

    public Hashtable<String, TrafficLight> getTrafficLightHashtable() {
        return trafficLightHashtable;
    }

    public void setTrafficLightHashtable(Hashtable<String, TrafficLight> trafficLightHashtable) {
        this.trafficLightHashtable = trafficLightHashtable;
    }

    public Hashtable<String, IRUnitManager> getChangeableRUnitHashtable() {
        return changeableRUnitHashtable;
    }

    public void setChangeableRUnitHashtable(Hashtable<String, IRUnitManager> changeableRUnitHashtable) {
        this.changeableRUnitHashtable = changeableRUnitHashtable;
    }
}