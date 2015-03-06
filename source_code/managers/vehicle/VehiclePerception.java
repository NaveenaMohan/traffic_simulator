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
    public static void See(int vehID, IRUnitManager rUnit, double maxVision, VehicleState vehicleState,
                           ISpaceManager spaceManager, IDataAndStructures dataAndStructures, ObjectInSpace myObject, String myDestination) {
        IRUnitManager temp = rUnit;

        if (temp.getNextRUnitList().size() > 0)
            temp = temp.getNextRUnitList().get(0);

        int rUnitsVision = (int)(maxVision/dataAndStructures.getGlobalConfigManager().getMetresPerRUnit());

        for (int i = 0; i < rUnitsVision; i++) {

            //distance from the object
            double distance = Math.max(0,(i+1) * dataAndStructures.getGlobalConfigManager().getMetresPerRUnit());

            //check for decision points
            if(temp.getNextRUnitList().size()>1)
                vehicleState.registerObject(new VehicleMemoryObject(temp, new RoadDecisionPoint(), distance, getObjectVelocity(new RoadDecisionPoint()), true));

            //check for blockages
            if (temp.getBlockage() != null)
                vehicleState.registerObject(new VehicleMemoryObject(temp, temp.getBlockage(), distance, getObjectVelocity(temp.getBlockage()), false));

            //check for zebra crossings
            if (getObjectForDoubleLane(temp) instanceof  ZebraCrossing)
                if(!((ZebraCrossing)getObjectForDoubleLane(temp)).getTrafficLight().isGreen())
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), false));

            //check for traffic lights
            if (getObjectForDoubleLane(temp) instanceof TrafficLight)
                if(!((TrafficLight)getObjectForDoubleLane(temp)).isGreen())
                    vehicleState.registerObject(new VehicleMemoryObject(temp, temp.getTrafficLight(), distance, getObjectVelocity(temp.getTrafficLight()), false));

            //check for traffic signs
            if (getObjectForDoubleLane(temp) instanceof TrafficSign) {
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), true));

                //get the next direction
                if(getObjectForDoubleLane(temp) instanceof DirectionSign)
                    vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), true));
            }
            //check for other vehicles
            ObjectInSpace possibleVehicle = spaceManager.getObjectAt(myObject.getId(), temp.getX(), temp.getY());
            if (possibleVehicle != null)
                if (possibleVehicle.getId() != vehID)
                    for (IVehicleManager veh : dataAndStructures.getVehicles()) {
                        if (veh.getVehID() == possibleVehicle.getId()) {
                            vehicleState.registerObject(
                                    new VehicleMemoryObject(
                                            temp,
                                            veh,
                                            distance - (veh.getObjectInSpace().getWidth() / 2) + veh.getVehicle().getDepthInCurrentRUnit(),
                                            getObjectVelocity(veh),
                                            false));
                            break;
                        }
                    }

            //check for end of road
            if (temp.getNextRUnitList().size() <= 0)
                vehicleState.registerObject(new VehicleMemoryObject(temp, new EndOfRoad(), distance, getObjectVelocity(new EndOfRoad()), true));

            //check if the changeable RUnit is clear
            vehicleState.setChangeableClear(false);
            if (temp.getChangeAbleRUnit() != null)
                if (spaceManager.checkFit(myObject.getId(), temp.getX(), temp.getY(), myObject.getWidth(), myObject.getLength()))
                    vehicleState.setChangeableClear(true);


            //go to next rUnit
            if (temp.getNextRUnitList().size() > 0)
                temp = VehicleMotor.chooseNext(temp,vehicleState);
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

    private static Object getObjectForDoubleLane(IRUnitManager rUnit)
    {
        if(rUnit.getChangeAbleRUnit()!=null & !rUnit.isLeft())
            rUnit = rUnit.getChangeAbleRUnit();

        //check for zebra crossings
        if (rUnit.getZebraCrossing() != null)
            return rUnit.getZebraCrossing();

        //check for traffic lights
        if (rUnit.getTrafficLight() != null)
            return rUnit.getTrafficLight();

        //check for traffic signs
        if (rUnit.getTrafficSign() != null)
            return rUnit.getTrafficSign();

        return rUnit;
    }
    private static double getObjectVelocity(Object obj)
    {//return the speed of the object
        if (obj instanceof Blockage)//check for blockage
            return 0;
        else if (obj instanceof Vehicle)//check for vehicle
            return ((Vehicle) obj).getCurrentVelocity();
        else if (obj instanceof EndOfRoad)//check for end of road
            return 100;
        else if (obj instanceof TrafficLight)//check for traffic light
            return 0;
        else if (obj instanceof ZebraCrossing)//check for zebra crossing
            return 0;
        else if (obj instanceof StopSign)//check for stop sign
            return 0;
        else if (obj instanceof SpeedLimitSign)//check for speed sign and do the conversion from km/h to m/s
            return (((SpeedLimitSign) obj).getSpeedLimit()*1000)/3600;
        else if (obj instanceof DirectionSign)//check for Direction Sign
            return 100;
        else if (obj instanceof RoadDecisionPoint)//check for Decision Point
            return 100;
        else
            throw new IllegalArgumentException("object passed is not defined");
    }
}
