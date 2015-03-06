package managers.space;

import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface ISpaceManager {

    void addObjectToSpace(ObjectInSpace objectInSpace);

    void removeObject(ObjectInSpace object);

    boolean checkFit(ObjectInSpace objectInSpace);

    boolean checkFit(int id, int x, int y, int width, int height);

    //returns the first object that has its centre at these coordinates
    ObjectInSpace getObjectAt(int myID, int x, int y);

    public List<ObjectInSpace> getObjects();
}
