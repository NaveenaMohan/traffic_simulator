package reports;

import dataAndStructures.DataAndStructures;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.vehicle.IVehicleManager;

import javax.swing.*;
import javax.swing.plaf.TextUI;
import javax.swing.text.*;
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
    private double avgVelocityTotalVehicles=0, slipperiness=0, visibility=0, percentageMadeDest=0, rate=0,
    avgTimeToDestination=0;
    protected JTextArea textArea;
    private StyledDocument document;
    private final static String newline = "\n";
    private int cars=0, emergencies=0, heavyLoads=0;
    StyleContext context = new StyleContext();
    StyleContext context2 = new StyleContext();
    StyleContext context3 = new StyleContext();
    Style style, style2, style3;
    boolean buttonPressed=false;
    int refresh=0;


    public DCP(DataAndStructures dataAndStructures) {
       // super(new GridBagLayout());
        this.dataAndStructures=dataAndStructures;

    }

    public void updateReportingInfo(DataAndStructures dataAndStructures){  // to be called every tick
        this.dataAndStructures=dataAndStructures;
        getVehiclesInformation(); //gets the info of all of the cars
    }


    private void createAndShowGUI() {

        avgVelocityTotalVehicles=0; slipperiness=0; visibility=0;
        percentageMadeDest=0; avgTimeToDestination=0; cars=0; emergencies=0;
        heavyLoads=0; rate=0;
        JFrame frame = new JFrame("Traffic Report");

        context = new StyleContext();
        context2 = new StyleContext();
        document = new DefaultStyledDocument(context3);


        style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(style, 25);
        StyleConstants.setSpaceAbove(style, 4);
        StyleConstants.setSpaceBelow(style, 4);
        StyleConstants.setBold(style, true);
        StyleConstants.setUnderline(style, true);

        style2 = context2.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style2, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontSize(style2, 20);
        StyleConstants.setSpaceAbove(style2, 4);
        StyleConstants.setSpaceBelow(style2, 4);
        StyleConstants.setLeftIndent(style2, (float) 2.5);
        StyleConstants.setBold(style2, true);

        style3 = context3.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style3, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontSize(style3, 20);
        StyleConstants.setSpaceAbove(style3, 4);
        StyleConstants.setSpaceBelow(style3, 4);
        StyleConstants.setLeftIndent(style3, (float) 2.5);

//        buttonPressed=true;
//       while(dataAndStructures.getGlobalConfigManager().getCurrentSecond()!=0)
//        {
//            refresh++;
//            if(refresh==100 || buttonPressed==true){
               try {
                    document.insertString(document.getLength(), "Vehicles Information: " + newline, style);
                    getVehiclesDensity(); // reports total of different types of vehicles
                    document.insertString(document.getLength(), "Climatic Conditions: " + newline, style);
                    getHowWasTheDay(); //reports the climatic conditions
                    document.insertString(document.getLength(), "Traffic Information: " + newline, style);
                    getAvgVelocityTotalVehicles(); //reports average velocity of total of cars
                    congestionRate();
                    document.insertString(document.getLength(), "Destination: " + newline, style);
                    vehiclesMadeDestination(); // reports percentage of vehicles that made it to their destination along with the avg time it took them to get there
                } catch (BadLocationException badLocationException) {
                    System.err.println("Error");
                }
//                refresh=0;
//                buttonPressed=false;
//            }
//        }

        JTextPane textPane = new JTextPane(document);
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(800, 900);
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

    private void getVehiclesDensity() {

        if (dataAndStructures.getVehicles()!=null) {
            if (dataAndStructures.getVehicles().size() != 0) {
                for (ObjectInSpace vehicle : dataAndStructures.getSpaceManager().getObjects()) {
                    switch (vehicle.getVehicleType()){
                        case car: cars++;
                            break;
                        case heavyLoad: heavyLoads++;
                            break;
                        case emergency: emergencies++;
                            break;
                    }
                }
                try {
                    document.insertString(document.getLength(), "Total of vehicles: ", style2);
                    document.insertString(document.getLength(), this.dataAndStructures.getVehicles().size() + newline , style3);
                    document.insertString(document.getLength(), "Cars: ", style2);
                    document.insertString(document.getLength(), cars + newline, style3);
                    document.insertString(document.getLength(), "Ambulances & Police Vehicles: ", style2);
                    document.insertString(document.getLength(), emergencies + newline, style3);
                    document.insertString(document.getLength(), "Public Transportation: ", style2);
                    document.insertString(document.getLength(), heavyLoads + newline + newline + newline, style3);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
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
        dataAndStructures.getGlobalConfigManager().getRoute().setTrafficPercent(1);
        if(dataAndStructures.getGlobalConfigManager().getRoute().getDestination()!="") {
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                vehicle.getVehicle().setMadeDestination(true);
                vehicle.getVehicle().setArrivalDestTime(dataAndStructures.getGlobalConfigManager().getCurrentSecond());
                if (vehicle.getVehicle().getMadeDestination()) {
                    percentageMadeDest++;
                    avgTimeToDestination = (avgTimeToDestination + (vehicle.getVehicle().getArrivalDestTime()-vehicle.getVehicle().getTimeCreated()));
                }
            }
            avgTimeToDestination=round((avgTimeToDestination / percentageMadeDest), 2);
            avgTimeToDestination=avgTimeToDestination/60; //to give time in minutes
            try {

                document.insertString(document.getLength(), "Destination Name: ", style2);
                document.insertString(document.getLength(), dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + newline, style3);

                document.insertString(document.getLength(), "Vehicles going to " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination()+ ": ", style2);
                document.insertString(document.getLength(), round(((dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent()*100*dataAndStructures.getVehicles().size())/100),2)
                        + "(" +dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent()*100 + "%)" + newline, style3);
                document.insertString(document.getLength(), "Vehicles that made it to " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination()+ ": ", style2);
                document.insertString(document.getLength(), percentageMadeDest+"(", style3);
                percentageMadeDest = round((percentageMadeDest * dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent()*100) / dataAndStructures.getVehicles().size(), 2);
                document.insertString(document.getLength(), percentageMadeDest+"%)" + newline, style3);

                document.insertString(document.getLength(), "Avg. Time for vehicles to get " +dataAndStructures.getGlobalConfigManager().getRoute().getDestination()+ ": ", style2);
                document.insertString(document.getLength(), round(avgTimeToDestination,2) +" mins" + newline, style3);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void getHowWasTheDay(){

        slipperiness=dataAndStructures.getGlobalConfigManager().getClimaticCondition().getSlipperiness();
        visibility=dataAndStructures.getGlobalConfigManager().getClimaticCondition().getVisibility();
        //0 is not slippery
        //0 means very good visibility
        try {
        if (slipperiness==0.5 & visibility==0.5)
            document.insertString(document.getLength(), "It was a rainy day" + newline, style2);
        if(slipperiness==0.9 & visibility==0.7)
            document.insertString(document.getLength(), "It was a snowy day" + newline, style2);
        if (slipperiness==0.2 & visibility==0.2)
            document.insertString(document.getLength(), "It was a sunny day" + newline, style2);
        if (slipperiness>=0 & slipperiness<=0.4)
            document.insertString(document.getLength(), "Roads were not slippery" + newline, style3);
        if (slipperiness>=0.5 & slipperiness<=0.6)
            document.insertString(document.getLength(), "Roads were a bit slippery" + newline, style3);
        if (slipperiness>=0.7 & slipperiness<=1)
            document.insertString(document.getLength(), "Roads were very slippery" + newline, style3);
        if (visibility>=0 & visibility<=0.4)
            document.insertString(document.getLength(), "Visibility was good" + newline, style3);
        if (visibility>=0.5 & visibility<=0.6)
            document.insertString(document.getLength(), "Visibility was moderate" + newline, style3);
        if (visibility >= 0.7 & visibility <= 1)
            document.insertString(document.getLength(), "Visibility was poor" + newline, style3);
        document.insertString(document.getLength(), newline + newline, style3);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

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
    private void congestionRate(){
        for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
            if (vehicle.getVehID() != 0) {
                    rate=rate +(vehicle.getVehicle().getVehicleMotor().getMaximumVelocity()-vehicle.getVehicle().getCurrentVelocity());
            }
        }
        rate=round(rate/dataAndStructures.getVehicles().size(),2);
        try {
            document.insertString(document.getLength(), "Traffic Intensity: ", style2);
            if (rate>=0 && rate<=10)
                document.insertString(document.getLength(), "Uncongested, No Traffic", style3);
            if (rate>=11 && rate<=19)
                document.insertString(document.getLength(), "Roads a bit congested", style3);
            if (rate>=20)
                document.insertString(document.getLength(), "Traffic jam", style3);
            document.insertString(document.getLength(), newline, style3);
            document.insertString(document.getLength(), "Congestion Rate (max speed - current velocity): ", style2);
            document.insertString(document.getLength(), rate + newline, style3);
            document.insertString(document.getLength(), newline + newline, style3);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    private void getAvgVelocityTotalVehicles() {

        getVehiclesAvgVelocity();
        avgVelocityTotalVehicles=0;
        for (Map.Entry<String, Double> velocityRecordsPerVehicle : averageVehiclesVelocity.entrySet()) {
            avgVelocityTotalVehicles =avgVelocityTotalVehicles+velocityRecordsPerVehicle.getValue();
        }
        avgVelocityTotalVehicles=round(((avgVelocityTotalVehicles/dataAndStructures.getVehicles().size())*3600/1000), 2);
        try {
            document.insertString(document.getLength(), "Average Velocity: ", style2);
            document.insertString(document.getLength(),  avgVelocityTotalVehicles +" Km/Hour" + newline, style3);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void getVehiclesAvgVelocity() {
        //UNCOMMENT THE FOLLOWING IF AVERAGE VEL PER VEHICLE IS NEEDED
        for (Map.Entry<String, Double> velocityRecordsPerVehicle : vehiclesVelocity.entrySet()) {
           // textArea.append("Vehicle ID: " + velocityRecordsPerVehicle.getKey() + "");
           // textArea.append(" Vehicle Average Velocity: " + round(velocityRecordsPerVehicle.getValue()/numberOfTimes.get(velocityRecordsPerVehicle.getKey()+""),2));
           // textArea.append(" Driver is: " + dataAndStructures.getVehicles().get(Integer.parseInt(velocityRecordsPerVehicle.getKey())-1).getVehicle().getDriver().getDriverBehaviorType() + newline);

            averageVehiclesVelocity.put(velocityRecordsPerVehicle.getKey() + "", (round((velocityRecordsPerVehicle.getValue() / numberOfTimes.get(velocityRecordsPerVehicle.getKey())), 2)));
        }

    }
}
