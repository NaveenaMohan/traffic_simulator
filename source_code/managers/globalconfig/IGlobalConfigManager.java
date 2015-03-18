package managers.globalconfig;



import java.io.Serializable;
import java.util.Map;

/**
 * Created by naveena on 08/02/15.
 */
public interface IGlobalConfigManager extends Serializable {

    void addOrUpdateVehicleDensity(Map<VehicleType, Double> vehicleDensityMap, int VehicleCount);

    void addOrUpdateWeather(ClimaticCondition climaticCondition);

    public ClimaticCondition getClimaticCondition();

    public DriverBehavior getDriverBehaviour();

    public VehicleDensity getVehicleDensity();

    public Route getRoute();

    void addOrUpdateDriverBehavior(Map<DriverBehaviorType, Double> driverBehaviorTypeMap);

    void addOrUpdateDestinationDensity(String destination, double density);

    double getCurrentSecond();

    void incrementTick();

    double getLengthMetres(int rUnits);

    double getLengthRUnits(double metres);

    int getTicksPerSecond();

    void setTicksPerSecond(int ticksPerSecond);

    Long getCurrentTick();

    double getMetresPerRUnit();


}
