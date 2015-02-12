package managers.roadnetwork;

import managers.runit.RUnit;
import managers.vehiclefactory.VehicleFactory;
import sun.nio.cs.StreamEncoder;
import sun.security.ssl.Debug;

import java.util.*;

/**
 * Created by naveena on 09/02/15.
 */
public class RoadNetwork {


    private Hashtable<String, RUnit> rUnitHashtable = new Hashtable<String, RUnit>();

    public Hashtable<String, RUnit> getrUnitHashtable() {
        return rUnitHashtable;
    }

    public void setrUnitHashtable(Hashtable<String, RUnit> rUnitHashtable) {
        this.rUnitHashtable = rUnitHashtable;
    }

    public RoadNetwork() {

        int max=50;

        RUnit prevRUnit = null;
        for(int i=0;i<max;i++)
        {
            RUnit currentRUnit = new RUnit(i, 50, 50+i);

            if(prevRUnit!=null)
                currentRUnit.getPrevsRUnitList().add(prevRUnit) ;


            rUnitHashtable.put(i+"", currentRUnit) ;

            if(prevRUnit!=null) {
                prevRUnit.getNextRUnitList().add(currentRUnit);
            }
            prevRUnit = currentRUnit;
        }

        for(int i=0;i<max;i++) {
            if (rUnitHashtable.containsKey(i + ""))
                System.out.println("ID: " + rUnitHashtable.get(i + "").getId() + " X: " + rUnitHashtable.get(i + "").getX() + " Y: " + rUnitHashtable.get(i + "").getY() +
                        " Prev: " + (rUnitHashtable.get(i + "").getPrevsRUnitList().size() > 0 ? rUnitHashtable.get(i + "").getPrevsRUnitList().get(0).getId() : "none") +
                        " Next: " + (rUnitHashtable.get(i + "").getNextRUnitList().size() > 0 ? rUnitHashtable.get(i + "").getNextRUnitList().get(0).getId() : "none"));
        }

        VehicleFactory vehicleFactory=new VehicleFactory(rUnitHashtable.get("0"));

        }
}
