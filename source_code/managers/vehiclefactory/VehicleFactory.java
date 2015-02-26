package managers.vehiclefactory;

import managers.globalconfig.VehicleType;
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
        ObjectInSpace objectInSpace;
        switch(vehicleType)
        {
            case car:
                objectInSpace = new ObjectInSpace(vehID,rUnit.getX(), rUnit.getY(), rUnit.getZ(), 2,3,2,null);
                break;
            case emergency:
                objectInSpace = new ObjectInSpace(vehID,rUnit.getX(), rUnit.getY(), rUnit.getZ(), 2,3,2,null);
                break;
            case heavyLoad:
                objectInSpace = new ObjectInSpace(vehID,rUnit.getX(), rUnit.getY(), rUnit.getZ(), 2,4,4,null);
                break;
            default:
                objectInSpace = new ObjectInSpace(vehID, rUnit.getX(), rUnit.getY(), rUnit.getZ(), 2,3,2,null);
        }

        //only add the new vehicle if it fits in the space outside the factory
        if(spaceManager.checkFit(objectInSpace)) {
            spaceManager.addObjectToSpace(objectInSpace);
            Vehicle vehicle = new Vehicle(vehID,//vehicle ID
                    rUnit,//vehicle starting point
                    driver,
                    100,//maximum speed
                    vehicleType,
                    destination,
                    objectInSpace,
                    2.7,//maximum acceleration in metres per second.
                    -100,//max deceleration
                    time
            );
            return vehicle;
        }
        return null;//return null if vehicle creation was not possible
    }

}
