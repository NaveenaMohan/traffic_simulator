package managers.runit;

import java.io.Serializable;

/**
 * Created by naveena on 09/02/15.
 */
public abstract class TrafficSign implements Serializable {

    private IRUnitManager rUnit;

    public IRUnitManager getrUnit() {
        return rUnit;
    }

    public void setrUnit(IRUnitManager rUnit) {
        this.rUnit = rUnit;
    }
}
