package ui;

import dataAndStructures.DataAndStructures;
import engine.SimEngine;
import managers.globalconfig.*;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.TrafficLight;
import managers.vehiclefactory.VehicleFactoryManager;
import reports.DCP;
import ui.components.DrawingBoard;
import ui.components.NoVehicleFactoryDialogBox;
import ui.components.RangeSlider;
import ui.fileops.ExportImportObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class  Traffic_Simulator {
    private static Traffic_Simulator window;
    ClimaticCondition climaticCondition = new ClimaticCondition();
    private JFrame trafficSimulatorFrame;
    private RoadNetworkManager roadNetworkManager = new RoadNetworkManager(new RoadNetwork());
    private VehicleFactoryManager vehicleFactoryManager = new VehicleFactoryManager();
    private GlobalConfigManager globalConfigManager = new GlobalConfigManager(
            100,//ticks per second
            0.5,//metres per RUnit
            climaticCondition,
            new DriverBehavior(),
            new VehicleDensity(),
            new Route()
    );
    DataAndStructures dataAndStructures = new DataAndStructures(roadNetworkManager, vehicleFactoryManager, globalConfigManager);
    private DCP dcp=new DCP(dataAndStructures);
    private boolean openReport=false;
    private SimEngine simEngine = new SimEngine(dataAndStructures,dcp);
    private DefaultTableModel model;


    /**
     * Create the application.
     */
    public Traffic_Simulator() {
        initialize();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new Traffic_Simulator();
                    window.trafficSimulatorFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        trafficSimulatorFrame = new JFrame();
        trafficSimulatorFrame.getContentPane().setBackground(UIManager.getColor("InternalFrame.background"));
        trafficSimulatorFrame.getContentPane().setBounds(new Rectangle(900, 900, 0, 0));
        trafficSimulatorFrame.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
        trafficSimulatorFrame.setTitle("TRAFFIC SIMULATOR");
        trafficSimulatorFrame.getContentPane().setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 16));
        trafficSimulatorFrame.setBounds(1000, 700, 1265, 697);
        trafficSimulatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trafficSimulatorFrame.getContentPane().setLayout(null);

        // Road Infrastructure Panel
        JPanel roadInfraStructurePanel = new JPanel();
        roadInfraStructurePanel.setBackground(UIManager.getColor("InternalFrame.background"));
        roadInfraStructurePanel.setBounds(6, 6, 284, 416);
        trafficSimulatorFrame.getContentPane().add(roadInfraStructurePanel);
        roadInfraStructurePanel.setLayout(null);

        JLabel txtrRoadInfrastructure = new JLabel();
        txtrRoadInfrastructure.setBounds(6, 6, 278, 23);
        txtrRoadInfrastructure.setBackground(Color.BLACK);
        //txtrRoadInfrastructure.setForeground(new Color(255, 255, 255));
        txtrRoadInfrastructure.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
        txtrRoadInfrastructure.setText("                   ROAD INFRASTRUCTURE");
        roadInfraStructurePanel.add(txtrRoadInfrastructure);

        JButton single_lane = new JButton();
        single_lane.setBounds(26, 27, 70, 70);
        single_lane.setToolTipText("Add a Single Lane Road");
        single_lane.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Single Lane.jpg")));
        roadInfraStructurePanel.add(single_lane);


        JButton double_lane = new JButton();
        double_lane.setBounds(108, 27, 70, 70);
        double_lane.setToolTipText("Add a Double Lane Road");
        double_lane.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Double Lane.jpg")));
        roadInfraStructurePanel.add(double_lane);

        final JButton traffic_light = new JButton();
        traffic_light.setBounds(190, 27, 70, 70);
        traffic_light.setToolTipText("Add a Traffic Light");
        traffic_light.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Traffic Light.png")));
        roadInfraStructurePanel.add(traffic_light);

        JButton zebra_crossing = new JButton();
        zebra_crossing.setBounds(26, 98, 70, 70);
        zebra_crossing.setToolTipText("Add a Zebra Crossing");
        zebra_crossing.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Zebra Crossing.png")));
        roadInfraStructurePanel.add(zebra_crossing);

        JButton road_blockages = new JButton();
        road_blockages.setBounds(108, 98, 70, 70);
        road_blockages.setToolTipText("Add Road Obstruction");
        road_blockages.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Construction.png")));
        roadInfraStructurePanel.add(road_blockages);

        JButton vehicle_factory = new JButton();
        vehicle_factory.setBounds(190, 98, 70, 70);
        vehicle_factory.setToolTipText("Vehicle Factory");
        vehicle_factory.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Vehicle Factory.png")));
        roadInfraStructurePanel.add(vehicle_factory);

        JLabel txtrTrafficSignBoards = new JLabel();
        txtrTrafficSignBoards.setBounds(64, 174, 158, 16);
        txtrTrafficSignBoards.setBackground(UIManager.getColor("Button.background"));
        txtrTrafficSignBoards.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
        txtrTrafficSignBoards.setText("TRAFFIC SIGN BOARDS");
        roadInfraStructurePanel.add(txtrTrafficSignBoards);

        JButton stop_sign = new JButton();
        stop_sign.setBounds(6, 192, 70, 70);
        stop_sign.setToolTipText("Add a Stop Sign");
        stop_sign.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Stop.png")));
        roadInfraStructurePanel.add(stop_sign);

        JButton left_sign = new JButton();
        left_sign.setBounds(72, 192, 70, 70);
        left_sign.setToolTipText("Add a Go Left Sign");
        left_sign.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Left.png")));
        roadInfraStructurePanel.add(left_sign);


        JButton up_sign = new JButton();
        up_sign.setBounds(141, 192, 70, 70);
        up_sign.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Straight.png")));
        roadInfraStructurePanel.add(up_sign);

        JButton right_sign = new JButton();
        right_sign.setBounds(208, 192, 70, 70);
        right_sign.setToolTipText("Add a Go Right Sign");
        right_sign.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Right.png")));
        roadInfraStructurePanel.add(right_sign);

        JLabel txtrSpeedLimitSigns = new JLabel();
        txtrSpeedLimitSigns.setBounds(82, 263, 130, 14);
        txtrSpeedLimitSigns.setForeground(UIManager.getColor("Button.darkShadow"));
        txtrSpeedLimitSigns.setText("SPEED LIMIT SIGNS");
        txtrSpeedLimitSigns.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
        txtrSpeedLimitSigns.setBackground(UIManager.getColor("Button.background"));
        roadInfraStructurePanel.add(txtrSpeedLimitSigns);

        JButton speed_20 = new JButton();
        speed_20.setBounds(1, 286, 50, 50);
        speed_20.setToolTipText("Speed Limit 20");
        speed_20.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/20.png")));
        roadInfraStructurePanel.add(speed_20);

        JButton speed_30 = new JButton();
        speed_30.setBounds(46, 286, 50, 50);
        speed_30.setToolTipText("Speed Limit 30");
        speed_30.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/30.png")));
        roadInfraStructurePanel.add(speed_30);

        JButton speed_50 = new JButton();
        speed_50.setBounds(93, 286, 50, 50);
        speed_50.setToolTipText("Speed Limit 50");
        speed_50.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/50.png")));
        roadInfraStructurePanel.add(speed_50);

        JButton speed_60 = new JButton();
        speed_60.setBounds(139, 286, 50, 50);
        speed_60.setToolTipText("Speed Limit 60");
        speed_60.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/60.png")));
        roadInfraStructurePanel.add(speed_60);

        JButton speed_70 = new JButton();
        speed_70.setBounds(186, 286, 50, 50);
        speed_70.setToolTipText("Speed Limit 70");
        speed_70.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/70.png")));
        roadInfraStructurePanel.add(speed_70);

        JButton speed_90 = new JButton();
        speed_90.setBounds(230, 286, 50, 50);
        speed_90.setToolTipText("Speed Limit 90");
        speed_90.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/90.png")));
        roadInfraStructurePanel.add(speed_90);

        JLabel txtrDestinationSignBoard = new JLabel();
        txtrDestinationSignBoard.setBounds(56, 340, 181, 14);
        txtrDestinationSignBoard.setText("DESTINATION SIGN BOARD");
        txtrDestinationSignBoard.setForeground(UIManager.getColor("Button.foreground"));
        txtrDestinationSignBoard.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
        txtrDestinationSignBoard.setBackground(UIManager.getColor("Button.background"));
        roadInfraStructurePanel.add(txtrDestinationSignBoard);

        JButton welcome_board = new JButton();
        welcome_board.setBounds(6, 355, 272, 57);
        welcome_board.setToolTipText("Add Welcome Board");
        welcome_board.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/Welcome Board.png")));
        roadInfraStructurePanel.add(welcome_board);

        // Simulation Configuration Panel

        JPanel simulationConfigPanel = new JPanel();
        simulationConfigPanel.setBackground(UIManager.getColor("InternalFrame.background"));
        simulationConfigPanel.setBounds(523, 6, 738, 71);
        trafficSimulatorFrame.getContentPane().add(simulationConfigPanel);
        simulationConfigPanel.setLayout(null);

        JLabel txtrSimulation = new JLabel();
        txtrSimulation.setBounds(345, 6, 146, 30);
        trafficSimulatorFrame.getContentPane().add(txtrSimulation);
        txtrSimulation.setText("SIMULATION");
        txtrRoadInfrastructure.setBackground(Color.BLACK);
        //txtrSimulation.setForeground(Color.WHITE);
        txtrSimulation.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
        txtrSimulation.setBackground(Color.GRAY);


        JLabel currentSecondLabel = new JLabel("Current Second : ");
        currentSecondLabel.setBounds(6, 6, 122, 24);


        final JLabel currentSecondValue = new JLabel("New label");
        currentSecondValue.setBounds(119, 6, 96, 24);

        JPanel currentSecondPanel = new JPanel();
        currentSecondPanel.setBounds(296, 41, 221, 36);
        trafficSimulatorFrame.getContentPane().add(currentSecondPanel);
        currentSecondPanel.setLayout(null);
        currentSecondPanel.add(currentSecondLabel);
        currentSecondPanel.add(currentSecondValue);


        final JButton playButton = new JButton();
        playButton.setToolTipText("Play");
        playButton.setBounds(6, 0, 70, 70);
        playButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/play.png")));
        simulationConfigPanel.add(playButton);

        final JButton pauseButton = new JButton();
        pauseButton.setToolTipText("Pause");
        pauseButton.setBounds(88, 0, 70, 70);
        pauseButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/pause.png")));
        pauseButton.setEnabled(false);
        simulationConfigPanel.add(pauseButton);

        final JButton stopButton = new JButton();
        stopButton.setToolTipText("Stop");
        stopButton.setBounds(170, 0, 70, 70);
        stopButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/stop_music.png")));
        simulationConfigPanel.add(stopButton);

        final JButton slowButton = new JButton();
        slowButton.setToolTipText("Slow");
        slowButton.setBounds(252, 0, 70, 70);
        slowButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/slow.png")));
        simulationConfigPanel.add(slowButton);
        slowButton.setEnabled(false);
        slowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simEngine.SlowDown();
            }
        });

        final JButton fastButton = new JButton();
        fastButton.setToolTipText("Fast");
        fastButton.setBounds(334, 0, 70, 70);
        fastButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/fast.png")));
        simulationConfigPanel.add(fastButton);
        fastButton.setEnabled(false);
        fastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simEngine.SpeedUp();
            }
        });

        final JButton uploadImageButton = new JButton();
        uploadImageButton.setToolTipText("Upload Road Image");
        uploadImageButton.setBounds(416, 0, 70, 70);
        uploadImageButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/upload.png")));
        simulationConfigPanel.add(uploadImageButton);


        final JButton reportButton = new JButton();
        reportButton.setToolTipText("Generate Report");
        reportButton.setBounds(659, 0, 70, 70);
        reportButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/report.png")));
        simulationConfigPanel.add(reportButton);
        reportButton.setEnabled(false);
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dcp.reportInformation();

            }
        });

        //Traffic Pattern Panel

        JPanel trafficPatternPanel = new JPanel();
        trafficPatternPanel.setBackground(UIManager.getColor("InternalFrame.background"));
        trafficPatternPanel.setBounds(6, 425, 497, 244);
        trafficSimulatorFrame.getContentPane().add(trafficPatternPanel);
        trafficPatternPanel.setLayout(null);
        trafficPatternPanel.setLayout(null);

        JLabel lblWeather = new JLabel("Weather");
        lblWeather.setBounds(6, 0, 85, 18);
        lblWeather.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        trafficPatternPanel.add(lblWeather);



        JLabel lblVisibility = new JLabel("VISIBILITY");
        lblVisibility.setBounds(248, 15, 85, 25);
        lblVisibility.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
        trafficPatternPanel.add(lblVisibility);

        final JSlider visibility_slider = new JSlider();
        visibility_slider.setBounds(337, 14, 165, 26);
        visibility_slider.setPaintTicks(true);
        visibility_slider.setMajorTickSpacing(10);
        visibility_slider.setMinimum(0);
        visibility_slider.setMaximum(100);
        visibility_slider.setValue((int) (climaticCondition.getVisibility() * 100.0));
        trafficPatternPanel.add(visibility_slider);
        visibility_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                climaticCondition.setVisibility(visibility_slider.getValue() / 100.0);
            }
        });

        final JSlider slippery_slider = new JSlider();
        slippery_slider.setBounds(337, 47, 165, 26);
        slippery_slider.setPaintTicks(true);
        slippery_slider.setPaintTicks(true);
        slippery_slider.setMajorTickSpacing(10);
        slippery_slider.setMinimum(0);
        slippery_slider.setMaximum(100);
        slippery_slider.setValue((int) (climaticCondition.getSlipperiness() * 100.0));
        trafficPatternPanel.add(slippery_slider);
        slippery_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                climaticCondition.setSlipperiness(slippery_slider.getValue()/100.0);
            }
        });

        JButton sunny_button = new JButton();
        sunny_button.setToolTipText("Sunny");
        sunny_button.setBounds(16, 17, 60, 60);
        sunny_button.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/sunny.png")));
        trafficPatternPanel.add(sunny_button);
        sunny_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                climaticCondition.setWeatherType(WeatherType.sunny);
                slippery_slider.setValue((int) (climaticCondition.getSlipperiness() * 100.0));
                visibility_slider.setValue((int) (climaticCondition.getVisibility() * 100.0));
            }
        });

        JButton rainy_button = new JButton();
        rainy_button.setToolTipText("Rainy");
        rainy_button.setBounds(90, 17, 60, 60);
        rainy_button.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/rainy.png")));
        trafficPatternPanel.add(rainy_button);
        rainy_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                climaticCondition.setWeatherType(WeatherType.rainy);
                slippery_slider.setValue((int) (climaticCondition.getSlipperiness() * 100.0));
                visibility_slider.setValue((int) (climaticCondition.getVisibility() * 100.0));
            }
        });

        JButton snow_button = new JButton();
        snow_button.setToolTipText("Snowy");
        snow_button.setBounds(162, 17, 60, 60);
        snow_button.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/snow.png")));
        trafficPatternPanel.add(snow_button);
        snow_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                climaticCondition.setWeatherType(WeatherType.snowy);
                slippery_slider.setValue((int) (climaticCondition.getSlipperiness() * 100.0));
                visibility_slider.setValue((int) (climaticCondition.getVisibility() * 100.0));
            }
        });


        final RangeSlider trafficDensityRangeSlider = new RangeSlider();
        trafficDensityRangeSlider.setPaintTicks(true);
        trafficDensityRangeSlider.setMajorTickSpacing(10);
        trafficDensityRangeSlider.setPreferredSize(new Dimension(240, trafficDensityRangeSlider.getPreferredSize().height));
        trafficDensityRangeSlider.setBounds(6, 99, 160, 26);
        trafficDensityRangeSlider.setMinimum(0);
        trafficDensityRangeSlider.setMaximum(100);
        trafficDensityRangeSlider.setValue((int) (globalConfigManager.getVehicleDensity().getCarDensity() * 100.0));
        trafficDensityRangeSlider.setUpperValue((int) ((globalConfigManager.getVehicleDensity().getCarDensity() + globalConfigManager.getVehicleDensity().getHeavyVehicleDensity() ) * 100.0));
        trafficPatternPanel.add(trafficDensityRangeSlider);
        trafficDensityRangeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                globalConfigManager.getVehicleDensity().setCarDensity((trafficDensityRangeSlider.getValue() - trafficDensityRangeSlider.getMinimum()) / 100.0);
                globalConfigManager.getVehicleDensity().setHeavyVehicleDensity((trafficDensityRangeSlider.getUpperValue() - trafficDensityRangeSlider.getValue()) / 100.0);
                globalConfigManager.getVehicleDensity().setEmergencyVehicleDensity((trafficDensityRangeSlider.getMaximum() - trafficDensityRangeSlider.getUpperValue()) / 100.0);
            }
        });


        final RangeSlider driverBehaviourRangeSlider = new RangeSlider();
        driverBehaviourRangeSlider.setPaintTicks(true);
        driverBehaviourRangeSlider.setMajorTickSpacing(10);
        driverBehaviourRangeSlider.setPreferredSize(new Dimension(240, trafficDensityRangeSlider.getPreferredSize().height));
        driverBehaviourRangeSlider.setBounds(6, 189, 160, 26);
        driverBehaviourRangeSlider.setMinimum(0);
        driverBehaviourRangeSlider.setMaximum(100);
        driverBehaviourRangeSlider.setValue((int) (globalConfigManager.getDriverBehaviour().getPercentageCautious() * 100.0));
        driverBehaviourRangeSlider.setUpperValue((int) ((globalConfigManager.getDriverBehaviour().getPercentageCautious() + (globalConfigManager.getDriverBehaviour().getPercentageNormal())) * 100.0));
        trafficPatternPanel.add(driverBehaviourRangeSlider);
        driverBehaviourRangeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                globalConfigManager.getDriverBehaviour().setPercentageCautious((driverBehaviourRangeSlider.getValue() - driverBehaviourRangeSlider.getMinimum()) / 100.0);
                globalConfigManager.getDriverBehaviour().setPercentageNormal((driverBehaviourRangeSlider.getUpperValue() - driverBehaviourRangeSlider.getValue()) / 100.0);
                globalConfigManager.getDriverBehaviour().setPercentageReckless((driverBehaviourRangeSlider.getMaximum() - driverBehaviourRangeSlider.getUpperValue()) / 100.0);

            }
        });

        JLabel lbl_slippery = new JLabel("SLIPPERINESS");
        lbl_slippery.setBounds(248, 47, 99, 30);
        lbl_slippery.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
        trafficPatternPanel.add(lbl_slippery);

        JLabel lbl_traffic_density = new JLabel("TRAFFIC DENSITY");
        lbl_traffic_density.setBounds(6, 82, 155, 18);
        lbl_traffic_density.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        trafficPatternPanel.add(lbl_traffic_density);

        final JSlider vehicleProductionRateSlider = new JSlider();
        vehicleProductionRateSlider.setPaintTicks(true);
        vehicleProductionRateSlider.setMinimum(0);
        vehicleProductionRateSlider.setMaximum(20);
        vehicleProductionRateSlider.setMajorTickSpacing(20);
        vehicleProductionRateSlider.setValue((int) (globalConfigManager.getVehicleDensity().getCreationRatePerSecond() * 10.0));
        vehicleProductionRateSlider.setBounds(165, 47, 155, 133);
        trafficPatternPanel.add(vehicleProductionRateSlider);
        vehicleProductionRateSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                globalConfigManager.getVehicleDensity().setCreationRatePerSecond((vehicleProductionRateSlider.getValue()/10.00));
            }
        });

        JLabel vehicleCreationRate = new JLabel("Rate");
        vehicleCreationRate.setForeground(SystemColor.controlHighlight);
        vehicleCreationRate.setFont(new Font("Times New Roman", Font.BOLD, 14));
        vehicleCreationRate.setBounds(225, 128, 36, 18);
        trafficPatternPanel.add(vehicleCreationRate);

        JLabel lblDriverBehavior = new JLabel("Driver Behavior");
        lblDriverBehavior.setBounds(6, 159, 173, 18);
        lblDriverBehavior.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        trafficPatternPanel.add(lblDriverBehavior);

        JLabel lblCar = new JLabel("Cars");
        lblCar.setForeground(UIManager.getColor("Button.light"));
        lblCar.setBounds(6, 129, 36, 18);
        lblCar.setFont(new Font("Copperplate Gothic", Font.BOLD, 11));
        trafficPatternPanel.add(lblCar);

        JLabel lblHeavyVehicles = new JLabel("Trucks");
        lblHeavyVehicles.setForeground(UIManager.getColor("Button.light"));
        lblHeavyVehicles.setBounds(51, 129, 99, 18);
        lblHeavyVehicles.setFont(new Font("Copperplate Gothic", Font.BOLD, 11));
        trafficPatternPanel.add(lblHeavyVehicles);

        JLabel lblEmergencyVehicles = new JLabel("Emergency");
        lblEmergencyVehicles.setForeground(UIManager.getColor("Button.light"));
        lblEmergencyVehicles.setBounds(103, 125, 126, 26);
        lblEmergencyVehicles.setFont(new Font("Copperplate Gothic", Font.BOLD, 11));
        trafficPatternPanel.add(lblEmergencyVehicles);

        JLabel lblCautions = new JLabel("Cautious");
        lblCautions.setForeground(UIManager.getColor("Button.light"));
        lblCautions.setBounds(6, 227, 56, 18);
        lblCautions.setFont(new Font("Copperplate Gothic", Font.BOLD, 11));
        trafficPatternPanel.add(lblCautions);

        JLabel lblNormal = new JLabel("Normal");
        lblNormal.setForeground(UIManager.getColor("Button.light"));
        lblNormal.setBounds(65, 227, 55, 18);
        lblNormal.setFont(new Font("Copperplate Gothic", Font.BOLD, 11));
        trafficPatternPanel.add(lblNormal);

        JLabel lblReckless = new JLabel("Reckless");
        lblReckless.setForeground(UIManager.getColor("Button.light"));
        lblReckless.setBounds(115, 227, 56, 18);
        lblReckless.setFont(new Font("Copperplate Gothic", Font.BOLD, 11));
        trafficPatternPanel.add(lblReckless);

        JLabel lblDestinationDensity = new JLabel("Destination Density");
        lblDestinationDensity.setBounds(183, 159, 189, 18);
        lblDestinationDensity.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        trafficPatternPanel.add(lblDestinationDensity);


        final JTextField destinationTxtBoxField = new JTextField(globalConfigManager.getRoute().getDestination());
        destinationTxtBoxField.setForeground(Color.LIGHT_GRAY);
        destinationTxtBoxField.setFont(new Font("Copperplate Gothic Bold", Font.ITALIC, 13));
        trafficPatternPanel.add(destinationTxtBoxField);
        destinationTxtBoxField.setBounds(183, 189, 145, 50);
        destinationTxtBoxField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                globalConfigManager.getRoute().setDestination(destinationTxtBoxField.getText());
            }
        });

        final JSlider destinationDensitySlider = new JSlider();
        destinationDensitySlider.setBounds(325, 143, 155, 133);
        destinationDensitySlider.setPaintTicks(true);
        destinationDensitySlider.setMajorTickSpacing(10);
        destinationDensitySlider.setMinimum(0);
        destinationDensitySlider.setMaximum(100);
        destinationDensitySlider.setValue((int) (globalConfigManager.getRoute().getTrafficPercent() * 100.0));
        trafficPatternPanel.add(destinationDensitySlider);
        destinationDensitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                globalConfigManager.getRoute().setTrafficPercent(destinationDensitySlider.getValue() / 100.0);
            }
        });


        //Traffic Light Configuration Panel

        final JPanel trafficLightConfigPanel = new JPanel();
        trafficLightConfigPanel.setBackground(UIManager.getColor("InternalFrame.background"));
        trafficLightConfigPanel.setBounds(505, 425, 756, 244);
        trafficSimulatorFrame.getContentPane().add(trafficLightConfigPanel);
        trafficLightConfigPanel.setLayout(null);

        JLabel trafficLightConfigLbl = new JLabel("Traffic Light Configurations");
        trafficLightConfigLbl.setBounds(18, 16, 257, 18);
        trafficLightConfigLbl.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        trafficLightConfigPanel.add(trafficLightConfigLbl);

        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 39, 744, 199);
        final JTable table = new JTable();
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(248, 248, 255));
        table.setCellSelectionEnabled(true);
        Object[][] data = {};
        String[] cols = {"TL ID", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9", "t10"};

        model = new DefaultTableModel(data, cols) {
            private static final long serialVersionUID = 1L;

            @SuppressWarnings("unchecked")
            public Class getColumnClass(int column) { //cells can be checked or unchecked
                return getValueAt(0, column).getClass();
            }
        };


        table.setModel(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                for (int i = 1; i <= (table.getColumnCount() - 1); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()); //call to the TableCellRenderer function to change the colour of the cell
                }
                scrollPane.validate();
                scrollPane.repaint();

            }
        });
        scrollPane.setViewportView(table);
        trafficLightConfigPanel.add(scrollPane);

        //Drawing Board Panel
        final DrawingBoard drawingBoard = new DrawingBoard(model, roadNetworkManager, simEngine,currentSecondValue);
        final JPanel drawingBoardPanel = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawingBoard.paintComponent(g);
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                drawingBoard.paint(g);
            }
        };
        drawingBoard.setDrawingBoardPanel(drawingBoardPanel);
        drawingBoardPanel.setBackground(Color.white);
        drawingBoardPanel.setBounds(286, 79, 1021, 348);
        drawingBoardPanel.setLayout(null);
        trafficSimulatorFrame.getContentPane().add(drawingBoardPanel);
        drawingBoard.initialize();//Initializing the drawing board

        //Adding action listeners for all configuration buttons

        single_lane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(true);
                drawingBoard.setPreviousRUnit(null);
                drawingBoard.setPreviousChangeableRunit(null);
                drawingBoard.addSingleLaneMouseDragMotionListener();
            }
        });

        double_lane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(true);
                drawingBoard.setPreviousRUnit(null);
                drawingBoard.setPreviousChangeableRunit(null);
                drawingBoard.addDoubleLaneMouseDragMotionListener();
            }
        });

        traffic_light.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addTrafficLightButtonListener();
            }
        });

        zebra_crossing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addZebraCrossingButtonListener();
            }
        });

        road_blockages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addBlockageButtonActionListener();
            }
        });

        vehicle_factory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addVehicleFactoryButtonActionListener();
            }
        });

        stop_sign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addStopButtonActionListener();
            }
        });

        left_sign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addLeftButtonActionListener();
            }
        });

        right_sign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addRightButtonActionListener();
            }
        });

        up_sign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addStraightButtonActionListener();
            }
        });

        speed_20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addSpeed20ButtonActionListener();
            }
        });


        speed_30.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addSpeed30ButtonActionListener();
            }
        });


        speed_50.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addSpeed50ButtonActionListener();
            }
        });

        speed_60.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addSpeed60ButtonActionListener();
            }
        });


        speed_70.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addSpeed70ButtonActionListener();
            }
        });

        welcome_board.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addWelcomeDestinationButtonActionListener();
            }
        });

        speed_90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                drawingBoard.addSpeed90ButtonActionListener();
            }
        });

        final JButton importConfigButton = new JButton();
        importConfigButton.setToolTipText("Import Configuration");
        importConfigButton.setBounds(498, 0, 70, 70);
        importConfigButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/import.png")));
        simulationConfigPanel.add(importConfigButton);
        importConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    FileInputStream fin = null;
                    try {
                        fin = new FileInputStream(fileChooser.getSelectedFile().getPath());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    ObjectInputStream ois = null;
                    try {
                        ois = new ObjectInputStream(fin);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        drawingBoard.clean();
                        ExportImportObject exportImportObject = (ExportImportObject) ois.readObject();
                        roadNetworkManager = (RoadNetworkManager) exportImportObject.getEngineDataStructures().getRoadNetworkManager();
                        vehicleFactoryManager = (VehicleFactoryManager) exportImportObject.getEngineDataStructures().getVehicleFactoryManager();
                        climaticCondition = exportImportObject.getEngineDataStructures().getGlobalConfigManager().getClimaticCondition();
                        globalConfigManager = (GlobalConfigManager) exportImportObject.getEngineDataStructures().getGlobalConfigManager();
                        dataAndStructures = exportImportObject.getEngineDataStructures();
                        dcp=new DCP(dataAndStructures);
                        simEngine = new SimEngine(dataAndStructures,dcp);
                        drawingBoard.setSingleLaneRUnits(exportImportObject.getUiDataStructures().getSingleLaneRUnits());
                        drawingBoard.setDoubleLaneRUnits(exportImportObject.getUiDataStructures().getDoubleLaneRUnits());
                        drawingBoard.setTrafficLightCoordinates(exportImportObject.getUiDataStructures().getTrafficLightCoordinates());
                        drawingBoard.setZebraCrossingCoordinates(exportImportObject.getUiDataStructures().getZebraCrossingCoordinates());
                        drawingBoard.setStopCoordinates(exportImportObject.getUiDataStructures().getStopCoordinates());
                        drawingBoard.setBlockageCoordinates(exportImportObject.getUiDataStructures().getBlockageCoordinates());
                        drawingBoard.setVehicleFactoryCoordinates(exportImportObject.getUiDataStructures().getVehicleFactoryCoordinates());
                        drawingBoard.setLeftCoordinates(exportImportObject.getUiDataStructures().getLeftCoordinates());
                        drawingBoard.setRightCoordinates(exportImportObject.getUiDataStructures().getRightCoordinates());
                        drawingBoard.setStraightCoordinates(exportImportObject.getUiDataStructures().getStraightCoordinates());
                        drawingBoard.setSpeed20Coordinates(exportImportObject.getUiDataStructures().getSpeed20Coordinates());
                        drawingBoard.setSpeed30Coordinates(exportImportObject.getUiDataStructures().getSpeed30Coordinates());
                        drawingBoard.setSpeed50Coordinates(exportImportObject.getUiDataStructures().getSpeed50Coordinates());
                        drawingBoard.setSpeed60Coordinates(exportImportObject.getUiDataStructures().getSpeed60Coordinates());
                        drawingBoard.setSpeed70Coordinates(exportImportObject.getUiDataStructures().getSpeed70Coordinates());
                        drawingBoard.setSpeed90Coordinates(exportImportObject.getUiDataStructures().getSpeed90Coordinates());
                        drawingBoard.setWelcomeCoordinates(exportImportObject.getUiDataStructures().getWelcomeCoordinates());
                        drawingBoard.setSimEngine(simEngine);
                        drawingBoard.setRoadNetworkManager(roadNetworkManager);
                        driverBehaviourRangeSlider.setValue((int) (globalConfigManager.getDriverBehaviour().getPercentageCautious() * 100.0));
                        driverBehaviourRangeSlider.setUpperValue((int) ((globalConfigManager.getDriverBehaviour().getPercentageCautious() + (globalConfigManager.getDriverBehaviour().getPercentageNormal())) * 100.0));
                        trafficDensityRangeSlider.setValue((int) (globalConfigManager.getVehicleDensity().getCarDensity() * 100.0));
                        trafficDensityRangeSlider.setUpperValue((int) ((globalConfigManager.getVehicleDensity().getCarDensity() + globalConfigManager.getVehicleDensity().getHeavyVehicleDensity() ) * 100.0));
                        slippery_slider.setValue((int) (climaticCondition.getSlipperiness() * 100.0));
                        visibility_slider.setValue((int) (climaticCondition.getVisibility() * 100.0));
                        destinationDensitySlider.setValue((int) (globalConfigManager.getRoute().getTrafficPercent() * 100.0));
                        destinationTxtBoxField.setText(globalConfigManager.getRoute().getDestination());
                        populateTrafficLightTableModel();
                        drawingBoard.setModel(model);
                        drawingBoard.getDrawingBoardPanel().repaint();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        final JButton exportConfigButton = new JButton();
        exportConfigButton.setToolTipText("Export Configuration");
        exportConfigButton.setBounds(580, 0, 70, 70);
        exportConfigButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/export.png")));
        simulationConfigPanel.add(exportConfigButton);
        exportConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fDialog = new FileDialog(trafficSimulatorFrame, "Save", FileDialog.SAVE);
                fDialog.setVisible(true);
                String path = fDialog.getDirectory() + fDialog.getFile();
                ExportImportObject exportImportObject = new ExportImportObject(dataAndStructures,drawingBoard);
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(path);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                ObjectOutputStream oos;
                try {
                    oos = new ObjectOutputStream(fout);
                    oos.writeObject(exportImportObject);
                    oos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        final JButton clearVehicleFactoryButton = new JButton();
        clearVehicleFactoryButton.setToolTipText("Clear Vehicle Factory");
        clearVehicleFactoryButton.setIcon(new ImageIcon(Traffic_Simulator.class.getResource("/resources/clearVehicleFactory.png")));
        clearVehicleFactoryButton.setBounds(325, 85, 65, 65);
        trafficPatternPanel.add(clearVehicleFactoryButton);
        clearVehicleFactoryButton.setEnabled(false);
        clearVehicleFactoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setSimulationPlaying(false);
                simEngine.CleanVehicles();
                drawingBoardPanel.repaint();
            }
        });


        //Start simulation
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setDraw(false);
                // Remove all listeners
                for(MouseListener mouseListener: drawingBoardPanel.getMouseListeners()){
                    drawingBoardPanel.removeMouseListener(mouseListener);
                }
                for(MouseMotionListener mouseListener: drawingBoardPanel.getMouseMotionListeners()){
                    drawingBoardPanel.removeMouseMotionListener(mouseListener);
                }
                if(drawingBoard.isSimulationStarted()){
                    simEngine.Unpause();
                    drawingBoard.setSimulationPlaying(true);
                    simEngine.Play(drawingBoard);
                    playButton.setEnabled(false);
                    pauseButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    slowButton.setEnabled(true);
                    fastButton.setEnabled(true);
                    uploadImageButton.setEnabled(false);
                    importConfigButton.setEnabled(false);
                    exportConfigButton.setEnabled(false);
                    reportButton.setEnabled(false);
                    clearVehicleFactoryButton.setEnabled(false);
                }else {
                    if (vehicleFactoryManager.vehicleFactoryList.isEmpty()) {
                        NoVehicleFactoryDialogBox vehicleFactoryDialogBox = new NoVehicleFactoryDialogBox();
                        vehicleFactoryDialogBox.vehicleFactoryDialog();
                        vehicleFactoryDialogBox.setVisible(true);
                    } else{
                        simEngine.Play(drawingBoard);
                        drawingBoard.setSimulationStarted(true);
                        drawingBoard.setSimulationPlaying(true);
                        playButton.setEnabled(false);
                        pauseButton.setEnabled(true);
                        stopButton.setEnabled(true);
                        slowButton.setEnabled(true);
                        fastButton.setEnabled(true);
                        uploadImageButton.setEnabled(false);
                        importConfigButton.setEnabled(false);
                        exportConfigButton.setEnabled(false);
                        reportButton.setEnabled(false);
                        clearVehicleFactoryButton.setEnabled(false);
                    }
                }
            }
        });



        //Pause Simulation
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(drawingBoard.isSimulationPlaying()){
                    simEngine.Pause();
                    drawingBoard.setSimulationPlaying(false);
                    playButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    stopButton.setEnabled(true);
                    slowButton.setEnabled(false);
                    fastButton.setEnabled(false);
                    uploadImageButton.setEnabled(false);
                    importConfigButton.setEnabled(false);
                    exportConfigButton.setEnabled(true);
                    reportButton.setEnabled(true);
                    clearVehicleFactoryButton.setEnabled(true);
                }
            }
        });

        //Stop Simulation
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.trafficSimulatorFrame.setVisible(false);
                window = new Traffic_Simulator();
                window.trafficSimulatorFrame.setVisible(true);
            }
        });

        //Upload road map image

        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               File file;
                BufferedImage originalimage;
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        file = fileChooser.getSelectedFile();
                        originalimage = ImageIO.read(file);
                        Image scaledImage = originalimage.getScaledInstance(drawingBoardPanel.getWidth(),drawingBoardPanel.getHeight(),Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(scaledImage);
                        JLabel picLabel = new JLabel(icon);
                        drawingBoardPanel.setLayout(new GridLayout());
                        drawingBoardPanel.add(picLabel);
                        drawingBoardPanel.revalidate();
                        drawingBoardPanel.repaint();


                    } catch (IOException e1) {
                        e1.printStackTrace();

                    }
                }

            }
        });
    }

    private void populateTrafficLightTableModel(){
        if(model != null){
            for(TrafficLight trafficLight : roadNetworkManager.getRoadNetwork().getTrafficLightHashtable().values()){
                model.addRow(new Object[]{trafficLight.getTrafficLightID(), trafficLight.getCycle().get(0), trafficLight.getCycle().get(1),
                        trafficLight.getCycle().get(2), trafficLight.getCycle().get(3), trafficLight.getCycle().get(4), trafficLight.getCycle().get(5),
                        trafficLight.getCycle().get(6), trafficLight.getCycle().get(7), trafficLight.getCycle().get(8), trafficLight.getCycle().get(9)});
            }
        }
    }

    @SuppressWarnings("serial")
    private static class TableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (table.getValueAt(row, column).equals(true)) {
                comp.setBackground(Color.GREEN);
            } else
                comp.setBackground(Color.RED);
            return comp;
        }
    }
}
