package managers.globalconfig;

import java.io.Serializable;

/**
 * Created by Fabians on 16/02/2015.
 */
public class DistancesScale implements Serializable {
    private double metresPerRUnit;

    public DistancesScale(double metresPerRUnit) {
        this.metresPerRUnit = metresPerRUnit;
    }

    public double getMetresPerRUnit() {
        return metresPerRUnit;
    }

    public double getLengthMetres(int rUnits) {
        return rUnits * metresPerRUnit;
    }

    public double getLengthRUnits(double metres) {
        return metres / metresPerRUnit;
    }
}
