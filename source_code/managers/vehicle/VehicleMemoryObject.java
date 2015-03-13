package managers.vehicle;

import managers.runit.IRUnitManager;

/**
 * Created by Fabians on 18/02/2015.
 * used by VehiclePerception, state and motor to recognise objects and assign distances to them
 */
public class VehicleMemoryObject<T> {
    private IRUnitManager rUnit;
    private T object;
    private double distance;
    private double velocity;
    private boolean passable;
    private boolean isInLeft;

    public boolean isInLeft() {
        return isInLeft;
    }

    public boolean isPassable() {
        return passable;
    }

    public double getVelocity() {
        return velocity;
    }

    public VehicleMemoryObject(IRUnitManager rUnit, T object, double distance, double velocity, boolean passable, boolean isInLeft) {
        this.rUnit = rUnit;
        this.object = object;
        this.distance=distance;
        this.velocity=velocity;
        this.passable=passable;
        this.isInLeft=isInLeft;
    }

    public IRUnitManager getrUnit() {
        return rUnit;
    }

    public T getObject() {
        return object;
    }

    public double getDistance()
    {
        return distance;
    }
}
