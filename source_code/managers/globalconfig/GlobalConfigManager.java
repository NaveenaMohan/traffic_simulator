package managers.globalconfig;


import java.util.Map;

/**
 * Created by Fabians on 13/02/2015.
 */
public class GlobalConfigManager implements IGlobalConfigManager {

    private TickTime tickTime;
    private DistancesScale distancesScale;
    private ClimaticCondition climaticCondition;
    private DriverBehavior driverBehavior;
    private VehicleDensity vehicleDensity;
    private Route route;

    public GlobalConfigManager(int tickRatio, double metresPerRUnit, ClimaticCondition climaticCondition, DriverBehavior driverBehavior
            , VehicleDensity vehicleDensity, Route route) {
        this.tickTime = new TickTime(tickRatio);
        this.distancesScale = new DistancesScale(metresPerRUnit);
        this.climaticCondition = climaticCondition;
        this.driverBehavior = driverBehavior;
        this.vehicleDensity = vehicleDensity;
        this.route = route;
    }

    @Override
    public ClimaticCondition getClimaticCondition() {
        return climaticCondition;
    }

    @Override
    public DriverBehavior getDriverBehaviour() {
        return driverBehavior;
    }

    @Override
    public VehicleDensity getVehicleDensity() {
        return vehicleDensity;
    }

    @Override
    public Route getRoute() {
        return route;
    }

    @Override
    public void addOrUpdateVehicleDensity(Map<VehicleType, Double> vehicleDensityMap, int VehicleCount) {

    }

    @Override
    public void addOrUpdateWeather(ClimaticCondition climaticCondition) {
        this.climaticCondition = climaticCondition;
    }

    @Override
    public void addOrUpdateDriverBehavior(Map<DriverBehaviorType, Double> driverBehaviorTypeMap) {

    }

    @Override
    public void addOrUpdateDestinationDensity(String destination, double density) {

    }

    @Override
    public double getCurrentSecond() {
        return tickTime.getCurrentSecond();
    }

    @Override
    public void incrementTick() {
        tickTime.incrementTick();
    }

    @Override
    public double getLengthMetres(int rUnits) {
        return distancesScale.getLengthMetres(rUnits);
    }

    @Override
    public double getLengthRUnits(double metres) {
        return distancesScale.getLengthRUnits(metres);
    }

    @Override
    public int getTicksPerSecond() {
        return tickTime.getRatio();
    }

    @Override
    public void setTicksPerSecond(int ticksPerSecond) {
        tickTime.setRatio(ticksPerSecond);
    }

    @Override
    public Long getCurrentTick() {
        return tickTime.getCurrentTick();
    }

    @Override
    public double getMetresPerRUnit() {
        return distancesScale.getMetresPerRUnit();
    }
}
