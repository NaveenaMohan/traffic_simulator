package managers.space;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface ISpaceManager extends Serializable {

    void addObjectToSpace(ObjectInSpace objectInSpace);

    boolean checkFit(int id, int x, int y, int width, int length);

    //returns the first object that has its centre at these coordinates
    ObjectInSpace getObjectAt(int myID, int x, int y);

    public List<ObjectInSpace> getObjects();

    public void setObjects(List<ObjectInSpace> objectInSpaces);
}
