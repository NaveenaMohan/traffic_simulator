package ui.components;

import common.Common;
import engine.SimEngine;
import managers.globalconfig.VehicleType;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.DirectionSignType;
import managers.runit.RUnit;
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
    BufferedImage bufferedRoadImage;
    BufferedImage bufferedChangeableRoadImage;
    private int currentX, currentY;
    private Set<RUnit> singleLaneRUnits = new LinkedHashSet<RUnit>();
    private Set<RUnit> doubleLaneRUnits = new LinkedHashSet<RUnit>();
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
    private RoadNetworkManager roadNetworkManager;
    private SimEngine simEngine;
    private RUnit previousRUnit;
    private RUnit previousChangeableRunit;
    private Image rUnitImage;
    private Image trafficLightImage;
    private Image carImage;
    private Image doubleRoad;
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

    public DrawingBoard(DefaultTableModel model, RoadNetworkManager roadNetworkManager, SimEngine simEngine, JLabel currentSecondValue) {
        this.model = model;
        this.roadNetworkManager = roadNetworkManager;
        this.simEngine = simEngine;
        this.currentSecondValue = currentSecondValue;
    }

    private static int getTrafficLightIdIndex() {
        return trafficLightIdIndex;
    }

    public static void setTrafficLightIdIndex(int trafficLightIdIndex) {
        DrawingBoard.trafficLightIdIndex = trafficLightIdIndex;
    }

    public void initialize() {
        rUnitImage = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/road.gif"));
        doubleRoad = drawingBoardPanel.getToolkit().getImage(DrawingBoard.class.getResource("/resources/doubleRoad.gif"));
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
        changeableRoadMediaTracker.addImage(doubleRoad, 1);
        try {
            changeableRoadMediaTracker.waitForAll();
        } catch (Exception e) {
            System.out.println("Exception while loading DoubleRoadImage");
        }
        bufferedChangeableRoadImage = new BufferedImage(20, 20,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D b = bufferedChangeableRoadImage.createGraphics();
        b.drawImage(doubleRoad, 0, 0, drawingBoardPanel);

    }

    //Configuration Phase - Drawing road and configuring other traffic elements
    public void paintComponent(Graphics g) {
        //Add single lane
        if (configButtonSelected.equals(ConfigButtonSelected.addSingleLane)) {
            Graphics2D g2D = (Graphics2D) g;

            Coordinates A = new Coordinates((previousRUnit==null ? currentX : previousRUnit.getX()),
                    (previousRUnit==null ? currentY : previousRUnit.getY()));
            Coordinates B = new Coordinates(currentX, currentY);

            do {
                if (previousRUnit == null || !A.equals(new Coordinates(previousRUnit.getX(),previousRUnit.getY()))) {
                    //Add and Return RUnit for single lane and store it as previous RUnit
                    previousRUnit = roadNetworkManager.addSingleLane(A.getX(), A.getY(), previousRUnit);
                    singleLaneRUnits.add(previousRUnit);
                    g2D.drawImage(bufferedRoadImage, currentX, currentY, drawingBoardPanel);
                }
                A = new Coordinates(Common.getNextPointFromTo(A, B).getX(), Common.getNextPointFromTo(A,B).getY());
            }while(A.getY()!=B.getY() & A.getX() != B.getX());
        }

        //Add double lane

        if (configButtonSelected.equals(ConfigButtonSelected.addDoubleLane)) {
            Graphics2D g2d = (Graphics2D) g;

            Coordinates A = new Coordinates((previousRUnit == null ? currentX : previousRUnit.getX()),
                    (previousRUnit == null ? currentY : previousRUnit.getY()));
            Coordinates B = new Coordinates(currentX, currentY);
            int currentChangeableX = Common.getAdjacentPointToB(A, B, 20, 90).getX();
            int currentChangeableY = Common.getAdjacentPointToB(A, B, 20, 90).getY();
            Coordinates changeableA = new Coordinates((previousChangeableRunit == null ? currentChangeableX : previousChangeableRunit.getX()),
                    (previousChangeableRunit == null ? currentChangeableY : previousChangeableRunit.getY()));
            Coordinates changeableB = new Coordinates(currentChangeableX, currentChangeableY);
            do {
                if ((previousRUnit == null && previousChangeableRunit == null) ||
                        (!A.equals(new Coordinates(previousRUnit.getX(), previousRUnit.getY())) && !changeableA.equals(new Coordinates(previousChangeableRunit.getX(), previousChangeableRunit.getY())))) {
                    //Add and Return RUnit for double lane and store it as previous RUnit
                    Map<String, RUnit> prevRUnitMap = roadNetworkManager.addDoubleLane(A.getX(), A.getY(), changeableA.getX(), changeableA.getY(), previousRUnit, previousChangeableRunit);
                    if (prevRUnitMap != null) {
                        previousRUnit = prevRUnitMap.get("runit");
                        previousChangeableRunit = prevRUnitMap.get("changeableRunit");
                    }
                    doubleLaneRUnits.add(previousRUnit);
                    //g2d.drawImage(bufferedChangeableRoadImage, currentX, currentY, drawingBoardPanel);
                    g2d.drawImage(bufferedRoadImage, currentX, currentY, drawingBoardPanel);
                    g2d.drawImage(bufferedChangeableRoadImage, currentChangeableX, currentChangeableY, drawingBoardPanel);
                }
                A = new Coordinates(Common.getNextPointFromTo(A, B).getX(), Common.getNextPointFromTo(A, B).getY());
                changeableA = new Coordinates(Common.getNextPointFromTo(changeableA, changeableB).getX(), Common.getNextPointFromTo(changeableA,changeableB).getY());
            } while (A.getY() != B.getY() & A.getX() != B.getX());
        }


        if (configButtonSelected.equals(ConfigButtonSelected.trafficLight)) {
            String trafficLightId = "TL-" + trafficLightIdIndex;
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the traffic Light
                TrafficLight trafficLight = new TrafficLight();
                trafficLight.setTrafficLightID(trafficLightId);
                simEngine.getDataAndStructures().getRoadNetworkManager().addTrafficLight(bestMatchRUnit, trafficLight);
                //Drawing the traffic Light and adding the traffic light cycle configuration
                JButton tl = new JButton();
                tl.setBounds(bestMatchRUnit.getX(), bestMatchRUnit.getY(), 15, 15);
                tl.setToolTipText("Traffic Light: " + trafficLightIdIndex);
                tl.setIcon(new ImageIcon(trafficLightImage));
                drawingBoardPanel.add(tl);

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
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the zebra crossing
                TrafficLight trafficLight = new TrafficLight();
                trafficLight.setTrafficLightID(trafficLightId);
                simEngine.getDataAndStructures().getRoadNetworkManager().addZebraCrossing(bestMatchRUnit, trafficLight);
                //Drawing the zebra crossing with traffic light configuration
                JButton ztl = new JButton();
                ztl.setToolTipText("Zebra Crossing Traffic Light: " + zebraCrossingTrafficLightIdIndex);
                ztl.setBounds(bestMatchRUnit.getX(), bestMatchRUnit.getY(), 15, 15);
                ztl.setIcon(new ImageIcon(zebraCrossingImage));
                drawingBoardPanel.add(ztl);

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
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addBlockage(bestMatchRUnit);
                //Drawing the blockage
                g.drawImage(blockageImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }


        if (configButtonSelected.equals(ConfigButtonSelected.vehicleFactory)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(vehicleFactoryCoordinates);
            if (bestMatchRUnit != null) {
                //Adding vehicle factory and dataStructures
                simEngine.getDataAndStructures().getVehicleFactoryManager().addVehicleFactory(roadNetworkManager.getRoadNetwork().getrUnitHashtable().get(String.valueOf(bestMatchRUnit.getId())));
                //Drawing the vehicleFactory
                g.drawImage(vehicleFactoryImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }


        if (configButtonSelected.equals(ConfigButtonSelected.stop)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(stopCoordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addStopSign(bestMatchRUnit);
                //Drawing the stop image
                g.drawImage(stopImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.left)) {
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.initUI();
                locationDialog.setVisible(true);
                String destination = locationDialog.getDestination();
                if(destination != null ){
                    leftCoordinates.put(new Coordinates(bestMatchRUnit.getX(),bestMatchRUnit.getY()),destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.left);
                    //Drawing the left sign
                    JButton left = new JButton();
                    left.setToolTipText("Take left to go to " + destination);
                    left.setBounds(bestMatchRUnit.getX(), bestMatchRUnit.getY(), 15, 15);
                    left.setIcon(new ImageIcon(leftSignImage));
                    drawingBoardPanel.add(left);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.right)) {
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.initUI();
                locationDialog.setVisible(true);
                String destination = locationDialog.getDestination();
                if(destination != null ){
                    rightCoordinates.put(new Coordinates(bestMatchRUnit.getX(),bestMatchRUnit.getY()),destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.right);
                    //Drawing the right sign
                    JButton right = new JButton();
                    right.setToolTipText("Take right to go to " + destination);
                    right.setBounds(bestMatchRUnit.getX(), bestMatchRUnit.getY(), 15, 15);
                    right.setIcon(new ImageIcon(rightSignImage));
                    drawingBoardPanel.add(right);
                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.straight)) {
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.initUI();
                locationDialog.setVisible(true);
                String destination = locationDialog.getDestination();
                if(destination != null ){
                    straightCoordinates.put(new Coordinates(bestMatchRUnit.getX(),bestMatchRUnit.getY()),destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addDirectionSign(bestMatchRUnit, destination, DirectionSignType.straight);
                    //Drawing the straight sign
                    JButton straight = new JButton();
                    straight.setToolTipText("Go straight to go to " + destination);
                    straight.setBounds(bestMatchRUnit.getX(), bestMatchRUnit.getY(), 15, 15);
                    straight.setIcon(new ImageIcon(straightSignImage));
                    drawingBoardPanel.add(straight);                }
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed20)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed20Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 20);
                //Drawing the speed limit
                g.drawImage(speed20Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed30)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed30Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 30);
                //Drawing the speed limit
                g.drawImage(speed30Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed50)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed50Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 50);
                //Drawing the speed limit
                g.drawImage(speed50Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed60)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed60Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 60);
                //Drawing the speed limit
                g.drawImage(speed60Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed70)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed70Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 70);
                //Drawing the speed limit
                g.drawImage(speed70Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.speed90)) {
            RUnit bestMatchRUnit = fetchAndAddBestMatchRUnit(speed90Coordinates);
            if (bestMatchRUnit != null) {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addSpeedLimit(bestMatchRUnit, 90);
                g.drawImage(speed90Image, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, drawingBoardPanel);
            }
            configButtonSelected = ConfigButtonSelected.noOption;
        }

        if (configButtonSelected.equals(ConfigButtonSelected.welcome)) {
            RUnit bestMatchRUnit = fetchBestMatchRUnit();
            if (bestMatchRUnit != null) {
                DestinationDialog destinationDialog = new DestinationDialog();
                destinationDialog.initUI();
                destinationDialog.setVisible(true);
                String destination = destinationDialog.getDestination();
                if(destination != null ){
                    welcomeCoordinates.put(new Coordinates(bestMatchRUnit.getX(),bestMatchRUnit.getY()),destination);
                    //Updating the best match rUnit with the blockage
                    simEngine.getDataAndStructures().getRoadNetworkManager().addWelcomeSign(bestMatchRUnit, destination);
                    JButton dest = new JButton();
                    dest.setToolTipText("Welcome to " + destination);
                    dest.setBounds(bestMatchRUnit.getX(), bestMatchRUnit.getY(), 15, 15);
                    dest.setIcon(new ImageIcon(welcomeImage));
                    drawingBoardPanel.add(dest);
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
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), doubleRoad.getWidth(drawingBoardPanel), doubleRoad.getHeight(drawingBoardPanel));
            if (rectangle.contains(currentX, currentY)) {
                if (coordinates != null) {
                    coordinates.add(new Coordinates(rUnit.getX(), rUnit.getY()));
                }
                return rUnit;
            }
        }
        return null;
    }

    private RUnit fetchBestMatchRUnit() {
        for (RUnit rUnit : simEngine.getDataAndStructures().getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), doubleRoad.getWidth(drawingBoardPanel), doubleRoad.getHeight(drawingBoardPanel));
            if (rectangle.contains(currentX, currentY)) {
                return rUnit;
            }
        }
        return null;
    }

    //Simulation Phase - Displaying roads and other traffic elements
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

