package managers.vehiclefactory;

import managers.globalconfig.VehicleType;
import managers.globalconfig.VehicleTypeStats;
import managers.runit.RUnit;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.vehicle.Driver;
import managers.vehicle.Vehicle;


/**
 * Created by naveena on 09/02/15.
 */
public class VehicleFactory{

    private RUnit rUnit;

    public VehicleFactory(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public RUnit getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public Vehicle addVehicle(int vehID, VehicleType vehicleType, Driver driver, String destination, ISpaceManager spaceManager, double time) {
        //vehicle factory creates vehicles, it sets their driver and objectInSpace

        //create object in space based on the vehicle type
        ObjectInSpace objectInSpace = new ObjectInSpace(vehID,rUnit.getX(), rUnit.getY(), rUnit.getZ(), VehicleTypeStats.getWidth(vehicleType)
                ,VehicleTypeStats.getLength(vehicleType),VehicleTypeStats.getHeight(vehicleType),null, vehicleType);

        //only add the new vehicle if it fits in the space outside the factory
        if(spaceManager.checkFit(objectInSpace.getId(), objectInSpace.getX(), objectInSpace.getY(), 10,10)) {
            spaceManager.addObjectToSpace(objectInSpace);
            Vehicle vehicle = new Vehicle(
                    vehID,//vehicle ID
                    rUnit,//vehicle starting point
                    driver,
                    (90*1000)/3600,//initial speed
                    destination,
                    objectInSpace,
                    VehicleTypeStats.getMaxAcceleration(vehicleType),//maximum acceleration in metres per second.
                    VehicleTypeStats.getMaxDeceleration(vehicleType),//max deceleration
                    time,
                    VehicleTypeStats.getMaxVelocity(vehicleType)
            );
            return vehicle;
        }
        return null;//return null if vehicle creation was not possible
    }

}
