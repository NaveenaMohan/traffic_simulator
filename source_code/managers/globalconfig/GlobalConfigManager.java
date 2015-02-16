package managers.globalconfig;

import managers.vehicle.VehicleType;

import java.util.Map;

/**
 * Created by Fabians on 13/02/2015.
 */
public class GlobalConfigManager implements IGlobalConfigManager {

    private TickTime tickTime;

    public GlobalConfigManager(int tickRatio) {
        tickTime=new TickTime(tickRatio);
    }

    @Override
    public void addOrUpdateVehicleDensity(Map<VehicleType, Double> vehicleDensityMap, int VehicleCount) {

    }

    @Override
    public void addOrUpdateWeather(double visibility, double slipperiness) {

    }

    @Override
    public void addOrUpdateDriverBehavior(Map<DriverBehaviorType, Double> driverBehaviorTypeMap) {

    }

    @Override
    public void addOrUpdateDestinationDensity(String destination, double density) {

    }

    @Override
    public Long getCurrentSecond() {
        return (tickTime.getCurrentTick()/tickTime.getRatio());
    }

    @Override
    public void incrementTick() {
        tickTime.incrementTick();
    }
}
