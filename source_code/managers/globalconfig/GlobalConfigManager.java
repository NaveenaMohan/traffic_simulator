package managers.globalconfig;


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
    public double getCurrentSecond() {
        return tickTime.getCurrentSecond();
    }

    @Override
    public void incrementTick() {
        tickTime.incrementTick();
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
    public double getMetresPerRUnit() {
        return distancesScale.getMetresPerRUnit();
    }
}
