package managers.space;

import java.util.ArrayList;
import java.util.List;

public class Space {

    List<ObjectInSpace> objects = new ArrayList<ObjectInSpace>();

    public void addObject(ObjectInSpace objectInSpace) {
        objects.add(objectInSpace);
    }

    public void removeObject(ObjectInSpace objectInSpace) {
        objects.remove(objectInSpace);
    }

    public boolean checkObjectFit(ObjectInSpace objectInSpace, int x, int y, int z, VehicleDirection direction) {
        return true;
    }
}
