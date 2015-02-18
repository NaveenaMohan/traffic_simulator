package managers.vehicle;

import managers.runit.IRUnitManager;
import managers.runit.RUnit;

/**
 * Created by Fabians on 18/02/2015.
 * used by VehiclePerception, state and motor to recognise objects and assign distances to them
 */
public class VehicleMemoryObject<T> {
    private IRUnitManager rUnit;
    private T object;
    private double distance;

    public VehicleMemoryObject(IRUnitManager rUnit, T object, double distance) {
        this.rUnit = rUnit;
        this.object = object;
        this.distance=distance;
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
