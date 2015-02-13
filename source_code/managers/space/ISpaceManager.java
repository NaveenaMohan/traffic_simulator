package managers.space;

import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public interface ISpaceManager {

    void addObjectToSpace(ObjectInSpace objectInSpace);

    void removeObject(ObjectInSpace object);

    boolean checkFit(ObjectInSpace objectInSpace);

    public List<ObjectInSpace> getObjects();
}
