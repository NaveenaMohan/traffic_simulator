package managers.globalconfig;

/**
 * Created by naveena on 09/02/15.
 */
public class VehicleDensity {

    private double carDensity=0.5;
    private double heavyVehicleDensity=0.0;
    private double emergencyVehicleDensity=0.5;
    private double creationRatePerSecond=0.5;

    public double getCarDensity() {
        return carDensity;
    }

    public void setCarDensity(double carDensity) {

        if(carDensity+heavyVehicleDensity+emergencyVehicleDensity==1){
            this.carDensity = carDensity;
        }else throw new IllegalArgumentException("Density of cars+heavies+emergencies not matching total of vehicles");

    }

    public double getHeavyVehicleDensity() {
        return heavyVehicleDensity;
    }

    public void setHeavyVehicleDensity(double heavyVehicleDensity) {
        if(carDensity+heavyVehicleDensity+emergencyVehicleDensity==1){
            this.heavyVehicleDensity = heavyVehicleDensity;
        }else throw new IllegalArgumentException("Density of cars+heavies+emergencies not matching total of vehicles");

    }

    public double getEmergencyVehicleDensity() {
        return emergencyVehicleDensity;
    }

    public void setEmergencyVehicleDensity(double emergencyVehicleDensity) {
        if(carDensity+heavyVehicleDensity+emergencyVehicleDensity==1){
            this.emergencyVehicleDensity = emergencyVehicleDensity;
        }else throw new IllegalArgumentException("Density of cars+heavies+emergencies not matching total of vehicles");
    }

    public double getCreationRatePerSecond() {
        return creationRatePerSecond;
    }

    public void setCreationRatePerSecond(int creationRatePerSecond) {
        this.creationRatePerSecond = creationRatePerSecond;
    }
}
