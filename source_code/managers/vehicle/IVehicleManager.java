package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.space.VehicleDirection;

import java.io.Serializable;

public interface IVehicleManager extends Serializable {

    void move(ISpaceManager spaceManager, double time, IDataAndStructures dataAndStructures);

    boolean isVisible(int minX, int maxX, int minY, int maxY);

    IVehicleManager getVehicle();

    ObjectInSpace getObjectInSpace();

    double getCurrentVelocity();

    int getVehID();

    double getCurrentAcceleration();

    boolean isVisible();

    public VehicleDirection getDirection();

    double getDepthInCurrentRUnit();

    String getDestination();

    boolean getMadeDestination();

    double getArrivalDestTime();

    VehicleMotor getVehicleMotor();

    double getTimeCreated();
}
