package managers.globalconfig;

import managers.vehicle.VehicleType;

import java.util.Map;

/**
 * Created by naveena on 08/02/15.
 */
public interface IGlobalConfigManager {

    void addOrUpdateVehicleDensity(Map<VehicleType, Double> vehicleDensityMap, int VehicleCount);

    void addOrUpdateWeather(double visibility, double slipperiness);

    void addOrUpdateDriverBehavior(Map<DriverBehaviorType, Double> driverBehaviorTypeMap);

    void addOrUpdateDestinationDensity(String destination, double density);

    int getCurrentSecond();

    void incrementTick();
}
