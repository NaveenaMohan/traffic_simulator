package managers.globalconfig;

import managers.vehicle.VehicleType;

import java.util.Map;

/**
 * Created by naveena on 08/02/15.
 */
public interface IGlobalConfigManager {

    void addOrUpdateVehicleDensity(Map<VehicleType, Double> vehicleDensityMap, int VehicleCount);

    void addOrUpdateWeather(ClimaticCondition climaticCondition);

    public ClimaticCondition getClimaticCondition();

    void addOrUpdateDriverBehavior(Map<DriverBehaviorType, Double> driverBehaviorTypeMap);

    void addOrUpdateDestinationDensity(String destination, double density);

    double getCurrentSecond();

    void incrementTick();

    double getLengthMetres(int rUnits);

    double getLengthRUnits(double metres);

    double getMetresPerRUnit();


}
