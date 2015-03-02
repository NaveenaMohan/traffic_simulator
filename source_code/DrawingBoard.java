import engine.SimEngine;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.DirectionSignType;
import managers.runit.RUnit;
import managers.runit.TrafficLight;
import managers.space.ObjectInSpace;
import ui.ConfigButtonSelected;
import ui.Coordinates;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingBoard extends JPanel implements ActionListener {
    private static int trafficLightIdIndex = 1;
    private static int zebraCrossingTrafficLightIdIndex = 1;
    BufferedImage bi;
    private int currentX, currentY;
    private List<Coordinates> singleLaneCoordinates = new ArrayList<Coordinates>();
    private Map<String, Coordinates> trafficLightCoordinates = new HashMap<String, Coordinates>();
    private Map<String, Coordinates> zebraCrossingCoordinates = new HashMap<String, Coordinates>();
    private List<Coordinates> blockageCoordinates = new ArrayList<Coordinates>();
    private List<Coordinates> stopCoordinates = new ArrayList<Coordinates>();
    private Map<Coordinates, String> leftCoordinates = new HashMap<Coordinates, String>();
    private Map<Coordinates, String> straightCoordinates = new HashMap<Coordinates, String>();
    private Map<Coordinates, String> rightCoordinates = new HashMap<Coordinates, String>();
    private List<Coordinates> speed20Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed30Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed50Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed60Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed70Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed90Coordinates = new ArrayList<Coordinates>();
    private Map<Coordinates, String> welcomeCoordinates = new HashMap<Coordinates, String>();
    private RoadNetworkManager roadNetworkManager;
    private SimEngine simEngine;
    private RUnit previousRUnit;
    private Image rUnitImage;
    private Image trafficLightImage;
    private Image carImage;
    private Image zebraCrossingImage;
    private Image blockageImage;
    private Image stopImage;
    private Image leftSignImage;
    private Image rightSignImage;
    private Image straightSignImage;
    private Image speed20Image;
    private Image speed30Image;
    private Image speed50Image;
    private Image speed60Image;
    private Image speed70Image;
    private Image speed90Image;
    private Image welcomeImage;
    private Image redLightImage;
    private Image greenLightImage;
    private ConfigButtonSelected configButtonSelected = ConfigButtonSelected.noOption;
    private boolean mousePressed;
    private DefaultTableModel model;
    private boolean isSimulationPlaying;
    private JLabel currentSecondValue;

    public DrawingBoard(DefaultTableModel model, RoadNetworkManager roadNetworkManager, SimEngine simEngine, JLabel currentSecondValue) {
        this.model = model;
        this.roadNetworkManager = roadNetworkManager;
        this.simEngine = simEngine;
        this.currentSecondValue = currentSecondValue;
        setBackground(Color.white);
        setBounds(286, 79, 1021, 348);
        setLayout(null);
    }

    public void initialize() {

        //TODO: Get the correct images

        rUnitImage = getToolkit().getImage(DrawingBoard.class.getResource("road.jpg"));
        trafficLightImage = getToolkit().getImage(DrawingBoard.class.getResource("lightMini.png"));
        carImage = getToolkit().getImage(DrawingBoard.class.getResource("car2.png"));
        zebraCrossingImage = getToolkit().getImage(DrawingBoard.class.getResource("zebraCrossingMini.png"));
        blockageImage = getToolkit().getImage(DrawingBoard.class.getResource("blockMini.png"));
        stopImage = getToolkit().getImage(DrawingBoard.class.getResource("stopMini.png"));
        greenLightImage = getToolkit().getImage(DrawingBoard.class.getResource("green.png"));
        redLightImage = getToolkit().getImage(DrawingBoard.class.getResource("red.png"));

        leftSignImage = getToolkit().getImage(DrawingBoard.class.getResource("leftMini.png"));
        rightSignImage = getToolkit().getImage(DrawingBoard.class.getResource("rightMini.png"));
        straightSignImage = getToolkit().getImage(DrawingBoard.class.getResource("straightMini.png"));
        speed20Image = getToolkit().getImage(DrawingBoard.class.getResource("20mini.png"));
        speed30Image = getToolkit().getImage(DrawingBoard.class.getResource("30mini.png"));
        speed50Image = getToolkit().getImage(DrawingBoard.class.getResource("50mini.png"));
        speed60Image = getToolkit().getImage(DrawingBoard.class.getResource("60mini.png"));
        speed70Image = getToolkit().getImage(DrawingBoard.class.getResource("70mini.png"));
        speed90Image = getToolkit().getImage(DrawingBoard.class.getResource("90mini.png"));
        welcomeImage = getToolkit().getImage(DrawingBoard.class.getResource("welcomeMini.png"));

        MediaTracker mt = new MediaTracker(this);
        mt.addImage(rUnitImage, 1);
        try {
            mt.waitForAll();
        } catch (Exception e) {
            System.out.println("Exception while loading RUnitImage");
        }
        bi = new BufferedImage(rUnitImage.getWidth(this), rUnitImage.getHeight(this),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D big = bi.createGraphics();
        big.drawImage(rUnitImage, 0, 0, this);
    }

    //Configuration Phase - Drawing road and configuring other traffic elements
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mousePressed && configButtonSelected.equals(ConfigButtonSelected.addSingleLane)) {
            Graphics2D g2D = (Graphics2D) g;
            Coordinates coordinates = new Coordinates(currentX, currentY);
            if (!singleLaneCoordinates.contains(coordinates)) {
                //Add and Return RUnit for single lane and store it as previous RUnit TODO : Change storage of previous RUnit
                singleLaneCoordinates.add(coordinates);
                previousRUnit = roadNetworkManager.addSingleLane(currentX, currentY, previousRUnit);
            }
            g2D.drawImage(bi, currentX, currentY, this);
        }

        if (configButtonSelected.equals(ConfigButtonSelected.trafficLight)) {
            String trafficLightId = "TL-" + trafficLightIdIndex;
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the traffic Light
                //TODO : remove the hard coding of traffic light and its cycle
                TrafficLight trafficLight = new TrafficLight();
//                ArrayList<Boolean> cycle = new ArrayList<Boolean>(Arrays.asList(true, false, true, false, true, true, true, true, true, true));
//                trafficLight.setCycle(cycle);
                trafficLight.setTrafficLightID(trafficLightId);
                simEngine.getDataAndStructures().getRoadNetworkManager().addTrafficLight(bestMatchRUnit, trafficLight);
                //Drawing the traffic Light and adding the traffic light cycle configuration
                g.drawImage(trafficLightImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                if (model != null) {
                    model.addRow(new Object[]{trafficLightId, false, false, false, false, false, false, false, false, false, false});
                    trafficLightCoordinates.put(trafficLightId, new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()));
                    trafficLightIdIndex++;
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.zebraCrossing)) {
            String trafficLightId = "ZTL-" + zebraCrossingTrafficLightIdIndex;
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the zebra crossing
                //TODO : remove the hard coding of traffic light and its cycle
                TrafficLight trafficLight = new TrafficLight();
//                ArrayList<Boolean> cycle = new ArrayList<Boolean>(Arrays.asList(true, false, true, false, true, true, true, true, true, true));
//                trafficLight.setCycle(cycle);
                trafficLight.setTrafficLightID(trafficLightId);
                simEngine.getDataAndStructures().getRoadNetworkManager().addZebraCrossing(bestMatchRUnit, trafficLight);
                //Drawing the zebra crossing with traffic light configuration
                g.drawImage(zebraCrossingImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                if (model != null) {
                    model.addRow(new Object[]{trafficLightId, false, false, false, false, false, false, false, false, false, false});
                    zebraCrossingCoordinates.put(trafficLightId, new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()));
                    zebraCrossingTrafficLightIdIndex++;
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.blockage)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(blockageCoordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addBlockage(bestMatchRUnit);
                //Drawing the blockage
                g.drawImage(blockageImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }


        if (configButtonSelected.equals(ConfigButtonSelected.vehicleFactory)) {
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Adding vehicle factory and dataStructures
                simEngine.getDataAndStructures().getVehicleFactoryManager().addVehicleFactory(roadNetworkManager.getRoadNetwork().getrUnitHashtable().get(String.valueOf(bestMatchRUnit.getId())));
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }


        if (configButtonSelected.equals(ConfigButtonSelected.stop)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(stopCoordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addStopSign(bestMatchRUnit);
                //Drawing the blockage
                g.drawImage(stopImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.left)) {
            LocationDialog locationDialog = new LocationDialog();
            locationDialog.initUI();
            locationDialog.setVisible(true);
            String destination = locationDialog.getDestination();
            if (destination != null) {
                RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(leftCoordinates, destination);
                if (bestMatchRUnit == null) {
                    System.out.println("No matching Runit"); //TODO : Dialog Box
                } else {
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.left);
                    //Drawing the blockage
                    g.drawImage(leftSignImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.right)) {
            LocationDialog locationDialog = new LocationDialog();
            locationDialog.initUI();
            locationDialog.setVisible(true);
            String destination = locationDialog.getDestination();
            if (destination != null) {
                RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(rightCoordinates, destination);
                if (bestMatchRUnit == null) {
                    System.out.println("No matching Runit"); //TODO : Dialog Box
                } else {
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.right);
                    //Drawing the blockage
                    g.drawImage(rightSignImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.straight)) {
            LocationDialog locationDialog = new LocationDialog();
            locationDialog.initUI();
            locationDialog.setVisible(true);
            String destination = locationDialog.getDestination();
            if (destination != null) {
                RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(straightCoordinates, destination);
                if (bestMatchRUnit == null) {
                    System.out.println("No matching Runit"); //TODO : Dialog Box
                } else {
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.straight);
                    //Drawing the blockage
                    g.drawImage(straightSignImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed20)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed20Coordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 20);
                //Drawing the blockage
                g.drawImage(speed20Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed30)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed30Coordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 30);
                //Drawing the blockage
                g.drawImage(speed30Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed50)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed50Coordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 50);
                //Drawing the blockage
                g.drawImage(speed50Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed60)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed60Coordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 60);
                //Drawing the blockage
                g.drawImage(speed60Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed70)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed70Coordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 70);
                //Drawing the blockage
                g.drawImage(speed70Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed90)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed90Coordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 90);
                //Drawing the blockage
                g.drawImage(speed90Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.welcome)) {
            DestinationDialog destinationDialog = new DestinationDialog();
            destinationDialog.initUI();
            destinationDialog.setVisible(true);
            String destination = destinationDialog.getDestination();
            if (destination != null) {
                RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(welcomeCoordinates, destination);
                if (bestMatchRUnit == null) {
                    System.out.println("No matching Runit"); //TODO : Dialog Box
                } else {
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addWelcomeSign(bestMatchRUnit, destination);
                    //Drawing the blockage
                    g.drawImage(welcomeImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (model != null) {
            for (int i = 0; i < model.getRowCount(); i++) {
                List<Boolean> cellValues = new ArrayList<Boolean>();
                String tlId = String.valueOf(model.getValueAt(i, 0));
                for (int j = 1; j < model.getColumnCount(); j++) {
                    cellValues.add(Boolean.valueOf(String.valueOf(model.getValueAt(i, j))));
                }
                roadNetworkManager.addTrafficLightBehavior(tlId, cellValues);
            }
        }
    }

    private RUnit fetchAndAddBestMatchRUnit(List<Coordinates> coordinates) {
        for (RUnit rUnit : simEngine.getDataAndStructures().getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), rUnitImage.getWidth(this), rUnitImage.getHeight(this));
            if (rectangle.contains(currentX, currentY)) {
                if (coordinates != null) {
                    coordinates.add(new Coordinates(rUnit.getX(), rUnit.getY()));
                }
                return rUnit;
            }
        }
        return null;
    }

    private RUnit fetchAndAddBestMatchRUnit(Map<Coordinates, String> coordinates, String destination) {
        for (RUnit rUnit : simEngine.getDataAndStructures().getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), rUnitImage.getWidth(this), rUnitImage.getHeight(this));
            if (rectangle.contains(currentX, currentY)) {
                if (coordinates != null) {
                    coordinates.put(new Coordinates(rUnit.getX(), rUnit.getY()), destination);
                }
                return rUnit;
            }
        }
        return null;
    }

    private RUnit fetchBestMatchRUnit() {
        for (RUnit rUnit : simEngine.getDataAndStructures().getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), rUnitImage.getWidth(this), rUnitImage.getHeight(this));
            if (rectangle.contains(currentX, currentY)) {
                return rUnit;
            }
        }
        return null;
    }

    //Simulation Phase - Displaying roads and other traffic elements
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        //Drawing the road
        for (Coordinates coordinate : singleLaneCoordinates) {
            g2D.drawImage(rUnitImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing traffic lights
        for (Coordinates coordinate : trafficLightCoordinates.values()) {
            g2D.drawImage(trafficLightImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing zebra crossing
        for (Coordinates coordinate : zebraCrossingCoordinates.values()) {
            g2D.drawImage(zebraCrossingImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing blockages
        for (Coordinates coordinate : blockageCoordinates) {
            g2D.drawImage(blockageImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing stop signs
        for (Coordinates coordinate : stopCoordinates) {
            g2D.drawImage(stopImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing left signs
        for (Coordinates coordinate : leftCoordinates.keySet()) {
            g2D.drawImage(leftSignImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing right signs
        for (Coordinates coordinate : rightCoordinates.keySet()) {
            g2D.drawImage(rightSignImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing straight signs
        for (Coordinates coordinate : straightCoordinates.keySet()) {
            g2D.drawImage(straightSignImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing speed20 signs
        for (Coordinates coordinate : speed20Coordinates) {
            g2D.drawImage(speed20Image, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing speed30 signs
        for (Coordinates coordinate : speed30Coordinates) {
            g2D.drawImage(speed30Image, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing speed50 signs
        for (Coordinates coordinate : speed50Coordinates) {
            g2D.drawImage(speed50Image, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing speed60 signs
        for (Coordinates coordinate : speed60Coordinates) {
            g2D.drawImage(speed60Image, coordinate.getX(), coordinate.getY(), this);
        }
        //Drawing speed70 signs
        for (Coordinates coordinate : speed70Coordinates) {
            g2D.drawImage(speed70Image, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing speed90 signs
        for (Coordinates coordinate : speed90Coordinates) {
            g2D.drawImage(speed90Image, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing welcome signs
        for (Coordinates coordinate : welcomeCoordinates.keySet()) {
            g2D.drawImage(welcomeImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Move Vehicles on RUnits on UI

        Graphics2D g2d = (Graphics2D) g;
        for (ObjectInSpace objectInSpace : simEngine.getDataAndStructures().getSpaceManager().getObjects()) {
            g2d.drawImage(carImage, objectInSpace.getX(), objectInSpace.getY(), this);
        }

        //Change colour of traffic lights on the UI
        if (isSimulationPlaying) {
            for (String trafficLightId : roadNetworkManager.getRoadNetwork().getTrafficLightHashtable().keySet()) {
                TrafficLight trafficLight = roadNetworkManager.getRoadNetwork().getTrafficLightHashtable().get(trafficLightId);
                if (trafficLight.getTrafficLightCurrentColor()) {
                    if (trafficLightCoordinates.get(trafficLightId) != null) {
                        g2d.drawImage(greenLightImage, trafficLightCoordinates.get(trafficLightId).getX(), trafficLightCoordinates.get(trafficLightId).getY(), this);
                    } else {
                        g2d.drawImage(greenLightImage, zebraCrossingCoordinates.get(trafficLightId).getX(), zebraCrossingCoordinates.get(trafficLightId).getY(), this);
                    }
                } else {
                    if (trafficLightCoordinates.get(trafficLightId) != null) {
                        g2d.drawImage(redLightImage, trafficLightCoordinates.get(trafficLightId).getX(), trafficLightCoordinates.get(trafficLightId).getY(), this);
                    } else {
                        g2d.drawImage(redLightImage, zebraCrossingCoordinates.get(trafficLightId).getX(), zebraCrossingCoordinates.get(trafficLightId).getY(), this);
                    }
                }
            }
        }
        currentSecondValue.setText(String.valueOf(simEngine.getDataAndStructures().getGlobalConfigManager().getCurrentSecond()));
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simEngine.performAction();
        repaint();
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }


    @Override
    public int getX() {
        return currentX;
    }

    @Override
    public int getY() {
        return currentY;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void addMouseDragMotionListener() {
        addMouseMotionListener(new MouseDragMotionListener(this));
        addMouseListener(new SingleLaneMotionListener(this));
    }

    public void addZebraCrossingButtonListener() {
        addMouseListener(new ZebraCrossingButtonListener(this));
    }

    public void addTrafficLightButtonListener() {
        addMouseListener(new TrafficLightButtonListener(this));
    }

    public ConfigButtonSelected getConfigButtonSelected() {
        return configButtonSelected;
    }

    public void setConfigButtonSelected(ConfigButtonSelected configButtonSelected) {
        this.configButtonSelected = configButtonSelected;
    }

    public boolean isSimulationPlaying() {
        return isSimulationPlaying;
    }

    public void setSimulationPlaying(boolean isSimulationPlaying) {
        this.isSimulationPlaying = isSimulationPlaying;
    }

    public void addBlockageButtonActionListener() {
        addMouseListener(new BlockageButtonListener(this));
    }

    public void addVehicleFactoryButtonActionListener() {
        addMouseListener(new VehicleFactoryButtonListener(this));
    }

    public void addStopButtonActionListener() {
        addMouseListener(new StopButtonListener(this));
    }

    public void addLeftButtonActionListener() {
        addMouseListener(new LeftButtonListener(this));
    }

    public void addRightButtonActionListener() {
        addMouseListener(new RightButtonListener(this));
    }

    public void addStraightButtonActionListener() {
        addMouseListener(new StraightButtonListener(this));
    }

    public void addSpeed20ButtonActionListener() {
        addMouseListener(new Speed20ButtonListener(this));
    }

    public void addSpeed30ButtonActionListener() {
        addMouseListener(new Speed30ButtonListener(this));
    }

    public void addSpeed50ButtonActionListener() {
        addMouseListener(new Speed50ButtonListener(this));
    }

    public void addSpeed60ButtonActionListener() {
        addMouseListener(new Speed60ButtonListener(this));
    }

    public void addSpeed70ButtonActionListener() {
        addMouseListener(new Speed70ButtonListener(this));
    }


    public void addSpeed90ButtonActionListener() {
        addMouseListener(new Speed90ButtonListener(this));
    }

    public void addWelcomeDestinationButtonActionListener() {
        addMouseListener(new WelcomeDestinationButtonListener(this));
    }
}