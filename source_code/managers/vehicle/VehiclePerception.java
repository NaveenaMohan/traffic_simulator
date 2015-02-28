package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.runit.*;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;

/**
 * Created by Fabians on 18/02/2015.
 * this class is responsible for collecting the information about the world
 */
public class VehiclePerception {
    public static void See(int vehID, IRUnitManager rUnit, double maxVision, VehicleState vehicleState, ISpaceManager spaceManager, IDataAndStructures dataAndStructures, ObjectInSpace myObject) {
        IRUnitManager temp = rUnit;

        if (temp.getNextRUnitList().size() > 0)
            temp = temp.getNextRUnitList().get(0);

        int rUnitsVision = (int)(maxVision/dataAndStructures.getGlobalConfigManager().getMetresPerRUnit());

        for (int i = 0; i < rUnitsVision; i++) {

            //distance from the object
            double distance = Math.max(0,(i+1) * dataAndStructures.getGlobalConfigManager().getMetresPerRUnit());

            //check for blockages
            if (temp.getBlockage() != null)
                vehicleState.registerObject(new VehicleMemoryObject(temp, temp.getBlockage(), distance, getObjectVelocity(temp.getBlockage())));


            //check for zebra crossings
            if (temp.getZebraCrossing() != null)
                vehicleState.registerObject(new VehicleMemoryObject(temp, temp.getZebraCrossing(), distance, getObjectVelocity(temp.getZebraCrossing())));

            //check for traffic lights
            if (temp.getTrafficLight() != null)
                if(!temp.getTrafficLight().isGreen())
                    vehicleState.registerObject(new VehicleMemoryObject(temp, temp.getTrafficLight(), distance, getObjectVelocity(temp.getTrafficLight())));

            //check for traffic signs
            if (temp.getTrafficSign() != null)
                vehicleState.registerObject(new VehicleMemoryObject(temp, temp.getTrafficSign(), distance, getObjectVelocity(temp.getTrafficSign())));

            //check for other vehicles
            ObjectInSpace possibleVehicle = spaceManager.getObjectWithCentreAt(temp.getX(), temp.getY(), temp.getZ());
            if (possibleVehicle != null)
                if (possibleVehicle.getId() != vehID)
                    for (IVehicleManager veh : dataAndStructures.getVehicles()) {
                        if (veh.getVehID() == possibleVehicle.getId()) {
                            vehicleState.registerObject(
                                    new VehicleMemoryObject(
                                            temp,
                                            veh,
                                            distance - (veh.getObjectInSpace().getWidth() / 2) + veh.getVehicle().getDepthInCurrentRUnit(),
                                            getObjectVelocity(veh)));
                            break;
                        }
                    }

            //check for end of road
            if (temp.getNextRUnitList().size() <= 0)
                vehicleState.registerObject(new VehicleMemoryObject(temp, new EndOfRoad(), distance, getObjectVelocity(new EndOfRoad())));

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


    public static Object getObjectAtRUnit(IRUnitManager rUnit)
    {
        //returns the object that is at the given rUnit

        //check for blockages
        if (rUnit.getBlockage() != null)
            return rUnit.getBlockage();

        //check for zebra crossings
        if (rUnit.getZebraCrossing() != null)
            return rUnit.getZebraCrossing();

        //check for traffic lights
        if (rUnit.getTrafficLight() != null)
            return rUnit.getTrafficLight();

        //check for traffic signs
        if (rUnit.getTrafficSign() != null)
            return rUnit.getTrafficSign();


        return null;
    }

    private static double getObjectVelocity(Object obj)
    {//return the speed of the object
        if (obj instanceof Blockage)//check for blockage
            return 0;
        else if (obj instanceof Vehicle)//check for vehicle
            return ((Vehicle) obj).getCurrentVelocity();
        else if (obj instanceof EndOfRoad)//check for end of road
            return 0;
        else if (obj instanceof TrafficLight)//check for traffic light
            return 0;
        else if (obj instanceof ZebraCrossing)//check for zebra crossing
            return 0;
        else if (obj instanceof StopSign)//check for stop sign
            return 0;
        else if (obj instanceof SpeedLimitSign)//check for speed sign and do the conversion from km/h to m/s
            return (((SpeedLimitSign) obj).getSpeedLimit()*1000)/3600;
        else
            throw new IllegalArgumentException("object passed is not defined");
    }
}
