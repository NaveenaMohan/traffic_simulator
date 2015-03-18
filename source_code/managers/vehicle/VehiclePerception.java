package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.runit.*;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;

import java.io.Serializable;

/**
 * Created by Fabians on 18/02/2015.
 * this class is responsible for collecting the information about the world
 */
public class VehiclePerception implements Serializable {


    public void see(int vehID, IRUnitManager rUnit, double maxVision, VehicleState vehicleState,
                    ISpaceManager spaceManager, IDataAndStructures dataAndStructures, ObjectInSpace myObject) {
        IRUnitManager temp = rUnit;

//        if (temp.getNextRUnitList().size() > 0)
//            temp = temp.getNextRUnitList().get(0);

        int rUnitsVision = (int) (maxVision / dataAndStructures.getGlobalConfigManager().getMetresPerRUnit());

        for (int i = 0; i < rUnitsVision; i++) {

            //distance from the object
            double distance = Math.max(0, (i) * dataAndStructures.getGlobalConfigManager().getMetresPerRUnit());



            //check for decision points
            if (temp.getNextRUnitList().size() > 1) {
                vehicleState.registerObject(new VehicleMemoryObject(temp, new RoadDecisionPoint(), distance, getObjectVelocity(new RoadDecisionPoint()), true, true));
                vehicleState.registerObject(new VehicleMemoryObject(temp, new RoadDecisionPoint(), distance, getObjectVelocity(new RoadDecisionPoint()), true, false));

            }
            //check for blockages
            if (getObjectForDoubleLane(temp) instanceof Blockage) {
                //add it for both lanes
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), false, true));
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), false, false));
            }
            //check for zebra crossings
            if (getObjectForDoubleLane(temp) instanceof ZebraCrossing)
                if (!((ZebraCrossing) getObjectForDoubleLane(temp)).getTrafficLight().isGreen()) {
                    //add it for both lanes
                    vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), false, true));
                    vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), false, false));
                }
            //check for traffic lights
            if (getObjectForDoubleLane(temp) instanceof TrafficLight)
                if (!((TrafficLight) getObjectForDoubleLane(temp)).isGreen()) {
                    //add it for both lanes
                    vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), false, true));
                    vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), false, false));
                }
            //check for traffic signs
            if (getObjectForDoubleLane(temp) instanceof TrafficSign) {
                //add it for both lanes
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), true, true));
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), true, false));
            }
            //get the next direction
            if (getObjectForDoubleLane(temp) instanceof DirectionSign) {
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), true, true));
                vehicleState.registerObject(new VehicleMemoryObject(temp, getObjectForDoubleLane(temp), distance, getObjectVelocity(getObjectForDoubleLane(temp)), true, false));
            }
            //check for other vehicles
            for (int j = 0; j < (temp.getChangeAbleRUnit() != null ? 2 : 1); j++) {//check for vehicle at both lanes and add it there
                IRUnitManager innerTemp = temp;
                ObjectInSpace possibleVehicle = spaceManager.getObjectAt(myObject.getId(), innerTemp.getX(), innerTemp.getY());

                if (possibleVehicle != null)
                    if (possibleVehicle.getId() != vehID)
                        for (IVehicleManager veh : dataAndStructures.getVehicles()) {
                            if (veh.getVehID() == possibleVehicle.getId()) {
                                vehicleState.registerObject(
                                        new VehicleMemoryObject(
                                                innerTemp,
                                                veh,
                                                distance - (veh.getObjectInSpace().getWidth() / 2) + veh.getVehicle().getDepthInCurrentRUnit(),
                                                getObjectVelocity(veh),
                                                false, innerTemp.isLeft()));
                                break;
                            }
                        }
                innerTemp = temp.getChangeAbleRUnit();
            }


            //go to next rUnit
            if (temp.getNextRUnitList().size() > 0)
                temp = VehicleMotor.chooseNext(temp, vehicleState);
            else
                break;

        }

    }

    public static boolean isChangeableClear(IRUnitManager temp, ISpaceManager spaceManager, ObjectInSpace myObject, int backAmount,
                                            int frontAmount) {
        if (temp.getChangeAbleRUnit() != null) {
            if (checkChangeableClear(spaceManager, myObject,
                    temp, backAmount, frontAmount))
                return true;
        }
        return false;
    }

    public static Object getObjectForDoubleLane(IRUnitManager rUnit) {
        if (rUnit.getChangeAbleRUnit() != null & !rUnit.isLeft())
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

        //check for traffic signs
        if (rUnit.getBlockage() != null)
            return rUnit.getBlockage();

        return rUnit;
    }

    private static boolean checkChangeableClear(ISpaceManager spaceManager, ObjectInSpace myObject
            , IRUnitManager rUnit, int backwardsSpaceCheck, int forwardSpaceCheck) {
        if (rUnit.getChangeAbleRUnit() != null) {
            IRUnitManager temp = rUnit.getChangeAbleRUnit();
            //look backwards
            for (int i = 0; i < backwardsSpaceCheck; i++) {
                if (temp.getPrevsRUnitList().size() > 0) {
                    if (!spaceManager.checkFit(myObject.getId(), temp.getX(), temp.getY(), 10,10))
                        return false;
                    temp = temp.getPrevsRUnitList().get(0);
                }
            }
            temp = rUnit.getChangeAbleRUnit();
            //look forward
            for (int i = 0; i < forwardSpaceCheck; i++) {
                if (temp.getNextRUnitList().size() > 0) {
                    if (!spaceManager.checkFit(myObject.getId(), temp.getX(), temp.getY(), 10,10))
                        return false;
                    temp = temp.getNextRUnitList().get(0);
                }
            }
        } else
            return false;

        return true;
    }

    private static double getObjectVelocity(Object obj) {//return the speed of the object
        if (obj instanceof Blockage)//check for blockage
            return 0;
        else if (obj instanceof Vehicle)//check for vehicle
            return ((Vehicle) obj).getCurrentVelocity();
        else if (obj instanceof TrafficLight)//check for traffic light
            return 0;
        else if (obj instanceof ZebraCrossing)//check for zebra crossing
            return 0;
        else if (obj instanceof StopSign)//check for stop sign
            return 1;
        else if (obj instanceof SpeedLimitSign)//check for speed sign and do the conversion from km/h to m/s
            return (((SpeedLimitSign) obj).getSpeedLimit() * 1000) / 3600;
        else if (obj instanceof DirectionSign)//check for Direction Sign
            return 100;
        else if (obj instanceof RoadDecisionPoint)//check for Decision Point
            return 100;
        else if (obj instanceof WelcomeSign)//check for Decision Point
            return 100;
        else
            throw new IllegalArgumentException("object passed (" + obj + ") is not defined");
    }
}
