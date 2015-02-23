package managers.vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * This method holds all the information that the vehicle knows about the road and objects ahead
 */
public class VehicleState {

    private List<VehicleMemoryObject> objects = new ArrayList<VehicleMemoryObject>();
    private boolean changeableClear=false;//this is set to true if there is a changeable RUnit next to you and it is clear

    public void RegisterObject(VehicleMemoryObject obj)
    {
        System.out.println("=registered "+obj.getObject().getClass() + " " + obj.getObject());
        //saves the given object to its list of objects
        objects.add(obj);
    }

    public void setChangeableClear(boolean changeableClear) {
        this.changeableClear = changeableClear;
    }

    public boolean isChangeableClear() {
        return changeableClear;
    }

    public VehicleMemoryObject NextObject()
    {
        if (objects.size()>0)
            return objects.get(0);
        else
            return null;
    }
}
