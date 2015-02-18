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
        return checkFit(objectToCheck.getId(), objectToCheck.getX(),objectToCheck.getY(),objectToCheck.getWidth(), objectToCheck.getLength());
    }

    @Override
    public boolean checkFit(int id, int x, int y, int width, int length) {
        //System.out.println("checkFit id: " + id + " x: "+ x + " y: " + y + " width: " + width + " length: " + length);
        ObjectInSpace objectToCheck = new ObjectInSpace(id, x,y,0,width,length,0, null);
        //go through each object in space and check if you intersect
        for(ObjectInSpace obj : space.getObjects())
        {
//            System.out.println("-against id: " + obj.getId() + " x: "+ obj.getX() + " y: " + obj.getY()
//                    + " width: " + obj.getWidth() + " length: " + obj.getLength());
            //check that the object is not yourself
            if(objectToCheck.getId()!=obj.getId())
                if( objectToCheck.getBounds().intersects(obj.getBounds())) //check if you intersect that object
                    return false;
                    //System.out.print("-object the same");
        }
       // System.out.println(" !! Good - true");
        return true;
    }

    @Override
    public List<ObjectInSpace> getObjects() {
        return space.getObjects();
    }


}
