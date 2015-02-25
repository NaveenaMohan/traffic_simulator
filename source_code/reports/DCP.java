package reports;

import managers.globalconfig.GlobalConfigManager;
import managers.vehicle.Vehicle;
import managers.vehiclefactory.VehicleFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * Created by Guera_000 on 23/02/2015.
 */
public class DCP {

    private Map<String, List<Double>> vehiclesSpeed = new HashMap<String, List<Double>>();
    private List<Double> Speed=new ArrayList<Double>();
    private Map<String, Double> averageVehiclesSpeed = new HashMap<String, Double>();
    private double avgSpeed=0;

    public DCP() {
    }

    public DCP(Vehicle vehicle) {
        Speed.add(vehicle.getCurrentAcceleration());

        if (vehiclesSpeed.containsKey(vehicle.getVehID()))
            vehiclesSpeed.get(vehicle.getVehID()).addAll(Speed);
        else
            vehiclesSpeed.put(vehicle.getVehID()+"", Speed);
    }


    public void getVehiclesAvgSpeed(Vehicle vehicle) {
        Speed.add(vehicle.getCurrentAcceleration());

        if (vehiclesSpeed.containsKey(vehicle.getVehID()))
            vehiclesSpeed.get(vehicle.getVehID()).addAll(Speed);
        else
            vehiclesSpeed.put(vehicle.getVehID()+"", Speed);

        int numberOfTimes=0;
        for (Map.Entry<String, List<Double>> speedRecordsPerVehicle : vehiclesSpeed.entrySet() ) {
            for (Double record : speedRecordsPerVehicle.getValue()){
                avgSpeed=+record;
                numberOfTimes++;
            }
            averageVehiclesSpeed.put(speedRecordsPerVehicle.getKey()+"", avgSpeed/numberOfTimes);
            //System.out.println("Reporting Avg Velocity Vehicle ID: "+ speedRecordsPerVehicle.getKey());

        }
        for (Map.Entry<String, Double> speedRecordsPerVehicle : averageVehiclesSpeed.entrySet() ) {
                System.out.println("Vehicle ID: " + speedRecordsPerVehicle.getKey());
                System.out.println("Vehicle Average Speed: " + speedRecordsPerVehicle.getValue());
            }
    }
}
