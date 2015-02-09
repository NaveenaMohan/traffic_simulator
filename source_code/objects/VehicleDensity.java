package objects;

/**
 * Created by naveena on 09/02/15.
 */
public class VehicleDensity {

    private double carDensity;
    private double heavyVehicleDensity;
    private double emergencyVehicleDensity;
    private int vehicleTotalCount;

    public double getCarDensity() {
        return carDensity;
    }

    public void setCarDensity(double carDensity) {
        this.carDensity = carDensity;
    }

    public double getHeavyVehicleDensity() {
        return heavyVehicleDensity;
    }

    public void setHeavyVehicleDensity(double heavyVehicleDensity) {
        this.heavyVehicleDensity = heavyVehicleDensity;
    }

    public double getEmergencyVehicleDensity() {
        return emergencyVehicleDensity;
    }

    public void setEmergencyVehicleDensity(double emergencyVehicleDensity) {
        this.emergencyVehicleDensity = emergencyVehicleDensity;
    }

    public int getVehicleTotalCount() {
        return vehicleTotalCount;
    }

    public void setVehicleTotalCount(int vehicleTotalCount) {
        this.vehicleTotalCount = vehicleTotalCount;
    }
}
