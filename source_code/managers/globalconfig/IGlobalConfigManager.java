package managers.globalconfig;


import java.io.Serializable;

/**
 * Created by naveena on 08/02/15.
 */
public interface IGlobalConfigManager extends Serializable {

    public ClimaticCondition getClimaticCondition();

    public DriverBehavior getDriverBehaviour();

    public VehicleDensity getVehicleDensity();

    public Route getRoute();

    public double getCurrentSecond();

    public void incrementTick();

    public int getTicksPerSecond();

    public void setTicksPerSecond(int ticksPerSecond);

    public double getMetresPerRUnit();


}
