package managers.globalconfig;



import java.io.Serializable;
import java.util.Map;

/**
 * Created by naveena on 08/02/15.
 */
public interface IGlobalConfigManager extends Serializable {

    public ClimaticCondition getClimaticCondition();

    public DriverBehavior getDriverBehaviour();

    public VehicleDensity getVehicleDensity();

    public Route getRoute();

    double getCurrentSecond();

    void incrementTick();

    int getTicksPerSecond();

    void setTicksPerSecond(int ticksPerSecond);

    double getMetresPerRUnit();
}
