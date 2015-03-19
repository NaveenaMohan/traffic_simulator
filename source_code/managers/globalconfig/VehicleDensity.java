package managers.globalconfig;

import java.io.Serializable;

/**
 * Created by naveena on 09/02/15.
 */
public class VehicleDensity implements Serializable {

    private double carDensity=0.65;
    private double heavyVehicleDensity=0.3;
    private double emergencyVehicleDensity=0.05;
    private double creationRatePerSecond=0.5;

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

    public double getCreationRatePerSecond() {
        return creationRatePerSecond;
    }

    public void setCreationRatePerSecond(double creationRatePerSecond) {
        this.creationRatePerSecond = creationRatePerSecond;
    }
}
