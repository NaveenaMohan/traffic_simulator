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
    public boolean checkFit(int id, int x, int y, int width, int length) {
        /*
        This function returns true if object does not overlap with other objects in space
         */

        ObjectInSpace objectToCheck = new ObjectInSpace(id, x, y, 0, width, length, 0, null, null);
        //go through each object in space and check if you intersect
        for (ObjectInSpace obj : space.getObjects()) {
            if (objectToCheck.getId() != obj.getId())
                if (objectToCheck.getBounds().intersects(obj.getBounds())) //check if you intersect that object
                    return false;
        }
        return true;
    }

    @Override
    public ObjectInSpace getObjectAt(int myID, int x, int y) {
        /*
        Returns an object with center at x and y that is different from the given ID
         */

        for (ObjectInSpace obj : space.getObjects()) {
            if (obj.getId() != myID)
                if (obj.getX() == x & obj.getY() == y & obj.isVisible())

                    return obj;
        }

        return null;
    }

    @Override
    public List<ObjectInSpace> getObjects() {
        return space.getObjects();
    }

    @Override
    public void setObjects(List<ObjectInSpace> objectInSpaces) {
        space.setObjects(objectInSpaces);
    }


}
