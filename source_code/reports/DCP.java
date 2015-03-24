package reports;

import common.Common;
import dataAndStructures.IDataAndStructures;
import managers.space.ObjectInSpace;
import managers.vehicle.IVehicleManager;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guera_000 on 23/02/2015.
 */
public class DCP extends JPanel {

    private final static String newline = "\n";
    private boolean firstTimeOpen = true;
    private boolean isFrameClosed = false;
    private StyleContext context3 = new StyleContext();
    private Style style2;
    private Style style3;
    private int vehiclesEnroute = 0;
    private JFrame frame;
    private IDataAndStructures dataAndStructures;
    private Map<String, Double> vehiclesVelocity = new HashMap<String, Double>();
    private Map<String, Integer> numberOfTimes = new HashMap<String, Integer>();
    private Map<String, Double> averageVehiclesVelocity = new HashMap<String, Double>();
    private double avgVelocityTotalVehicles = 0, slipperiness = 0, visibility = 0, percentageMadeDest = 0, rate = 0,
            avgTimeToDestination = 0;
    private StyledDocument document;
    private int cars = 0, emergencies = 0, heavyLoads = 0;

    public DCP(IDataAndStructures dataAndStructures) {
        this.dataAndStructures = dataAndStructures;

    }

    public void updateReportingInfo(IDataAndStructures dataAndStructures) {  // to be called every tick
        this.dataAndStructures = dataAndStructures;
        getVehiclesInformation(); //gets the info of all of the cars
    }


    private void createAndShowGUI() {
        if (!firstTimeOpen)
            frame.dispose();
        avgVelocityTotalVehicles = 0;
        slipperiness = 0;
        visibility = 0;
        percentageMadeDest = 0;
        avgTimeToDestination = 0;
        cars = 0;
        emergencies = 0;
        heavyLoads = 0;
        rate = 0;
        vehiclesEnroute = 0;

        frame = new JFrame("Traffic Report");
        StyleContext context = new StyleContext();
        StyleContext context2 = new StyleContext();
        document = new DefaultStyledDocument(context3);

        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
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

        try {
            document.insertString(document.getLength(), "Vehicles Information " + newline, style);
            getVehiclesDensity(); // reports total of different types of vehicles
            document.insertString(document.getLength(), "Forecast " + newline, style);
            getHowWasTheDay(); //reports the climatic conditions
            document.insertString(document.getLength(), "Traffic Information " + newline, style);
            getAvgVelocityTotalVehicles(); //reports average velocity of total of cars
            congestionRate();
            if (!dataAndStructures.getGlobalConfigManager().getRoute().getDestination().equals("")) {
                document.insertString(document.getLength(), "Destination " + newline, style);
                vehiclesMadeDestination(); // reports percentage of vehicles that made it to their destination along with the avg time it took them to get there
            }
        } catch (BadLocationException badLocationException) {
            System.err.println("Error" + badLocationException.getMessage());
        }
        JTextPane textPane = new JTextPane(document);
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(900, 1050);
        frame.setVisible(true);
        firstTimeOpen = false;
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                isFrameClosed = false;
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                isFrameClosed = true;
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public void reportInformation() { //to be called by the UI when Report button is clicked
        if (dataAndStructures.getGlobalConfigManager().getCurrentSecond() != 0) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
        }
    }

