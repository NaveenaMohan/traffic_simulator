package managers.globalconfig;


import java.io.Serializable;

/**
 * Created by naveena on 08/02/15.
 */
public interface IGlobalConfigManager extends Serializable {

    public void addOrUpdateWeather(ClimaticCondition climaticCondition);

    public ClimaticCondition getClimaticCondition();

    public DriverBehavior getDriverBehaviour();

    public VehicleDensity getVehicleDensity();

    public Route getRoute();

    public double getCurrentSecond();

    public void incrementTick();

    public double getLengthMetres(int rUnits);

    public double getLengthRUnits(double metres);

    public int getTicksPerSecond();

    public void setTicksPerSecond(int ticksPerSecond);

    public Long getCurrentTick();

    public double getMetresPerRUnit();


}
