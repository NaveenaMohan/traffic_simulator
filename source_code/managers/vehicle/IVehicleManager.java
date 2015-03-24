package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.space.VehicleDirection;

import java.io.Serializable;

public interface IVehicleManager extends Serializable {

    public void move(ISpaceManager spaceManager, double time, IDataAndStructures dataAndStructures);

    public boolean isVisible(int minX, int maxX, int minY, int maxY);

    public IVehicleManager getVehicle();

    public ObjectInSpace getObjectInSpace();

    public double getCurrentVelocity();

    public int getVehID();

    public double getCurrentAcceleration();

    public boolean isVisible();

    public VehicleDirection getDirection();

    public double getDepthInCurrentRUnit();

    public String getDestination();

    public boolean getMadeDestination();

    public double getArrivalDestTime();

    public VehicleMotor getVehicleMotor();

    public double getTimeCreated();
}