    public void getVehiclesDensity() {
        int carsNoVisible = 0, heavyLoadsNoVisible = 0, emergenciesNoVisible = 0;

        if (dataAndStructures.getVehicles() != null) {
            if (dataAndStructures.getVehicles().size() != 0) {
                for (ObjectInSpace vehicle : dataAndStructures.getSpaceManager().getObjects()) {
                    if (vehicle.isVisible()) {
                        switch (vehicle.getVehicleType()) {
                            case car:
                                cars++;
                                break;
                            case heavyLoad:
                                heavyLoads++;
                                break;
                            case emergency:
                                emergencies++;
                                break;
                        }
                    }
                    if (!vehicle.isVisible()) {
                        switch (vehicle.getVehicleType()) {
                            case car:
                                carsNoVisible++;
                                break;
                            case heavyLoad:
                                heavyLoadsNoVisible++;
                                break;
                            case emergency:
                                emergenciesNoVisible++;
                                break;
                        }
                    }
                }
                try {
                    int vehiclesVisible = cars + heavyLoads + emergencies;
                    int totalCars = carsNoVisible + cars;
                    int totalHeavy = heavyLoadsNoVisible + heavyLoads;
                    int totalEmergency = emergenciesNoVisible + emergencies;
                    document.insertString(document.getLength(), "Total of created vehicles: ", style2);
                    document.insertString(document.getLength(), this.dataAndStructures.getVehicles().size() + " (Cars: " +
                            totalCars + ", Emergency vehicles: " + totalEmergency + ", Public Transportation: " + totalHeavy + ")" + newline, style3);
                    document.insertString(document.getLength(), "Total of vehicles still on the road network: ", style2);
                    document.insertString(document.getLength(), vehiclesVisible + newline, style3);
                    document.insertString(document.getLength(), "Cars driving: ", style2);
                    document.insertString(document.getLength(), cars + newline, style3);
                    document.insertString(document.getLength(), "Ambulances & Police Vehicles driving: ", style2);
                    document.insertString(document.getLength(), emergencies + newline, style3);
                    document.insertString(document.getLength(), "Public Transportation driving: ", style2);
                    document.insertString(document.getLength(), heavyLoads + newline + newline + newline, style3);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void vehiclesMadeDestination() {
        int vehiclesWithDest = 0, vehiclesLost = 0;
        if (!dataAndStructures.getGlobalConfigManager().getRoute().getDestination().equals("") && dataAndStructures.getVehicles().size() != 0) {
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                if (!vehicle.getVehicle().getDestination().equals("") && vehicle.isVisible() && !vehicle.getVehicle().getMadeDestination())
                    vehiclesEnroute++; //counts vehicles that are still visible and going to their destination
                if (!vehicle.getVehicle().getDestination().equals("") && !vehicle.isVisible() && !vehicle.getVehicle().getMadeDestination())
                    vehiclesLost++;
                if (vehicle.getVehicle().getMadeDestination()) {
                    percentageMadeDest++;//counts vehicles that already made it to their destination
                    avgTimeToDestination = (avgTimeToDestination + (vehicle.getVehicle().getArrivalDestTime() - vehicle.getVehicle().getTimeCreated()));
                }
                if (!vehicle.getVehicle().getDestination().equals(""))
                    vehiclesWithDest++;
            }
            avgTimeToDestination = Common.round((avgTimeToDestination / percentageMadeDest), 2);
            avgTimeToDestination = avgTimeToDestination / 60; //to give time in minutes
            try {

                document.insertString(document.getLength(), "Destination Name: ", style2);
                document.insertString(document.getLength(), dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + newline, style3);

                document.insertString(document.getLength(), "Total vehicles with destination " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + ": ", style2);
                document.insertString(document.getLength(), vehiclesWithDest + " (" + Common.round(((vehiclesWithDest * 100) / dataAndStructures.getVehicles().size()), 2) + "%)" + newline, style3);

                document.insertString(document.getLength(), "Vehicles that made it to " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + ": ", style2);
                document.insertString(document.getLength(), percentageMadeDest / 1 + " (", style3);

                percentageMadeDest = Common.round((percentageMadeDest * dataAndStructures.getGlobalConfigManager().getRoute().getTrafficPercent() * 100) / vehiclesWithDest, 2);
                document.insertString(document.getLength(), percentageMadeDest + "%)" + newline, style3);

                document.insertString(document.getLength(), "Avg. Time for vehicles to get " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + ": ", style2);
                document.insertString(document.getLength(), Common.round(avgTimeToDestination, 2) + " mins" + newline, style3);

                document.insertString(document.getLength(), "Vehicles still enroute to " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + ": ", style2);
                document.insertString(document.getLength(), vehiclesEnroute + " (" + Common.round((vehiclesEnroute * 100) / vehiclesWithDest, 2) + "%)" + newline, style3);

                document.insertString(document.getLength(), "Vehicles got lost, did not make it to " + dataAndStructures.getGlobalConfigManager().getRoute().getDestination() + ": ", style2);
                document.insertString(document.getLength(), vehiclesLost + " (" + Common.round((vehiclesLost * 100) / vehiclesWithDest, 2) + "%)" + newline, style3);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void getHowWasTheDay() {
        //0 is not slippery
        //0 means very good visibility
        try {
            slipperiness = dataAndStructures.getGlobalConfigManager().getClimaticCondition().getSlipperiness();
            visibility = dataAndStructures.getGlobalConfigManager().getClimaticCondition().getVisibility();
            if (slipperiness == 0.5 && visibility == 0.5)
                document.insertString(document.getLength(), "It's raining" + newline, style2);
            if (slipperiness == 0.9 && visibility == 0.7)
                document.insertString(document.getLength(), "It's snowing" + newline, style2);
            if (slipperiness == 0.0 && visibility == 0.0)
                document.insertString(document.getLength(), "It's sunny" + newline, style2);
            if (slipperiness >= 0.0 && slipperiness <= 0.4)
                document.insertString(document.getLength(), "Roads are not slippery" + newline, style3);
            if (slipperiness >= 0.5 && slipperiness <= 0.6)
                document.insertString(document.getLength(), "Roads are a bit slippery" + newline, style3);
            if (slipperiness >= 0.7 && slipperiness <= 1)
                document.insertString(document.getLength(), "Roads are very slippery" + newline, style3);
            if (visibility >= 0.0 && visibility <= 0.4)
                document.insertString(document.getLength(), "Visibility is good" + newline, style3);
            if (visibility >= 0.5 && visibility <= 0.6)
                document.insertString(document.getLength(), "Visibility is moderate" + newline, style3);
            if (visibility >= 0.7 && visibility <= 1)
                document.insertString(document.getLength(), "Visibility is poor" + newline, style3);
            document.insertString(document.getLength(), newline + newline, style3);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    private void getVehiclesInformation() {
        if (dataAndStructures.getVehicles() != null)
            for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
                if (vehicle.getVehID() != 0 && vehicle.isVisible()) {
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

    private void congestionRate() {
        int vehiclesVisible = 0;
        rate = 0;
        for (IVehicleManager vehicle : dataAndStructures.getVehicles()) {
            if (vehicle.getVehID() != 0 && vehicle.isVisible()) {
                vehiclesVisible++;
                rate = rate + (vehicle.getVehicle().getVehicleMotor().getMaximumVelocity() - vehicle.getVehicle().getCurrentVelocity());
            }
        }
        rate = Common.round(rate / vehiclesVisible, 1);
        try {
            document.insertString(document.getLength(), "Traffic Intensity: ", style2);
            if (rate >= 0 && rate < 16)
                document.insertString(document.getLength(), "Uncongested, No Traffic", style3);
            if (rate >= 16 && rate < 26)
                document.insertString(document.getLength(), "A bit congested", style3);
            if (rate >= 26 && rate < 36)
                document.insertString(document.getLength(), "Congested", style3);
            if (rate >= 36)
                document.insertString(document.getLength(), "Traffic jam", style3);
            document.insertString(document.getLength(), newline, style3);
            document.insertString(document.getLength(), "Average Congestion Rate (max speed - current velocity): ", style2);
            document.insertString(document.getLength(), rate + " Km/Hour below max limit speed" + newline, style3);
            document.insertString(document.getLength(), newline + newline, style3);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void getAvgVelocityTotalVehicles() {

        getVehiclesAvgVelocity();
        avgVelocityTotalVehicles = 0;
        for (Map.Entry<String, Double> velocityRecordsPerVehicle : averageVehiclesVelocity.entrySet()) {
            avgVelocityTotalVehicles = avgVelocityTotalVehicles + velocityRecordsPerVehicle.getValue();
        }
        avgVelocityTotalVehicles = Common.round(((avgVelocityTotalVehicles / averageVehiclesVelocity.size()) * 3600 / 1000), 2);
        try {
            document.insertString(document.getLength(), "Average Velocity: ", style2);
            document.insertString(document.getLength(), avgVelocityTotalVehicles + " Km/Hour" + newline, style3);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void getVehiclesAvgVelocity() {
        //storing the average vel per vehicle of all vehicles
        for (Map.Entry<String, Double> velocityRecordsPerVehicle : vehiclesVelocity.entrySet()) {
            averageVehiclesVelocity.put(velocityRecordsPerVehicle.getKey() + "", (Common.round((velocityRecordsPerVehicle.getValue() / numberOfTimes.get(velocityRecordsPerVehicle.getKey())), 2)));
        }

    }
}
