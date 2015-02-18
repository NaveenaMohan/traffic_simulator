package managers.roadnetwork;

import managers.runit.RUnit;
import managers.runit.TrafficLight;
import managers.vehiclefactory.VehicleFactory;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by naveena on 09/02/15.
 */
public class RoadNetwork {


    private Hashtable<String, RUnit> rUnitHashtable = new Hashtable<String, RUnit>();

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
//TODO : remove the below

    public RoadNetwork() {

        int max = 50;

        RUnit prevRUnit = null;
        for (int i = 0; i < max; i++) {
            RUnit currentRUnit = new RUnit(i, 50, 50 + i);

            if (prevRUnit != null)
                currentRUnit.getPrevsRUnitList().add(prevRUnit);


            rUnitHashtable.put(i + "", currentRUnit);

            if (prevRUnit != null) {
                prevRUnit.getNextRUnitList().add(currentRUnit);
            }
            prevRUnit = currentRUnit;
        }

        /*for (int i = 0; i < max; i++) {
            if (rUnitHashtable.containsKey(i + ""))
                System.out.println("ID: " + rUnitHashtable.get(i + "").getId() + " X: " + rUnitHashtable.get(i + "").getX() + " Y: " + rUnitHashtable.get(i + "").getY() +
                        " Prev: " + (rUnitHashtable.get(i + "").getPrevsRUnitList().size() > 0 ? rUnitHashtable.get(i + "").getPrevsRUnitList().get(0).getId() : "none") +
                        " Next: " + (rUnitHashtable.get(i + "").getNextRUnitList().size() > 0 ? rUnitHashtable.get(i + "").getNextRUnitList().get(0).getId() : "none") +
                        " Traffic Light ID: " + ((trafficLightHashtable.containsKey(i + "")) ? trafficLightHashtable.get(i + "").getTrafficLightID() : " No traffic Light ") +
                        " Traffic Light Current Color: " + ((trafficLightHashtable.containsKey(i + "")) ? ((trafficLightHashtable.get(i + "").isGreen()) ? " Green" : " Red") : "No traffic Light"));

        }*/

        VehicleFactory vehicleFactory = new VehicleFactory(rUnitHashtable.get("0"));

       /* for(RUnit rUnit : rUnitHashtable.values()){
            System.out.println("ID: " + rUnit.getId() + " X: " + rUnit.getX() + " Y: " + rUnit.getY() +
                    " Prev: " + (rUnit.getPrevsRUnitList().size() > 0 ? rUnit.getPrevsRUnitList().get(0).getId() : "none") +
                    " Next: " + (rUnit.getNextRUnitList().size() > 0 ? rUnit.getNextRUnitList().get(0).getId() : "none"));
        }
        */
    }
}