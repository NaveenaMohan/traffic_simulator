package managers.space;

import java.util.List;

/**
 * Created by Fabians on 13/02/2015.
 */
public class SpaceManager implements ISpaceManager {
    private Space space = new Space();
    @Override
    public void addObjectToSpace(ObjectInSpace objectInSpace) {
        space.addObject(objectInSpace);
    }

    @Override
    public void removeObject(ObjectInSpace object) {

    }

    @Override
    public boolean checkFit(ObjectInSpace objectToCheck) {
        //go through every object in space and check if you fits
        //for now ignore direction

        //go through each object in space and check if you intersect
        for(ObjectInSpace obj : space.getObjects())
        {
            if(objectToCheck!=obj & objectToCheck.getBounds().intersects(obj.getBounds()))
                return false;
        }
        return true;

    }

    @Override
    public List<ObjectInSpace> getObjects() {
        return space.getObjects();
    }


}
