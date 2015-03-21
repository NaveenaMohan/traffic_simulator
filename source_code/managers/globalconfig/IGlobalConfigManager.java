package managers.globalconfig;


import java.io.Serializable;

/**
 * Created by naveena on 08/02/15.
 */
public interface IGlobalConfigManager extends Serializable {

    void addOrUpdateWeather(ClimaticCondition climaticCondition);

    public ClimaticCondition getClimaticCondition();

    public DriverBehavior getDriverBehaviour();

    public VehicleDensity getVehicleDensity();

    public Route getRoute();

    double getCurrentSecond();

    void incrementTick();

    double getLengthMetres(int rUnits);

    double getLengthRUnits(double metres);

    int getTicksPerSecond();

    void setTicksPerSecond(int ticksPerSecond);

    Long getCurrentTick();

    double getMetresPerRUnit();


}
