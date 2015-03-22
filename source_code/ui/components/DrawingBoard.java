package ui.components;

import common.Common;
import engine.SimEngine;
import managers.globalconfig.VehicleType;
import managers.roadnetwork.IRoadNetworkManager;
import managers.runit.DirectionSignType;
import managers.runit.IRUnitManager;
import managers.runit.TrafficLight;
import managers.space.ObjectInSpace;
import ui.ConfigButtonSelected;
import ui.Coordinates;
import ui.listeners.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class DrawingBoard implements ActionListener {
    private static int trafficLightIdIndex = 1;
    private static int zebraCrossingTrafficLightIdIndex = 1;
    private BufferedImage bufferedRoadImage;
    private BufferedImage bufferedChangeableRoadImage;
    private int currentX, currentY;
    private Set<IRUnitManager> singleLaneRUnits = new LinkedHashSet<IRUnitManager>();
    private Set<IRUnitManager> doubleLaneRUnits = new LinkedHashSet<IRUnitManager>();
    private Set<IRUnitManager> changeAbleLaneRUnits = new LinkedHashSet<IRUnitManager>();
    private List<Coordinates> vehicleFactoryCoordinates = new ArrayList<Coordinates>();
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
    private IRoadNetworkManager roadNetworkManager;
    private SimEngine simEngine;
    private IRUnitManager previousRUnit;
    private IRUnitManager previousChangeableRunit;
    private Image rUnitImage;
    private Image rUnitImage2;
    private Image trafficLightImage;
    private Image carImage;
    private Image truckImage;
    private Image emergencyVehicleImage;
    private Image zebraCrossingImage;
    private Image greenLightZebraImage;
    private Image redLightZebraImage;
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
    private Image vehicleFactoryImage;
    private ConfigButtonSelected configButtonSelected = ConfigButtonSelected.noOption;
    private boolean isDraw;
    private DefaultTableModel model;
    private boolean isSimulationPlaying;
    private boolean isSimulationStarted;
    private JLabel currentSecondValue;
    private JPanel drawingBoardPanel;

    public DrawingBoard(DefaultTableModel model, IRoadNetworkManager roadNetworkManager, SimEngine simEngine, JLabel currentSecondValue) {
        this.model = model;
        this.roadNetworkManager = roadNetworkManager;
        this.simEngine = simEngine;
        this.currentSecondValue = currentSecondValue;
    }

    public void initialize() {
        //Loading all images for various UI components
        rUnitImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/leftLane.png"));
        rUnitImage2 = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/rightLane.png"));
        trafficLightImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/lightMini.png"));
        carImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/car.png"));
        truckImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/truck.png"));
        emergencyVehicleImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/emergency.gif"));
        zebraCrossingImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/zebraCrossingMini.png"));
        blockageImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/blockMini.png"));
        stopImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/stopMini.png"));
        greenLightImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/green.png"));
        redLightImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/red.png"));
        greenLightZebraImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/greenZebra.png"));
        redLightZebraImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/redZebra.png"));
        vehicleFactoryImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/vehicleFactoryMini.png"));
        leftSignImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/leftMini.png"));
        rightSignImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/rightMini.png"));
        straightSignImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/straightMini.png"));
        speed20Image = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/20mini.png"));
        speed30Image = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/30mini.png"));
        speed50Image = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/50mini.png"));
        speed60Image = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/60mini.png"));
        speed70Image = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/70mini.png"));
        speed90Image = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/90mini.png"));
        welcomeImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/welcomeMini.png"));

        //Loading and populating BufferedImages for the Roads to dyanmically draw and provision them in the UI and backend
        MediaTracker roadMediaTracker = new MediaTracker(drawingBoardPanel);
        roadMediaTracker.addImage(rUnitImage, 1);
        try {
            roadMediaTracker.waitForAll();
        } catch (Exception e) {
            System.out.println("Exception while loading RUnitImage");
        }
        bufferedRoadImage = new BufferedImage(rUnitImage.getWidth(drawingBoardPanel), rUnitImage.getHeight(drawingBoardPanel),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D roadGraphics = bufferedRoadImage.createGraphics();
        roadGraphics.drawImage(rUnitImage, 0, 0, drawingBoardPanel);

        MediaTracker changeableRoadMediaTracker = new MediaTracker(drawingBoardPanel);
        changeableRoadMediaTracker.addImage(rUnitImage2, 1);
        try {
            changeableRoadMediaTracker.waitForAll();
        } catch (Exception e) {
            System.out.println("Exception while loading DoubleRoadImage");
        }
        bufferedChangeableRoadImage = new BufferedImage(rUnitImage2.getWidth(drawingBoardPanel), rUnitImage2.getHeight(drawingBoardPanel),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D changeableRoadGraphics = bufferedChangeableRoadImage.createGraphics();
        changeableRoadGraphics.drawImage(rUnitImage2, 0, 0, drawingBoardPanel);

    }

    //Configuration Phase - Drawing road and adding other traffic elements
    //This function paints/draws the component when the component is drawn or added initially
    public void paintComponent(Graphics g) {

        //Add single lane
        if (isDraw() && configButtonSelected.equals(ConfigButtonSelected.addSingleLane)) {
            Graphics2D g2D = (Graphics2D) g;

            Coordinates prevOrCurCoordinates = new Coordinates((previousRUnit == null ? currentX : previousRUnit.getX()),
                    (previousRUnit == null ? currentY : previousRUnit.getY()));
            Coordinates currentCoordinates = new Coordinates(currentX, currentY);
            //Smart Drawing feature to enable auto completion of smooth roads when clicked at 2 points in the drawing panel.
            do {
                if (previousRUnit == null || !prevOrCurCoordinates.equals(new Coordinates(previousRUnit.getX(), previousRUnit.getY()))) {
                    //Add and Return RUnit for single lane and store it as previous RUnit
                    previousRUnit = roadNetworkManager.addSingleLane(prevOrCurCoordinates.getX(), prevOrCurCoordinates.getY(), previousRUnit);
                    singleLaneRUnits.add(previousRUnit);
                    //Drawing the road with repositioned coordinates
                    Coordinates coordinates = getRepositionedImageCoordinates(bufferedRoadImage, currentX, currentY);
                    g2D.drawImage(bufferedRoadImage, coordinates.getX(), coordinates.getY(), drawingBoardPanel);
                }
                prevOrCurCoordinates = new Coordinates(Common.getNextPointFromTo(prevOrCurCoordinates, currentCoordinates).getX(), Common.getNextPointFromTo(prevOrCurCoordinates, currentCoordinates).getY());
            }
            while (prevOrCurCoordinates.getY() != currentCoordinates.getY() & prevOrCurCoordinates.getX() != currentCoordinates.getX());
        }

        //Add double lane

        if (isDraw() && configButtonSelected.equals(ConfigButtonSelected.addDoubleLane)) {
            Graphics2D g2d = (Graphics2D) g;

            //previous coordinates
            Coordinates prevOrCurCoordinates = new Coordinates((previousRUnit == null ? currentX : previousRUnit.getX()),
                    (previousRUnit == null ? currentY : previousRUnit.getY()));

            //current coordinates
            Coordinates currentCoordinates = new Coordinates(currentX, currentY);

            //Populate changeable coordinates
            int currentChangeableX = Common.getAdjacentPointToB(prevOrCurCoordinates, currentCoordinates, 10, 90).getX();
            int currentChangeableY = Common.getAdjacentPointToB(prevOrCurCoordinates, currentCoordinates, 10, 90).getY();

            double directionChangeable = 1000;
            if (previousChangeableRunit != null) {
                directionChangeable = Common.getAngle(previousChangeableRunit.getX(), previousChangeableRunit.getY(), currentChangeableX, currentChangeableY);
            }
            Coordinates changeableA = new Coordinates((previousChangeableRunit == null ? currentChangeableX : previousChangeableRunit.getX()),
                    (previousChangeableRunit == null ? currentChangeableY : previousChangeableRunit.getY()));
            //Smart Drawing feature to enable auto completion of smooth double roads when clicked at 2 points in the drawing panel.
            do {
                if ((previousRUnit == null && previousChangeableRunit == null) ||
                        (!prevOrCurCoordinates.equals(new Coordinates(previousRUnit.getX(), previousRUnit.getY())))) {
                    //Add and Return RUnit for double lane and store it as previous RUnit
                    Map<String, IRUnitManager> prevRUnitMap = roadNetworkManager.addDoubleLane(prevOrCurCoordinates.getX(), prevOrCurCoordinates.getY(), changeableA.getX(), changeableA.getY(), previousRUnit, previousChangeableRunit);
                    if (prevRUnitMap != null) {
                        previousRUnit = prevRUnitMap.get("runit");
                        previousChangeableRunit = prevRUnitMap.get("changeableRunit");
                    }
                    doubleLaneRUnits.add(previousRUnit);
                    changeAbleLaneRUnits.add(previousChangeableRunit);
                    //Drawing the road with repositioned coordinates
                    Coordinates coordinates = getRepositionedImageCoordinates(bufferedRoadImage, currentX, currentY);
                    g2d.drawImage(bufferedRoadImage, coordinates.getX(), coordinates.getY(), drawingBoardPanel);
                    //Drawing the changeable road with repositioned coordinates
                    coordinates = getRepositionedImageCoordinates(bufferedChangeableRoadImage, currentChangeableX, currentChangeableY);
                    g2d.drawImage(bufferedChangeableRoadImage, coordinates.getX(), coordinates.getY(), drawingBoardPanel);
                }

                //remember the previous A coordinate for getting the correct adjacent point
                Coordinates oldA = new Coordinates(Common.getNthPrevRUnit(previousRUnit, 3).getX(),
                        Common.getNthPrevRUnit(previousRUnit, 3).getY());

                //get the next point from A to B
                prevOrCurCoordinates = new Coordinates(Common.getNextPointFromTo(prevOrCurCoordinates, currentCoordinates).getX(), Common.getNextPointFromTo(prevOrCurCoordinates, currentCoordinates).getY());

                //get new changeable coordinates
                currentChangeableX = Common.getAdjacentPointToB(oldA, prevOrCurCoordinates, 10, 90).getX();
                currentChangeableY = Common.getAdjacentPointToB(oldA, prevOrCurCoordinates, 10, 90).getY();

                //set new changeable
                changeableA = new Coordinates(currentChangeableX, currentChangeableY);


            }
            while (prevOrCurCoordinates.getY() != currentCoordinates.getY() & prevOrCurCoordinates.getX() != currentCoordinates.getX());
        }


        if (configButtonSelected.equals(ConfigButtonSelected.trafficLight)) {
            String trafficLightId = "TL-" + trafficLightIdIndex;
            IRUnitManager bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                //Dynamically adding rows to the traffic light configuration table with "RED" colored cells and corresponding traffic light ID
                if (model != null) {
                    model.addRow(new Object[]{trafficLightId, false, false, false, false, false, false, false, false, false, false});
                    trafficLightCoordinates.put(trafficLightId, new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()));
                    trafficLightIdIndex++;
                }
                //Updating the best match rUnit with the traffic Light
                TrafficLight trafficLight = new TrafficLight();
                trafficLight.setTrafficLightID(trafficLightId);
                simEngine.getDataAndStructures().getRoadNetworkManager().addTrafficLight(bestMatchRUnit, trafficLight);
                //Drawing the traffic Light and adding the traffic light cycle configuration
                JButton tl = new JButton();
                Coordinates coordinates = getRepositionedImageCoordinates(trafficLightImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                tl.setBounds(coordinates.getX(), coordinates.getY(), trafficLightImage.getWidth(drawingBoardPanel), trafficLightImage.getHeight(drawingBoardPanel));
                tl.setToolTipText("Traffic Light: " + trafficLightIdIndex);
                tl.setIcon(new ImageIcon(trafficLightImage));
                drawingBoardPanel.add(tl);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.zebraCrossing)) {
            String trafficLightId = "ZTL-" + zebraCrossingTrafficLightIdIndex;
            IRUnitManager bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                //Dynamically adding rows to the traffic light configuration table with "RED" colored cells and corresponding traffic light ID
                if (model != null) {
                    model.addRow(new Object[]{trafficLightId, false, false, false, false, false, false, false, false, false, false});
                    zebraCrossingCoordinates.put(trafficLightId, new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()));
                    zebraCrossingTrafficLightIdIndex++;
                }
                //Updating the best match rUnit with the zebra crossing
                TrafficLight trafficLight = new TrafficLight();
                trafficLight.setTrafficLightID(trafficLightId);
                simEngine.getDataAndStructures().getRoadNetworkManager().addZebraCrossing(bestMatchRUnit, trafficLight);
                //Drawing the zebra crossing with traffic light configuration
                JButton ztl = new JButton();
                ztl.setToolTipText("Zebra Crossing Traffic Light: " + zebraCrossingTrafficLightIdIndex);
                Coordinates coordinates = getRepositionedImageCoordinates(zebraCrossingImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                ztl.setBounds(coordinates.getX(), coordinates.getY(), zebraCrossingImage.getWidth(drawingBoardPanel), zebraCrossingImage.getHeight(drawingBoardPanel));
                ztl.setIcon(new ImageIcon(zebraCrossingImage));
                drawingBoardPanel.add(ztl);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.blockage)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(blockageCoordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addBlockage(bestMatchRUnit);
                //Drawing the blockage
                Coordinates coordinates = getRepositionedImageCoordinates(blockageImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(blockageImage, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }


        if (configButtonSelected.equals(ConfigButtonSelected.vehicleFactory)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(vehicleFactoryCoordinates);
            if (bestMatchRUnit != null) {
                //Adding vehicle factory and dataStructures
                simEngine.getDataAndStructures().getVehicleFactoryManager().addVehicleFactory(roadNetworkManager.getRoadNetwork().getrUnitHashtable().get(String.valueOf(bestMatchRUnit.getId())));
                //Drawing the vehicleFactory
                Coordinates coordinates = getRepositionedImageCoordinates(vehicleFactoryImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(vehicleFactoryImage, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }


        if (configButtonSelected.equals(ConfigButtonSelected.stop)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(stopCoordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addStopSign(bestMatchRUnit);
                //Drawing the stop image
                Coordinates coordinates = getRepositionedImageCoordinates(stopImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(stopImage, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.left)) {
            IRUnitManager bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                //Location Dialog Box to input the location for the sign boards
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.initUI();
                locationDialog.setVisible(true);
                String destination = locationDialog.getDestination();
                if (destination != null) {
                    leftCoordinates.put(new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()), destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.left);
                    //Drawing the left sign
                    JButton left = new JButton();
                    left.setToolTipText("Take left to go to " + destination);
                    Coordinates coordinates = getRepositionedImageCoordinates(leftSignImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                    left.setBounds(coordinates.getX(), coordinates.getY(), leftSignImage.getWidth(drawingBoardPanel), leftSignImage.getHeight(drawingBoardPanel));
                    left.setIcon(new ImageIcon(leftSignImage));
                    drawingBoardPanel.add(left);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.right)) {
            IRUnitManager bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                //Location Dialog Box to input the location for the sign boards
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.initUI();
                locationDialog.setVisible(true);
                String destination = locationDialog.getDestination();
                if (destination != null) {
                    rightCoordinates.put(new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()), destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.right);
                    //Drawing the right sign
                    JButton right = new JButton();
                    right.setToolTipText("Take right to go to " + destination);
                    Coordinates coordinates = getRepositionedImageCoordinates(rightSignImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                    right.setBounds(coordinates.getX(), coordinates.getY(), rightSignImage.getWidth(drawingBoardPanel), rightSignImage.getHeight(drawingBoardPanel));
                    right.setIcon(new ImageIcon(rightSignImage));
                    drawingBoardPanel.add(right);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.straight)) {
            IRUnitManager bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                //Location Dialog Box to input the location for the sign boards
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.initUI();
                locationDialog.setVisible(true);
                String destination = locationDialog.getDestination();
                if (destination != null) {
                    straightCoordinates.put(new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()), destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.straight);
                    //Drawing the straight sign
                    JButton straight = new JButton();
                    straight.setToolTipText("Go straight to go to " + destination);
                    Coordinates coordinates = getRepositionedImageCoordinates(straightSignImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                    straight.setBounds(coordinates.getX(), coordinates.getY(), straightSignImage.getWidth(drawingBoardPanel), straightSignImage.getHeight(drawingBoardPanel));
                    straight.setIcon(new ImageIcon(straightSignImage));
                    drawingBoardPanel.add(straight);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed20)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(speed20Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 20);
                //Drawing the speed limit
                Coordinates coordinates = getRepositionedImageCoordinates(speed20Image, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(speed20Image, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed30)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(speed30Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the speed sign board
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 30);
                //Drawing the speed limit
                Coordinates coordinates = getRepositionedImageCoordinates(speed30Image, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(speed30Image, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed50)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(speed50Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the speed sign board
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 50);
                //Drawing the speed limit
                Coordinates coordinates = getRepositionedImageCoordinates(speed50Image, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(speed50Image, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed60)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(speed60Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the speed sign board
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 60);
                //Drawing the speed limit
                Coordinates coordinates = getRepositionedImageCoordinates(speed60Image, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(speed60Image, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed70)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(speed70Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the speed sign board
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 70);
                //Drawing the speed limit
                Coordinates coordinates = getRepositionedImageCoordinates(speed70Image, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(speed70Image, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed90)) {
            IRUnitManager bestMatchRUnit = fetchAndAddBestMatchRUnit(speed90Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the speed sign board
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 90);
                //Drawing the speed limit
                Coordinates coordinates = getRepositionedImageCoordinates(speed90Image, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                g.drawImage(speed90Image, coordinates.getX(), coordinates.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.welcome)) {
            IRUnitManager bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                //Destination Dialog Box to input the destination for the welcome to destination sign board
                DestinationDialog destinationDialog = new DestinationDialog();
                destinationDialog.initUI();
                destinationDialog.setVisible(true);
                String destination = destinationDialog.getDestination();
                if (destination != null) {
                    welcomeCoordinates.put(new Coordinates(bestMatchRUnit.getX(), bestMatchRUnit.getY()), destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addWelcomeSign(bestMatchRUnit, destination);
                    JButton dest = new JButton();
                    dest.setToolTipText("Welcome to " + destination);
                    //Drawing the welcome to destination sign board
                    Coordinates coordinates = getRepositionedImageCoordinates(welcomeImage, bestMatchRUnit.getX(), bestMatchRUnit.getY());
                    dest.setBounds(coordinates.getX(), coordinates.getY(), welcomeImage.getWidth(drawingBoardPanel), welcomeImage.getHeight(drawingBoardPanel));
                    dest.setIcon(new ImageIcon(welcomeImage));
                    drawingBoardPanel.add(dest);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (model != null) {
            //Dynamically updating the traffic light color values in the backend data structures for each repaint() call
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

    //Fetches and adds the coordinates of the best match RUnit while installing any configuration on the roads
    private IRUnitManager fetchAndAddBestMatchRUnit(List<Coordinates> coordinates) {
        for (IRUnitManager rUnit : simEngine.getDataAndStructures().getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), rUnitImage.getWidth(drawingBoardPanel) + 5, rUnitImage.getHeight(drawingBoardPanel) + 5);
            if (rectangle.contains(currentX, currentY)) {
                if (coordinates != null) {
                    coordinates.add(new Coordinates(rUnit.getX(), rUnit.getY()));
                }
                return rUnit;
            }
        }
        return null;
    }

    //Fetches the best match RUnit while installing any configuration on the roads
    private IRUnitManager fetchBestMatchRUnit() {
        for (IRUnitManager rUnit : simEngine.getDataAndStructures().getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), rUnitImage.getWidth(drawingBoardPanel) + 5, rUnitImage.getHeight(drawingBoardPanel) + 5);
            if (rectangle.contains(currentX, currentY)) {
                return rUnit;
            }
        }
        return null;
    }

    //Simulation Phase - Displaying roads and other traffic elements
    //This function paints/draws all the added components
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        //Drawing single road
        for (IRUnitManager rUnit : singleLaneRUnits) {
            Coordinates coordinates = getRepositionedImageCoordinates(rUnitImage, rUnit.getX(), rUnit.getY());
            g2D.drawImage(rUnitImage, coordinates.getX(), coordinates.getY(), drawingBoardPanel);
            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            BasicStroke bs = new BasicStroke(2);
            g2D.setStroke(bs);
        }


        //Drawing double road
        for (IRUnitManager rUnit : doubleLaneRUnits) {
            Coordinates coordinates = getRepositionedImageCoordinates(rUnitImage, rUnit.getX(), rUnit.getY());
            g2D.drawImage(rUnitImage, coordinates.getX(), coordinates.getY(), drawingBoardPanel);
            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            BasicStroke bs = new BasicStroke(2);
            g2D.setStroke(bs);
        }

        //Drawing double road - Changeable
        for (IRUnitManager rUnit : changeAbleLaneRUnits) {
            Coordinates coordinates = getRepositionedImageCoordinates(rUnitImage2, rUnit.getX(), rUnit.getY());
            g2D.drawImage(rUnitImage2, coordinates.getX(), coordinates.getY(), drawingBoardPanel);
            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            BasicStroke bs = new BasicStroke(2);
            g2D.setStroke(bs);
        }


        //Drawing traffic lights
        for (Coordinates coordinate : trafficLightCoordinates.values()) {
            coordinate = getRepositionedImageCoordinates(trafficLightImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(trafficLightImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing zebra crossing
        for (Coordinates coordinate : zebraCrossingCoordinates.values()) {
            coordinate = getRepositionedImageCoordinates(zebraCrossingImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(zebraCrossingImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing vehicle factories
        for (Coordinates coordinate : vehicleFactoryCoordinates) {
            coordinate = getRepositionedImageCoordinates(vehicleFactoryImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(vehicleFactoryImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing blockages
        for (Coordinates coordinate : blockageCoordinates) {
            coordinate = getRepositionedImageCoordinates(blockageImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(blockageImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing stop signs
        for (Coordinates coordinate : stopCoordinates) {
            coordinate = getRepositionedImageCoordinates(stopImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(stopImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing left signs
        for (Coordinates coordinate : leftCoordinates.keySet()) {
            coordinate = getRepositionedImageCoordinates(leftSignImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(leftSignImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing right signs
        for (Coordinates coordinate : rightCoordinates.keySet()) {
            coordinate = getRepositionedImageCoordinates(rightSignImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(rightSignImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing straight signs
        for (Coordinates coordinate : straightCoordinates.keySet()) {
            coordinate = getRepositionedImageCoordinates(straightSignImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(straightSignImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed20 signs
        for (Coordinates coordinate : speed20Coordinates) {
            coordinate = getRepositionedImageCoordinates(speed20Image, coordinate.getX(), coordinate.getY());
            g2D.drawImage(speed20Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed30 signs
        for (Coordinates coordinate : speed30Coordinates) {
            coordinate = getRepositionedImageCoordinates(speed30Image, coordinate.getX(), coordinate.getY());
            g2D.drawImage(speed30Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed50 signs
        for (Coordinates coordinate : speed50Coordinates) {
            coordinate = getRepositionedImageCoordinates(speed50Image, coordinate.getX(), coordinate.getY());
            g2D.drawImage(speed50Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed60 signs
        for (Coordinates coordinate : speed60Coordinates) {
            coordinate = getRepositionedImageCoordinates(speed60Image, coordinate.getX(), coordinate.getY());
            g2D.drawImage(speed60Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }
        //Drawing speed70 signs
        for (Coordinates coordinate : speed70Coordinates) {
            coordinate = getRepositionedImageCoordinates(speed70Image, coordinate.getX(), coordinate.getY());
            g2D.drawImage(speed70Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed90 signs
        for (Coordinates coordinate : speed90Coordinates) {
            coordinate = getRepositionedImageCoordinates(speed90Image, coordinate.getX(), coordinate.getY());
            g2D.drawImage(speed90Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing welcome signs
        for (Coordinates coordinate : welcomeCoordinates.keySet()) {
            coordinate = getRepositionedImageCoordinates(welcomeImage, coordinate.getX(), coordinate.getY());
            g2D.drawImage(welcomeImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Move Vehicles on RUnits on UI

        Graphics2D g2d = (Graphics2D) g;
        for (ObjectInSpace objectInSpace : simEngine.getDataAndStructures().getSpaceManager().getObjects()) {
            if (objectInSpace.isVisible()) {
                Image vehicleImage;
                //Identifying the vehicle type
                if (objectInSpace.getVehicleType().equals(VehicleType.car)) {
                    vehicleImage = carImage;
                } else if (objectInSpace.getVehicleType().equals(VehicleType.heavyLoad)) {
                    vehicleImage = truckImage;
                } else {
                    vehicleImage = emergencyVehicleImage;
                }

                //Rotating or aligning the vehicles wrt to the road

                double angle = objectInSpace.getDirection().getAngle();
                if (angle < 0) {
                    angle = angle + 360;
                }
                // Save the Affine Transformation of the Graphics
                AffineTransform affineTransform = g2d.getTransform();
                //Rotate the vehicle based on the angle calculated
                g2d.rotate(Math.toRadians(angle), objectInSpace.getX(), objectInSpace.getY());
                Coordinates coordinates = getRepositionedImageCoordinates(vehicleImage, objectInSpace.getX(), objectInSpace.getY());
                //Draw the vehicle on the road based on the repositioned coordinates
                g2d.drawImage(vehicleImage, coordinates.getX(), coordinates.getY(), drawingBoardPanel);
                //Set the Affine Transformation to the original value
                g2d.setTransform(affineTransform);
            }
        }

        //Change colour of traffic lights on the UI
        if (isSimulationPlaying) {
            for (String trafficLightId : roadNetworkManager.getRoadNetwork().getTrafficLightHashtable().keySet()) {
                TrafficLight trafficLight = roadNetworkManager.getRoadNetwork().getTrafficLightHashtable().get(trafficLightId);
                if (trafficLight.getTrafficLightCurrentColor()) {
                    if (trafficLightCoordinates.get(trafficLightId) != null) {
                        Coordinates coordinate = getRepositionedImageCoordinates(trafficLightImage, trafficLightCoordinates.get(trafficLightId).getX(), trafficLightCoordinates.get(trafficLightId).getY());
                        g2d.drawImage(greenLightImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
                    } else {
                        Coordinates coordinate = getRepositionedImageCoordinates(zebraCrossingImage, zebraCrossingCoordinates.get(trafficLightId).getX(), zebraCrossingCoordinates.get(trafficLightId).getY());
                        g2d.drawImage(greenLightZebraImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
                    }
                } else {
                    if (trafficLightCoordinates.get(trafficLightId) != null) {
                        Coordinates coordinate = getRepositionedImageCoordinates(trafficLightImage, trafficLightCoordinates.get(trafficLightId).getX(), trafficLightCoordinates.get(trafficLightId).getY());
                        g2d.drawImage(redLightImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
                    } else {
                        Coordinates coordinate = getRepositionedImageCoordinates(zebraCrossingImage, zebraCrossingCoordinates.get(trafficLightId).getX(), zebraCrossingCoordinates.get(trafficLightId).getY());
                        g2d.drawImage(redLightZebraImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
                    }
                }
            }
        }
        //Update the simulation tick time
        currentSecondValue.setText(String.valueOf(simEngine.getDataAndStructures().getGlobalConfigManager().getCurrentSecond()));
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private Coordinates getRepositionedImageCoordinates(Image image, int x, int y) {
        /*The image is drawn with its top-left corner at x,y . This function fetches the repositioned coordinates of the image to draw it adjust the X and Y coordinates
         to the centre*/
        x = x - image.getWidth(drawingBoardPanel) / 2;
        y = y - image.getHeight(drawingBoardPanel) / 2;
        return new Coordinates(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simEngine.performAction();
        drawingBoardPanel.repaint();
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getX() {
        return currentX;
    }

    public int getY() {
        return currentY;
    }

    public void addSingleLaneMouseDragMotionListener() {
        drawingBoardPanel.addMouseMotionListener(new MouseDragMotionListener(this));
        drawingBoardPanel.addMouseListener(new SingleLaneMotionListener(this));
    }

    public void addDoubleLaneMouseDragMotionListener() {
        drawingBoardPanel.addMouseMotionListener(new MouseDragMotionListener(this));
        drawingBoardPanel.addMouseListener(new DoubleLaneMotionListener(this));
    }

    public void addZebraCrossingButtonListener() {
        drawingBoardPanel.addMouseListener(new ZebraCrossingButtonListener(this));
    }

    public void addTrafficLightButtonListener() {
        drawingBoardPanel.addMouseListener(new TrafficLightButtonListener(this));
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
        drawingBoardPanel.addMouseListener(new BlockageButtonListener(this));
    }

    public void addVehicleFactoryButtonActionListener() {
        drawingBoardPanel.addMouseListener(new VehicleFactoryButtonListener(this));
    }

    public void addStopButtonActionListener() {
        drawingBoardPanel.addMouseListener(new StopButtonListener(this));
    }

    public void addLeftButtonActionListener() {
        drawingBoardPanel.addMouseListener(new LeftButtonListener(this));
    }

    public void addRightButtonActionListener() {
        drawingBoardPanel.addMouseListener(new RightButtonListener(this));
    }

    public void addStraightButtonActionListener() {
        drawingBoardPanel.addMouseListener(new StraightButtonListener(this));
    }

    public void addSpeed20ButtonActionListener() {
        drawingBoardPanel.addMouseListener(new Speed20ButtonListener(this));
    }

    public void addSpeed30ButtonActionListener() {
        drawingBoardPanel.addMouseListener(new Speed30ButtonListener(this));
    }

    public void addSpeed50ButtonActionListener() {
        drawingBoardPanel.addMouseListener(new Speed50ButtonListener(this));
    }

    public void addSpeed60ButtonActionListener() {
        drawingBoardPanel.addMouseListener(new Speed60ButtonListener(this));
    }

    public void addSpeed70ButtonActionListener() {
        drawingBoardPanel.addMouseListener(new Speed70ButtonListener(this));
    }

    public void addSpeed90ButtonActionListener() {
        drawingBoardPanel.addMouseListener(new Speed90ButtonListener(this));
    }

    public void addWelcomeDestinationButtonActionListener() {
        drawingBoardPanel.addMouseListener(new WelcomeDestinationButtonListener(this));
    }

    public IRUnitManager getPreviousRUnit() {
        return previousRUnit;
    }

    public void setPreviousRUnit(IRUnitManager previousRUnit) {
        this.previousRUnit = previousRUnit;
    }

    public IRUnitManager getPreviousChangeableRunit() {
        return previousChangeableRunit;
    }

    public void setPreviousChangeableRunit(IRUnitManager previousChangeableRunit) {
        this.previousChangeableRunit = previousChangeableRunit;
    }

    public void clean() {
        //Deleting traffic light configuration tables holding the coordinates of various traffic elements
        model.setRowCount(0);
        singleLaneRUnits.clear();
        doubleLaneRUnits.clear();
        trafficLightCoordinates.clear();
        zebraCrossingCoordinates.clear();
        blockageCoordinates.clear();
        stopCoordinates.clear();
        leftCoordinates.clear();
        rightCoordinates.clear();
        straightCoordinates.clear();
        welcomeCoordinates.clear();
        speed20Coordinates.clear();
        speed30Coordinates.clear();
        speed50Coordinates.clear();
        speed60Coordinates.clear();
        speed70Coordinates.clear();
        speed90Coordinates.clear();
        vehicleFactoryCoordinates.clear();
        drawingBoardPanel.removeAll();
        drawingBoardPanel.repaint();
    }

    public boolean isSimulationStarted() {
        return isSimulationStarted;
    }

    public void setSimulationStarted(boolean isSimulationStarted) {
        this.isSimulationStarted = isSimulationStarted;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public JPanel getDrawingBoardPanel() {
        return drawingBoardPanel;
    }

    public void setDrawingBoardPanel(JPanel drawingBoardPanel) {
        this.drawingBoardPanel = drawingBoardPanel;
    }

    public Set<IRUnitManager> getSingleLaneRUnits() {
        return singleLaneRUnits;
    }

    public void setSingleLaneRUnits(Set<IRUnitManager> singleLaneRUnits) {
        this.singleLaneRUnits = singleLaneRUnits;
    }

    public Set<IRUnitManager> getDoubleLaneRUnits() {
        return doubleLaneRUnits;
    }

    public void setDoubleLaneRUnits(Set<IRUnitManager> doubleLaneRUnits) {
        this.doubleLaneRUnits = doubleLaneRUnits;
    }

    public List<Coordinates> getVehicleFactoryCoordinates() {
        return vehicleFactoryCoordinates;
    }

    public void setVehicleFactoryCoordinates(List<Coordinates> vehicleFactoryCoordinates) {
        this.vehicleFactoryCoordinates = vehicleFactoryCoordinates;
    }

    public Map<String, Coordinates> getTrafficLightCoordinates() {
        return trafficLightCoordinates;
    }

    public void setTrafficLightCoordinates(Map<String, Coordinates> trafficLightCoordinates) {
        this.trafficLightCoordinates = trafficLightCoordinates;
    }

    public Map<String, Coordinates> getZebraCrossingCoordinates() {
        return zebraCrossingCoordinates;
    }

    public void setZebraCrossingCoordinates(Map<String, Coordinates> zebraCrossingCoordinates) {
        this.zebraCrossingCoordinates = zebraCrossingCoordinates;
    }

    public List<Coordinates> getBlockageCoordinates() {
        return blockageCoordinates;
    }

    public void setBlockageCoordinates(List<Coordinates> blockageCoordinates) {
        this.blockageCoordinates = blockageCoordinates;
    }

    public List<Coordinates> getStopCoordinates() {
        return stopCoordinates;
    }

    public void setStopCoordinates(List<Coordinates> stopCoordinates) {
        this.stopCoordinates = stopCoordinates;
    }

    public Map<Coordinates, String> getLeftCoordinates() {
        return leftCoordinates;
    }

    public void setLeftCoordinates(Map<Coordinates, String> leftCoordinates) {
        this.leftCoordinates = leftCoordinates;
    }

    public Map<Coordinates, String> getStraightCoordinates() {
        return straightCoordinates;
    }

    public void setStraightCoordinates(Map<Coordinates, String> straightCoordinates) {
        this.straightCoordinates = straightCoordinates;
    }

    public Map<Coordinates, String> getRightCoordinates() {
        return rightCoordinates;
    }

    public void setRightCoordinates(Map<Coordinates, String> rightCoordinates) {
        this.rightCoordinates = rightCoordinates;
    }

    public List<Coordinates> getSpeed20Coordinates() {
        return speed20Coordinates;
    }

    public void setSpeed20Coordinates(List<Coordinates> speed20Coordinates) {
        this.speed20Coordinates = speed20Coordinates;
    }

    public List<Coordinates> getSpeed30Coordinates() {
        return speed30Coordinates;
    }

    public void setSpeed30Coordinates(List<Coordinates> speed30Coordinates) {
        this.speed30Coordinates = speed30Coordinates;
    }

    public List<Coordinates> getSpeed50Coordinates() {
        return speed50Coordinates;
    }

    public void setSpeed50Coordinates(List<Coordinates> speed50Coordinates) {
        this.speed50Coordinates = speed50Coordinates;
    }

    public List<Coordinates> getSpeed60Coordinates() {
        return speed60Coordinates;
    }

    public void setSpeed60Coordinates(List<Coordinates> speed60Coordinates) {
        this.speed60Coordinates = speed60Coordinates;
    }

    public List<Coordinates> getSpeed70Coordinates() {
        return speed70Coordinates;
    }

    public void setSpeed70Coordinates(List<Coordinates> speed70Coordinates) {
        this.speed70Coordinates = speed70Coordinates;
    }

    public List<Coordinates> getSpeed90Coordinates() {
        return speed90Coordinates;
    }

    public void setSpeed90Coordinates(List<Coordinates> speed90Coordinates) {
        this.speed90Coordinates = speed90Coordinates;
    }

    public Map<Coordinates, String> getWelcomeCoordinates() {
        return welcomeCoordinates;
    }

    public void setWelcomeCoordinates(Map<Coordinates, String> welcomeCoordinates) {
        this.welcomeCoordinates = welcomeCoordinates;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public IRoadNetworkManager getRoadNetworkManager() {
        return roadNetworkManager;
    }

    public void setRoadNetworkManager(IRoadNetworkManager roadNetworkManager) {
        this.roadNetworkManager = roadNetworkManager;
    }

    public SimEngine getSimEngine() {
        return simEngine;
    }

    public void setSimEngine(SimEngine simEngine) {
        this.simEngine = simEngine;
    }
}