//        //Drawing single road
//        for (Coordinates coordinate : singleLaneRUnits) {
//            g2D.drawImage(rUnitImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
//            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            BasicStroke bs = new BasicStroke(2);
//            g2D.setStroke(bs);
//        }

        //Drawing single road
        for (RUnit rUnit : singleLaneRUnits) {
            AffineTransform affineTransform = g2D.getTransform();
            double angle = Common.getRoadBackwardDirection(rUnit,15);
            if (angle < 0) {
                angle = angle + 360;
            }
            g2D.rotate(Math.toRadians(-20), rUnit.getX(), rUnit.getY());
            g2D.rotate(Math.toRadians(angle), rUnit.getX(), rUnit.getY());
            g2D.drawImage(rUnitImage, rUnit.getX(), rUnit.getY(), drawingBoardPanel);
            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            BasicStroke bs = new BasicStroke(2);
            g2D.setStroke(bs);
            g2D.setTransform(affineTransform) ;
        }


        //Drawing double road
        for (RUnit rUnit : doubleLaneRUnits) {
            AffineTransform affineTransform = g2D.getTransform();
            double angle = Common.getRoadBackwardDirection(rUnit,15);
            if (angle < 0) {
                angle = angle + 360;
            }
            g2D.rotate(Math.toRadians(-20), rUnit.getX(), rUnit.getY());
            g2D.rotate(Math.toRadians(angle), rUnit.getX(), rUnit.getY());
            g2D.drawImage(doubleRoad, rUnit.getX(), rUnit.getY(), drawingBoardPanel);
            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            BasicStroke bs = new BasicStroke(2);
            g2D.setStroke(bs);
            g2D.setTransform(affineTransform) ;
        }


        //Drawing traffic lights
        for (Coordinates coordinate : trafficLightCoordinates.values()) {
            g2D.drawImage(trafficLightImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing zebra crossing
        for (Coordinates coordinate : zebraCrossingCoordinates.values()) {
            g2D.drawImage(zebraCrossingImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing vehicle factories
        for (Coordinates coordinate : vehicleFactoryCoordinates) {
            g2D.drawImage(vehicleFactoryImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing blockages
        for (Coordinates coordinate : blockageCoordinates) {
            g2D.drawImage(blockageImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing stop signs
        for (Coordinates coordinate : stopCoordinates) {
            g2D.drawImage(stopImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing left signs
        for (Coordinates coordinate : leftCoordinates.keySet()) {
            g2D.drawImage(leftSignImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing right signs
        for (Coordinates coordinate : rightCoordinates.keySet()) {
            g2D.drawImage(rightSignImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing straight signs
        for (Coordinates coordinate : straightCoordinates.keySet()) {
            g2D.drawImage(straightSignImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed20 signs
        for (Coordinates coordinate : speed20Coordinates) {
            g2D.drawImage(speed20Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed30 signs
        for (Coordinates coordinate : speed30Coordinates) {
            g2D.drawImage(speed30Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed50 signs
        for (Coordinates coordinate : speed50Coordinates) {
            g2D.drawImage(speed50Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed60 signs
        for (Coordinates coordinate : speed60Coordinates) {
            g2D.drawImage(speed60Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }
        //Drawing speed70 signs
        for (Coordinates coordinate : speed70Coordinates) {
            g2D.drawImage(speed70Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing speed90 signs
        for (Coordinates coordinate : speed90Coordinates) {
            g2D.drawImage(speed90Image, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Drawing welcome signs
        for (Coordinates coordinate : welcomeCoordinates.keySet()) {
            g2D.drawImage(welcomeImage, coordinate.getX(), coordinate.getY(), drawingBoardPanel);
        }

        //Move Vehicles on RUnits on UI

        Graphics2D g2d = (Graphics2D) g;
        for (ObjectInSpace objectInSpace : simEngine.getDataAndStructures().getSpaceManager().getObjects()) {
            if(objectInSpace.isVisible()){
                Image vehicleImage;
                if(objectInSpace.getVehicleType().equals(VehicleType.car)){
                    vehicleImage = carImage;
                }else if(objectInSpace.getVehicleType().equals(VehicleType.heavyLoad)){
                    vehicleImage = truckImage;
                }else{
                    vehicleImage = emergencyVehicleImage;
                }


                double angle = objectInSpace.getDirection().getAngle();
                if(angle < 0){
                    angle = angle + 360;
                }
                AffineTransform affineTransform = g2d.getTransform();
                g2d.rotate(Math.toRadians(angle),objectInSpace.getX(), objectInSpace.getY());
                //g2d.rotate(angle,objectInSpace.getX(), objectInSpace.getY());
                g2d.drawImage(vehicleImage, objectInSpace.getX(), objectInSpace.getY(), drawingBoardPanel);
                g2d.setTransform(affineTransform);
            }
        }

        //Change colour of traffic lights on the UI
        if (isSimulationPlaying) {
            for (String trafficLightId : roadNetworkManager.getRoadNetwork().getTrafficLightHashtable().keySet()) {
                TrafficLight trafficLight = roadNetworkManager.getRoadNetwork().getTrafficLightHashtable().get(trafficLightId);
                if (trafficLight.getTrafficLightCurrentColor()) {
                    if (trafficLightCoordinates.get(trafficLightId) != null) {
                        g2d.drawImage(greenLightImage, trafficLightCoordinates.get(trafficLightId).getX(), trafficLightCoordinates.get(trafficLightId).getY(), drawingBoardPanel);
                    } else {
                        g2d.drawImage(greenLightZebraImage, zebraCrossingCoordinates.get(trafficLightId).getX(), zebraCrossingCoordinates.get(trafficLightId).getY(), drawingBoardPanel);
                    }
                } else {
                    if (trafficLightCoordinates.get(trafficLightId) != null) {
                        g2d.drawImage(redLightImage, trafficLightCoordinates.get(trafficLightId).getX(), trafficLightCoordinates.get(trafficLightId).getY(), drawingBoardPanel);
                    } else {
                        g2d.drawImage(redLightZebraImage, zebraCrossingCoordinates.get(trafficLightId).getX(), zebraCrossingCoordinates.get(trafficLightId).getY(), drawingBoardPanel);
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

    public RUnit getPreviousRUnit() {
        return previousRUnit;
    }

    public void setPreviousRUnit(RUnit previousRUnit) {
        this.previousRUnit = previousRUnit;
    }

    public RUnit getPreviousChangeableRunit() {
        return previousChangeableRunit;
    }

    public void setPreviousChangeableRunit(RUnit previousChangeableRunit) {
        this.previousChangeableRunit = previousChangeableRunit;
    }

    public void clean() {
        //Deleting traffic light configuration table
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

    public Set<RUnit> getSingleLaneRUnits() {
        return singleLaneRUnits;
    }

    public void setSingleLaneRUnits(Set<RUnit> singleLaneRUnits) {
        this.singleLaneRUnits = singleLaneRUnits;
    }

    public Set<RUnit> getDoubleLaneRUnits() {
        return doubleLaneRUnits;
    }

    public void setDoubleLaneRUnits(Set<RUnit> doubleLaneRUnits) {
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

    public RoadNetworkManager getRoadNetworkManager() {
        return roadNetworkManager;
    }

    public void setRoadNetworkManager(RoadNetworkManager roadNetworkManager) {
        this.roadNetworkManager = roadNetworkManager;
    }

    public SimEngine getSimEngine() {
        return simEngine;
    }

    public void setSimEngine(SimEngine simEngine) {
        this.simEngine = simEngine;
    }
}