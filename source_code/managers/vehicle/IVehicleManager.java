package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.IGlobalConfigManager;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.space.Space;

public interface IVehicleManager {

    void move(ISpaceManager spaceManager, double time, IDataAndStructures dataAndStructures);

    boolean isVisible(int minX, int maxX, int minY, int maxY);

    Vehicle getVehicle();

    ObjectInSpace getObjectInSpace();

    double getCurrentVelocity();

    int getVehID();

    double getCurrentAcceleration();

}
