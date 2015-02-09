package managers.space;

/**
 * Created by naveena on 08/02/15.
 */
public interface ISpaceManager {

    ObjectInSpace createVehicleSpace(int x, int y, int width, int height);

    void removeObject(ObjectInSpace object);

    boolean checkTick(int x, int y, VehicleDirection vehicleDirection, int width, int height);

}
