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
        for (int i = 0; i < objectsAhead.size(); i++) {
            if (objectsAhead.get(i).getDistance() <= metres) {
                if (objectsAhead.get(i).isInLeft() == inLeft)
                    if (!(isEmergency &
                            (objectsAhead.get(i).getObject() instanceof TrafficLight |
                                    objectsAhead.get(i).getObject() instanceof SpeedLimitSign))) {
                        return objectsAhead.get(i);
                    }
            } else
                break;
        }
        return null;
    }

    public VehicleMemoryObject getNextVehicleObject(double metres, boolean inLeft) {
        for (int i = 0; i < objectsAhead.size(); i++) {
            if (objectsAhead.get(i).getDistance() <= metres) {
                if (objectsAhead.get(i).isInLeft() == inLeft & objectsAhead.get(i).getObject() instanceof IVehicleManager)
                    return objectsAhead.get(i);
            } else
                break;
        }

        return null;
    }


    public VehicleMemoryObject getNextSpeedAffectingRoadElement(double metres, boolean inLeft) {
        for (int i = 0; i < objectsAhead.size(); i++) {
            if (objectsAhead.get(i).getDistance() <= metres) {
                if (objectsAhead.get(i).isInLeft() == inLeft &
                        (objectsAhead.get(i).getObject() instanceof SpeedLimitSign |
                                objectsAhead.get(i).getObject() instanceof TrafficLight |
                                objectsAhead.get(i).getObject() instanceof ZebraCrossing |
                                objectsAhead.get(i).getObject() instanceof StopSign))
                    return objectsAhead.get(i);
            } else
                break;
        }

        return null;
    }

    public VehicleMemoryObject getSlowestWithin(double metres, boolean isEmergency, boolean inLeft) {
        //returns the slowest object within metres metres


        int currentIndex = 0;
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
