package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.runit.IRUnitManager;
import managers.runit.RUnit;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;

/**
 * Created by Fabians on 18/02/2015.
 * this class is responsible for collecting the information about the world
 */
public class VehiclePerception {
    public static void See(int vehID, IRUnitManager rUnit, int maxVision, VehicleState vehicleState, ISpaceManager spaceManager, IDataAndStructures dataAndStructures, ObjectInSpace myObject) {
        IRUnitManager temp = rUnit;
        for (int i = 0; i < maxVision; i++) {
            double distance = Math.max(0,i * dataAndStructures.getGlobalConfigManager().getMetresPerRUnit());

            //check for blockages
            if (temp.getBlockage() != null)
                vehicleState.RegisterObject(new VehicleMemoryObject(temp, temp.getBlockage(), distance));

            //TODO SOMETHING IS WRONG WITH ZEBRA CROSSING
            //check for zebra crossings
//            if (temp.getZebraCrossing() != null)
//                vehicleState.RegisterObject(new VehicleMemoryObject(temp, temp.getZebraCrossing(), distance));

            //check for traffic lights
            if (temp.getTrafficLight() != null) {

                vehicleState.RegisterObject(new VehicleMemoryObject(temp, temp.getTrafficLight(), distance));
            }

            //check for traffic signs
            if (temp.getTrafficSign() != null)
                vehicleState.RegisterObject(new VehicleMemoryObject(temp, temp.getTrafficSign(), distance));

            //check for other vehicles
            ObjectInSpace possibleVehicle = spaceManager.getObjectWithCentreAt(temp.getX(), temp.getY(), temp.getZ());
            if (possibleVehicle != null)
                if (possibleVehicle.getId() != vehID)
                    for (IVehicleManager veh : dataAndStructures.getVehicles()) {
                        if (veh.getVehID() == possibleVehicle.getId()) {
                            vehicleState.RegisterObject(new VehicleMemoryObject(temp, veh, distance - (veh.getObjectInSpace().getWidth() / 2)));
                            break;
                        }
                    }

            //check for end of road
            if (temp.getNextRUnitList().size() <= 0)
                vehicleState.RegisterObject(new VehicleMemoryObject(temp, new EndOfRoad(), distance));

            //check if the changeable RUnit is clear
            vehicleState.setChangeableClear(false);
            if (temp.getChangeAbleRUnit() != null)
                if (spaceManager.checkFit(myObject.getId(), temp.getX(), temp.getY(), myObject.getWidth(), myObject.getLength()))
                    vehicleState.setChangeableClear(true);


            //go to next rUnit
            if (temp.getNextRUnitList().size() > 0)
                temp = temp.getNextRUnitList().get(0);
            else
                break;

        }
    }
}
