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

    public void printTrafficLights(){
        int max=50;
        for(int i=0;i<max;i++) {
            if (rUnitHashtable.containsKey(i + ""))
                System.out.println("ID: " + rUnitHashtable.get(i + "").getId() + " X: " + rUnitHashtable.get(i + "").getX() + " Y: " + rUnitHashtable.get(i + "").getY() +
                        " Prev: " + (rUnitHashtable.get(i + "").getPrevsRUnitList().size() > 0 ? rUnitHashtable.get(i + "").getPrevsRUnitList().get(0).getId() : "none") +
                        " Next: " + (rUnitHashtable.get(i + "").getNextRUnitList().size() > 0 ? rUnitHashtable.get(i + "").getNextRUnitList().get(0).getId() : "none") +
                        " Traffic Light ID: " + (rUnitHashtable.get(i+"").getTrafficLight() != null ? rUnitHashtable.get(i+"").getTrafficLight().getTrafficLightID() : " No traffic Light ") +
                        " Traffic Light Current Color: " + (rUnitHashtable.get(i+"").getTrafficLight() != null ? ((rUnitHashtable.get(i+"").getTrafficLight().isGreen()) ? " Green" : " Red") : "Default colour, Green"));

        }
    }
}