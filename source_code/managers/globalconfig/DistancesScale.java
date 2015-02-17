package managers.globalconfig;

/**
 * Created by Fabians on 16/02/2015.
 */
public class DistancesScale {
    private double metresPerRUnit;

    public DistancesScale(double metresPerRUnit) {
        this.metresPerRUnit = metresPerRUnit;
    }

    public double getMetresPerRUnit()
    {
        return metresPerRUnit;
    }

    public double getLengthMetres(int rUnits)
    {
        return rUnits*metresPerRUnit;
    }

    public double getLengthRUnits(double metres)
    {
        return metres/metresPerRUnit;
    }
}
