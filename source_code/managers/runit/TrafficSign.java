package managers.runit;

import java.io.Serializable;

/**
 * Created by naveena on 09/02/15.
 */
public abstract class TrafficSign implements Serializable {

    private RUnit rUnit;

    public RUnit getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }
}
