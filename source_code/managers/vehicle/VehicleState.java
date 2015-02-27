package managers.vehicle;

import managers.runit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This method holds all the information that the vehicle knows about the road and objects ahead
 */
public class VehicleState {

    private List<VehicleMemoryObject> objectsAhead = new ArrayList<VehicleMemoryObject>();
    private boolean changeableClear = false;//this is set to true if there is a changeable RUnit next to you and it is clear

    public void registerObject(VehicleMemoryObject obj) {
        //System.out.println("=registered "+obj.getObject().getClass() + " " + obj.getObject());
        //saves the given object to its list of objects
        objectsAhead.add(obj);
    }

    public void setChangeableClear(boolean changeableClear) {
        this.changeableClear = changeableClear;
    }

    public boolean isChangeableClear() {
        return changeableClear;
    }

    public VehicleMemoryObject getSlowestWithin(double metres) {
        //returns the slowest object within metres metres


        int currentIndex = 0;
        VehicleMemoryObject slowestObject=null;

        for (VehicleMemoryObject obj : objectsAhead) {
            if (obj.getDistance() > metres)
                break;

            if (slowestObject == null) {
                slowestObject = obj;
            }else if(slowestObject.getVelocity()>obj.getVelocity()){
                slowestObject=obj;
            }
        }

        return slowestObject;
    }


}
