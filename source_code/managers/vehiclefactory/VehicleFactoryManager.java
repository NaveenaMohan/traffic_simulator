package managers.vehiclefactory;

import common.Common;
import dataAndStructures.IDataAndStructures;
import managers.globalconfig.DriverBehaviorType;
import managers.globalconfig.VehicleType;
import managers.runit.IRUnitManager;
import managers.vehicle.Driver;
import managers.vehicle.IVehicleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabians on 12/02/2015.
 */
public class VehicleFactoryManager implements IVehicleFactoryManager {

    private List<VehicleFactory> vehicleFactoryList = new ArrayList<VehicleFactory>();

    @Override
    public void addVehicleFactory(IRUnitManager rUnit) {
        vehicleFactoryList.add(new VehicleFactory(rUnit));
    }

    @Override
    public IVehicleManager createVehicle(IDataAndStructures dataAndStructures) {
        //create vehicle is consulting globalConfig to look at destinations, driver behaviour, climatic conditions, etc...


        //get the vehicleCreation Rate and ticks per second to figure out whether to create a vehicle on this occasion or not
        if (Common.randIntegerBetween(1,
                (int) ((dataAndStructures.getGlobalConfigManager().getTicksPerSecond()
                        / dataAndStructures.getGlobalConfigManager().getVehicleDensity().getCreationRatePerSecond()))) == 1)
            if (vehicleFactoryList.size() > 0) {
                //get the vehicle factory from which you will now produce
                return vehicleFactoryList.get(Common.randIntegerBetween(0, vehicleFactoryList.size() - 1)).addVehicle(
                        dataAndStructures.getVehicles().size() + 1,
                        nextVehicleType(dataAndStructures),//vehicle Type
                        nextDriver(dataAndStructures),//driver
                        nextDestination(dataAndStructures),//destination
                        dataAndStructures.getSpaceManager(),
                        dataAndStructures.getGlobalConfigManager().getCurrentSecond());
            }
        return null;
    }

    private Driver nextDriver(IDataAndStructures dataAndStructures) {
        //randomly creates a driver
        double randomDriver = Common.randDoubleBetween(0, 1);
        DriverBehaviorType driverBehaviourType;
        if (randomDriver < dataAndStructures.getGlobalConfigManager().getDriverBehaviour().getPercentageCautious())//cautious
            driverBehaviourType = DriverBehaviorType.cautious;
        else if (randomDriver < dataAndStructures.getGlobalConfigManager().getDriverBehaviour().getPercentageCautious()
                + dataAndStructures.getGlobalConfigManager().getDriverBehaviour().getPercentageNormal())//normal
            driverBehaviourType = DriverBehaviorType.normal;
        else//reckless
            driverBehaviourType = DriverBehaviorType.reckless;

        return new Driver(
                dataAndStructures.getGlobalConfigManager().getDriverBehaviour().getRandomSpeedOffsetForDriverType(driverBehaviourType),
                dataAndStructures.getGlobalConfigManager().getDriverBehaviour().getRandomVisibilityOffsetForDriverType(driverBehaviourType),
                dataAndStructures.getGlobalConfigManager().getDriverBehaviour().getRandomReactionTimeOffsetForDriverType(driverBehaviourType),
                driverBehaviourType
        );
    }

    private String nextDestination(IDataAndStructures dataAndStructures) {
        String destination = "";
        if ((double) Common.randDoubleBetween(0, 1) <= dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent())
            destination = dataAndStructures.getGlobalConfigManager().getRoute().getDestination();

        return destination;
    }

    private VehicleType nextVehicleType(IDataAndStructures dataAndStructures) {
        double rand = Common.randDoubleBetween(0, 1);
        if (rand <= dataAndStructures.getGlobalConfigManager().getVehicleDensity().getCarDensity())
            return VehicleType.car;
        else if (rand <= dataAndStructures.getGlobalConfigManager().getVehicleDensity().getCarDensity()
                + dataAndStructures.getGlobalConfigManager().getVehicleDensity().getHeavyVehicleDensity())
            return VehicleType.heavyLoad;
        else
            return VehicleType.emergency;
    }

    public List<VehicleFactory> getVehicleFactoryList() {
        return vehicleFactoryList;
    }
}
