package managers.space;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Space implements Serializable {

    private List<ObjectInSpace> objects = new ArrayList<ObjectInSpace>();

    public void addObject(ObjectInSpace objectInSpace) {
        objects.add(objectInSpace);
    }

    public  List<ObjectInSpace> getObjects(){
        return objects;
    }

    public void setObjects(List<ObjectInSpace> objects) {
        this.objects = objects;
    }
}
