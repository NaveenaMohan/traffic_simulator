package managers.vehicle;

import managers.runit.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This method holds all the information that the vehicle knows about the road and objects ahead
 */
public class VehicleState implements Serializable {
//this object holds the information about the state of the environment around the vehicle


    private List<VehicleMemoryObject> objectsAhead = new ArrayList<VehicleMemoryObject>();

    private DirectionSignType nextDirectionAtDecisionPoint;
    private IRUnitManager nextRUnitAfterDecisionPoint;

    public DirectionSignType getNextDirectionAtDecisionPoint() {
        return nextDirectionAtDecisionPoint;
    }

    public void setNextDirectionAtDecisionPoint(DirectionSignType nextDirectionAtDecisionPoint) {
        this.nextDirectionAtDecisionPoint = nextDirectionAtDecisionPoint;
    }

    public IRUnitManager getNextRUnitAfterDecisionPoint() {
        return nextRUnitAfterDecisionPoint;
    }

    public void setNextRUnitAfterDecisionPoint(IRUnitManager nextRUnitAfterDecisionPoint) {
        this.nextRUnitAfterDecisionPoint = nextRUnitAfterDecisionPoint;
    }

    public void registerObject(VehicleMemoryObject obj) {

        //saves the given object to its list of objects
        objectsAhead.add(obj);
    }

    public void cleanObjectsAhead() {
        objectsAhead = new ArrayList<VehicleMemoryObject>();
    }

    public VehicleMemoryObject nextObjectWithin(double metres, boolean isEmergency, boolean inLeft) {
        /*
        Returns the next object within metres metres
         */
        for (VehicleMemoryObject anObjectsAhead : objectsAhead) {
            if (anObjectsAhead.getDistance() <= metres) {
                if (anObjectsAhead.isInLeft() == inLeft)
                    if (!(isEmergency &
                            (anObjectsAhead.getObject() instanceof TrafficLight |
                                    anObjectsAhead.getObject() instanceof SpeedLimitSign))) {
                        return anObjectsAhead;
                    }
            } else
                break;
        }
        return null;
    }

    public VehicleMemoryObject getNextVehicleObject(double metres, boolean inLeft) {
        for (VehicleMemoryObject anObjectsAhead : objectsAhead) {
            if (anObjectsAhead.getDistance() <= metres) {
                if (anObjectsAhead.isInLeft() == inLeft & anObjectsAhead.getObject() instanceof IVehicleManager)
                    return anObjectsAhead;
            } else
                break;
        }

        return null;
    }


    public VehicleMemoryObject getNextSpeedAffectingRoadElement(double metres, boolean inLeft) {
        for (VehicleMemoryObject anObjectsAhead : objectsAhead) {
            if (anObjectsAhead.getDistance() <= metres) {
                if (anObjectsAhead.isInLeft() == inLeft &
                        (anObjectsAhead.getObject() instanceof SpeedLimitSign |
                                anObjectsAhead.getObject() instanceof TrafficLight |
                                anObjectsAhead.getObject() instanceof ZebraCrossing |
                                anObjectsAhead.getObject() instanceof StopSign))
                    return anObjectsAhead;
            } else
                break;
        }

        return null;
    }

    public VehicleMemoryObject getSlowestWithin(double metres, boolean isEmergency, boolean inLeft) {
        //returns the slowest object within metres metres

        VehicleMemoryObject slowestObject = null;

        for (VehicleMemoryObject obj : objectsAhead) {
            if (obj.getDistance() > metres)
                break;

            if (obj.isInLeft() == inLeft) {
                if (slowestObject == null & !(isEmergency &
                        (obj.getObject() instanceof TrafficLight |
                                obj.getObject() instanceof SpeedLimitSign))) {
                    slowestObject = obj;
                }
                if (slowestObject != null)
                    if ((slowestObject.getVelocity() > obj.getVelocity()) & !(isEmergency &
                            (obj.getObject() instanceof TrafficLight |
                                    obj.getObject() instanceof SpeedLimitSign))) {
                        slowestObject = obj;
                    }
            }
        }

        return slowestObject;
    }


}
