package managers.space;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface ISpaceManager extends Serializable {

    public void addObjectToSpace(ObjectInSpace objectInSpace);

    public boolean checkFit(int id, int x, int y, int width, int length);

    //returns the first object that has its centre at these coordinates
    public ObjectInSpace getObjectAt(int myID, int x, int y);

    public List<ObjectInSpace> getObjects();

    public void setObjects(List<ObjectInSpace> objectInSpaces);
}
