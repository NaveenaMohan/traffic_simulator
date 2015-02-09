package objects;

import java.util.Hashtable;

/**
 * Created by naveena on 09/02/15.
 */
public class RoadNetwork {

    private Hashtable<String, RUnit> rUnitHashtable;

    public Hashtable<String, RUnit> getrUnitHashtable() {
        return rUnitHashtable;
    }

    public void setrUnitHashtable(Hashtable<String, RUnit> rUnitHashtable) {
        this.rUnitHashtable = rUnitHashtable;
    }
}
