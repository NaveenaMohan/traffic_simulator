package reports;

import dataAndStructures.DataAndStructures;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import managers.globalconfig.GlobalConfigManager;
import managers.vehicle.IVehicleManager;
import managers.vehicle.Vehicle;
import managers.vehiclefactory.VehicleFactory;
import managers.vehiclefactory.VehicleFactoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * Created by Guera_000 on 23/02/2015.
 */
public class DCP {

    private DataAndStructures dataAndStructures;
    private Map<String, List<Double>> vehiclesVelocity = new HashMap<String, List<Double>>();
    private List<Double> velocity=new ArrayList<Double>();
    private Map<String, Double> averageVehiclesVelocity = new HashMap<String, Double>();
    private double avgVelocityPerVehicle=0, avgVelocityTotalVehicles=0, slipperiness=0, visibility=0, percentageMadeDest=0,
    avgTimeToDestination=0;
    private int totalNoCars=0;



    public DCP(DataAndStructures dataAndStructures) {

        this.dataAndStructures=dataAndStructures;
    }

    public void updateReportingInfo(DataAndStructures dataAndStructures){  // to be called every tick
        this.dataAndStructures=dataAndStructures;
    }

    public void reportInformation(){ //to be called by the UI when Report button is clicked
        getVehiclesInformation(); //gets the info of all of the cars
        getHowWasTheDay(); //reports the climatic conditions
        getVehiclesDensity(); // reports total of different types of vehicles
        getAvgVelocityTotalVehicles(); //reports average velocity of total of cars
        vehiclesMadeDestination(); // reports percentage of vehicles that made it to their destination along with the avg time it took them to get there
    }

    private void getVehiclesDensity(){
        System.out.println("From a total of" + totalNoCars +" vehicles, "
                + dataAndStructures.getGlobalConfigManager().getVehicleDensity().getCarDensity() + "are cars, " +
                dataAndStructures.getGlobalConfigManager().getVehicleDensity().getEmergencyVehicleDensity() + "are emergency vehicles, and"
                + dataAndStructures.getGlobalConfigManager().getVehicleDensity().getHeavyVehicleDensity() + "are heavy vehicles.");
    }

    private void vehiclesMadeDestination() {
        for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
            if (vehicle.getVehicle().getMadeDestination) {
                percentageMadeDest++;
                avgTimeToDestination+=vehicle.getVehicle().getArrivalDestTime();
            }
        }
        avgTimeToDestination=avgTimeToDestination/percentageMadeDest;
        percentageMadeDest=(percentageMadeDest*dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent())/totalNoCars;
        System.out.printf(percentageMadeDest + "per cent out of " + dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent() + "" +
                "per cent of the cars with a destination, made it to " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination()+ ".");
    }

    private void getHowWasTheDay(){
        slipperiness=dataAndStructures.getGlobalConfigManager().getClimaticCondition().getSlipperiness();
        visibility=dataAndStructures.getGlobalConfigManager().getClimaticCondition().getVisibility();

        //0 is not slippery
        //0 means very good visibility
        if (slipperiness==0.5 & visibility==0.5)
            System.out.println("It was a rainy day");
        if (slipperiness==0.9 & visibility==0.7)
            System.out.println("It was a snowy day");
        if (slipperiness==0 & visibility==0)
            System.out.println("It was a sunny day");
        if (slipperiness==0)
            System.out.printf("Roads were not slippery");
        if (slipperiness==0.5)
            System.out.printf("Roads were a bit slippery");
        if (slipperiness==0.9)
            System.out.printf("Roads were very slippery");
        if (visibility==0)
            System.out.printf("Visibility was good");
        if (visibility==0.5)
            System.out.printf("Visibility was moderate");
        if (visibility==0.9)
            System.out.printf("Visibility was poor");
    }

    private void getVehiclesInformation() {
        for(IVehicleManager vehicle : dataAndStructures.getVehicles()) {
            velocity.add(vehicle.getCurrentVelocity());

            if (vehiclesVelocity.containsKey(vehicle.getVehID()))
                vehiclesVelocity.get(vehicle.getVehID()).addAll(velocity); // adding velocities to an existing vehicle in the hash-map
            else
                vehiclesVelocity.put(vehicle.getVehID() + "", velocity); //first time adding a car with its velocities into the hash-map
        }
    }

    private void getAvgVelocityTotalVehicles() {

        getVehiclesAvgVelocity();

        for (Map.Entry<String, Double> velocityRecordsPerVehicle : averageVehiclesVelocity.entrySet()) {
            this.avgVelocityTotalVehicles=+ velocityRecordsPerVehicle.getValue();
        }
        this.avgVelocityTotalVehicles=avgVelocityTotalVehicles/dataAndStructures.getVehicles().size();

        System.out.println("The average velocity of all vehicles is: " + avgVelocityTotalVehicles);
    }

    private void getVehiclesAvgVelocity() {

        int numberOfTimes=0;
        for (Map.Entry<String, List<Double>> velocityRecordsPerVehicle : vehiclesVelocity.entrySet()) {
            for (Double record : velocityRecordsPerVehicle.getValue()) { //get all the velocities of each car to get its average
                avgVelocityPerVehicle = +record;
            }
            numberOfTimes = velocityRecordsPerVehicle.getValue().size();
            averageVehiclesVelocity.put(velocityRecordsPerVehicle.getKey() + "", avgVelocityPerVehicle / numberOfTimes);
            //System.out.println("Reporting Avg Velocity Vehicle ID: "+ speedRecordsPerVehicle.getKey());

        }
        //UNCOMMENT THE FOLLOWING IF AVERAGE VEL PER VEHICLE IS NEEDED
       /* for (Map.Entry<String, Double> velocityRecordsPerVehicle : averageVehiclesVelocity.entrySet()) {
            System.out.println("Vehicle ID: " + velocityRecordsPerVehicle.getKey());
            System.out.println("Vehicle Average Velocity: " + velocityRecordsPerVehicle.getValue());
        }*/

    }
}
