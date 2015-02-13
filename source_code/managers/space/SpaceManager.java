package managers.space;

/**
 * Created by Fabians on 13/02/2015.
 */
public class SpaceManager implements ISpaceManager {
    private Space space = new Space();
    @Override
    public ObjectInSpace createVehicleSpace(int x, int y, int z, int width, int height, int length, VehicleDirection vehicleDirection) {
        ObjectInSpace newObject = new ObjectInSpace(x,y,z,width,height,length, vehicleDirection);
        space.addObject(newObject);
        return newObject;
    }

    @Override
    public void removeObject(ObjectInSpace object) {

    }

    @Override
    public boolean checkTick(int x, int y, VehicleDirection vehicleDirection, int width, int height) {
        return false;
    }
}
