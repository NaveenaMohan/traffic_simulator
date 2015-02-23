package managers.globalconfig;



import java.util.Map;

/**
 * Created by Fabians on 13/02/2015.
 */
public class GlobalConfigManager implements IGlobalConfigManager {

    private TickTime tickTime;
    private DistancesScale distancesScale;
    private ClimaticCondition climaticCondition;

    public GlobalConfigManager(int tickRatio, int metresPerRUnit, ClimaticCondition climaticCondition) {
        this.tickTime=new TickTime(tickRatio);
        this.distancesScale = new DistancesScale(metresPerRUnit);
        this.climaticCondition=climaticCondition;
    }

    @Override
    public ClimaticCondition getClimaticCondition() {
        return climaticCondition;
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
        return ((double)tickTime.getCurrentTick()/tickTime.getRatio());
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
    public double getMetresPerRUnit() {
        return distancesScale.getMetresPerRUnit();
    }
}
