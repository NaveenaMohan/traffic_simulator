package reports;

import dataAndStructures.DataAndStructures;
import managers.vehicle.IVehicleManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;

/**
 * Created by Guera_000 on 23/02/2015.
 */
public class DCP extends JPanel{

    private DataAndStructures dataAndStructures;
    private Map<String, Double> vehiclesVelocity = new HashMap<String, Double>();
    private Map<String, Integer> numberOfTimes = new HashMap<String, Integer>();
    private Map<String, Double> averageVehiclesVelocity = new HashMap<String, Double>();
    private double avgVelocityTotalVehicles=0, slipperiness=0, visibility=0, percentageMadeDest=0,
    avgTimeToDestination=0;
    protected JTextArea textArea;
    private final static String newline = "\n";


    public DCP(DataAndStructures dataAndStructures) {
        super(new GridBagLayout());
        this.dataAndStructures=dataAndStructures;

    }

    public void updateReportingInfo(DataAndStructures dataAndStructures){  // to be called every tick
        this.dataAndStructures=dataAndStructures;
        getVehiclesInformation(); //gets the info of all of the cars
    }


    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Traffic Report");

        textArea = new JTextArea(15, 80);
        textArea.setFont(new Font("Serif", Font.BOLD, 25));
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setEditable(false);

        textArea.append("Report:" + newline + newline);
        getVehiclesDensity(); // reports total of different types of vehicles
        textArea.append(newline);
        getHowWasTheDay(); //reports the climatic conditions
        textArea.append(newline);
        getAvgVelocityTotalVehicles(); //reports average velocity of total of cars
        textArea.append(newline);
        vehiclesMadeDestination(); // reports percentage of vehicles that made it to their destination along with the avg time it took them to get there

        JScrollPane scrollPane = new JScrollPane(textArea);
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);

        //Add contents to the window.
        frame.add(scrollPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public void reportInformation(){ //to be called by the UI when Report button is clicked

        if(dataAndStructures.getGlobalConfigManager().getCurrentSecond()!=0) {

            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    createAndShowGUI();
                }
            });
        }
    }

    private void getVehiclesDensity(){

        if (dataAndStructures.getVehicles()!=null) {
            if (dataAndStructures.getVehicles().size() != 0) {
                textArea.append("From a total of " + this.dataAndStructures.getVehicles().size() + " vehicles, "
                        + dataAndStructures.getGlobalConfigManager().getVehicleDensity().getCarDensity() + " are cars, " +
                        dataAndStructures.getGlobalConfigManager().getVehicleDensity().getEmergencyVehicleDensity() + " fire trucks, ambulances and police vehicles, and "
                        + dataAndStructures.getGlobalConfigManager().getVehicleDensity().getHeavyVehicleDensity() + " are public transportation." + newline);
                }

        }

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void vehiclesMadeDestination() {
        dataAndStructures.getGlobalConfigManager().getRoute().setDestination("London");
        dataAndStructures.getGlobalConfigManager().getRoute().setTrafficPercent(.3);
        if(dataAndStructures.getGlobalConfigManager().getRoute().getDestination()!="") {
            totalNoCars = dataAndStructures.getVehicles().size();
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                vehicle.getVehicle().setMadeDestination(true);
                vehicle.getVehicle().setArrivalDestTime(dataAndStructures.getGlobalConfigManager().getCurrentSecond());
                if (vehicle.getVehicle().getMadeDestination()) {
                    percentageMadeDest++;
                    avgTimeToDestination = (avgTimeToDestination + vehicle.getVehicle().getArrivalDestTime());
                }
            }
            avgTimeToDestination=round((avgTimeToDestination / percentageMadeDest), 2);
            textArea.append("The average time for vehicles to get their destination was: " + avgTimeToDestination);
            percentageMadeDest = round((percentageMadeDest * dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent()) / dataAndStructures.getVehicles().size(), 2);
            textArea.append(" and " + percentageMadeDest + " per cent out of " + dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent() +
                    " per cent of the cars made it to their destination: " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + "." + newline);
        }
    }

    private void getHowWasTheDay(){

        slipperiness=dataAndStructures.getGlobalConfigManager().getClimaticCondition().getSlipperiness();
        visibility=dataAndStructures.getGlobalConfigManager().getClimaticCondition().getVisibility();

        //0 is not slippery
        //0 means very good visibility
        if (slipperiness==0.5 & visibility==0.5)
            textArea.append("It was a rainy day" + newline);
        if(slipperiness==0.9 & visibility==0.7)
            textArea.append("It was a snowy day" + newline);
        if (slipperiness==0.2 & visibility==0.2)
            textArea.append("It was a sunny day. " + newline);
        if (slipperiness==0)
            textArea.append("Roads were not slippery"+ newline);
        if (slipperiness==0.5)
            textArea.append("Roads were a bit slippery"+ newline);
        if (slipperiness==0.9)
            textArea.append("Roads were very slippery"+ newline);
        if (visibility==0)
            textArea.append("Visibility was good"+ newline);
        if (visibility==0.5)
            textArea.append("Visibility was moderate"+ newline);
        if (visibility==0.9)
            textArea.append("Visibility was poor"+ newline);

    }

    private void getVehiclesInformation() {
        if (dataAndStructures.getVehicles() != null)
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                if (vehicle.getVehID() != 0) {
                    if (vehiclesVelocity.containsKey(vehicle.getVehID() + "")) {
                        vehiclesVelocity.put(vehicle.getVehID() + "", (vehiclesVelocity.get(vehicle.getVehID() + "") + vehicle.getCurrentVelocity()));
                        numberOfTimes.put(vehicle.getVehID() + "", (numberOfTimes.get(vehicle.getVehID() + "") + 1));
                    } else {
                        vehiclesVelocity.put(vehicle.getVehID() + "", vehicle.getCurrentVelocity()); //first time adding a car with its velocities into the hash-map
                        numberOfTimes.put(vehicle.getVehID() + "", 1);
                    }

                }
            }
    }

    private void getAvgVelocityTotalVehicles() {

        getVehiclesAvgVelocity();
        avgVelocityTotalVehicles=0;
        for (Map.Entry<String, Double> velocityRecordsPerVehicle : averageVehiclesVelocity.entrySet()) {
            avgVelocityTotalVehicles =avgVelocityTotalVehicles+velocityRecordsPerVehicle.getValue();
        }
        avgVelocityTotalVehicles=round((avgVelocityTotalVehicles/dataAndStructures.getVehicles().size()), 2);
        textArea.append(newline + "The average velocity of all vehicles is: " + avgVelocityTotalVehicles + newline);
    }

    private void getVehiclesAvgVelocity() {
        //UNCOMMENT THE FOLLOWING IF AVERAGE VEL PER VEHICLE IS NEEDED
        for (Map.Entry<String, Double> velocityRecordsPerVehicle : vehiclesVelocity.entrySet()) {
            textArea.append("Vehicle ID: " + velocityRecordsPerVehicle.getKey() + "");
            textArea.append(" Vehicle Average Velocity: " + round(velocityRecordsPerVehicle.getValue()/numberOfTimes.get(velocityRecordsPerVehicle.getKey()+""),2));
            textArea.append(" Driver is: " + dataAndStructures.getVehicles().get(Integer.parseInt(velocityRecordsPerVehicle.getKey())-1).getVehicle().getDriver().getDriverBehaviorType() + newline);

            averageVehiclesVelocity.put(velocityRecordsPerVehicle.getKey() + "", (round((velocityRecordsPerVehicle.getValue() / numberOfTimes.get(velocityRecordsPerVehicle.getKey())), 2)));
        }

    }
}
