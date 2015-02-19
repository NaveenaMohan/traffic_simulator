import engine.SimEngine;
import managers.roadnetwork.RoadNetworkManager;
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
import java.util.Arrays;
import java.util.List;

public class DrawingBoard extends JPanel implements ActionListener {
    private static int trafficLightIdIndex = 0;
    private static int zebraCrossingTrafficLightIdIndex = 0;
    BufferedImage bi;
    private int currentX, currentY;
    private List<Coordinates> singleLaneCoordinates = new ArrayList<Coordinates>();
    private List<Coordinates> trafficLightCoordinates = new ArrayList<Coordinates>();
    private List<Coordinates> zebraCrossingCoordinates = new ArrayList<Coordinates>();
    private List<Coordinates> blockageCoordinates = new ArrayList<Coordinates>();
    private RoadNetworkManager roadNetworkManager;
    private SimEngine simEngine;
    private RUnit previousRUnit;
    private Image rUnitImage;
    private Image trafficLightImage;
    private Image carImage;
    private Image zebraCrossingImage;
    private Image blockageImage;
    private ConfigButtonSelected configButtonSelected = ConfigButtonSelected.noOption;
    private boolean mousePressed;
    private DefaultTableModel model;


    public DrawingBoard(DefaultTableModel model, RoadNetworkManager roadNetworkManager, SimEngine simEngine) {
        this.model = model;
        this.roadNetworkManager = roadNetworkManager;
        this.simEngine = simEngine;
        setBackground(Color.white);
        setBounds(286, 79, 1021, 348);
        setLayout(null);
    }

    public void initialize() {
        rUnitImage = getToolkit().getImage(DrawingBoard.class.getResource("road.jpg"));
        trafficLightImage = getToolkit().getImage(DrawingBoard.class.getResource("lightMini.png"));
        carImage = getToolkit().getImage(DrawingBoard.class.getResource("car.png"));
        zebraCrossingImage = getToolkit().getImage(DrawingBoard.class.getResource("zebraCrossingMini.png"));
        blockageImage = getToolkit().getImage(DrawingBoard.class.getResource("blockMini.png"));
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
            //Drawing the road
            for (Coordinates coordinate : singleLaneCoordinates) {
                g2D.drawImage(bi, coordinate.getX(), coordinate.getY(), this);
            }
        }

        if (configButtonSelected.equals(ConfigButtonSelected.trafficLight)) {
            RUnit bestMatchRUnit = fetchBestMatchRunit(trafficLightCoordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the traffic Light
                //TODO : remove the hard coding of traffic light and its cycle
                TrafficLight trafficLight = new TrafficLight();
                ArrayList<Boolean> cycle = new ArrayList<Boolean>(Arrays.asList(true, false, true, false, true, true, true, true, true, true));
                trafficLight.setCycle(cycle);
                trafficLight.setTrafficLightID(1);
                simEngine.getDataAndStructures().getRoadNetworkManager().addTrafficLight(bestMatchRUnit, trafficLight);
                //Drawing the traffic Light and adding the traffic light cycle configuration
                g.drawImage(trafficLightImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                if (model != null) {
                    if (trafficLightIdIndex == 0) {
                        trafficLightIdIndex++;
                    } else {
                        model.addRow(new Object[]{"TL-" + trafficLightIdIndex, false, false, false, false, false, false, false, false, false, false});
                        trafficLightIdIndex++;
                    }
                }
            }
        }

        if (configButtonSelected.equals(ConfigButtonSelected.zebraCrossing)) {
            RUnit bestMatchRUnit = fetchBestMatchRunit(zebraCrossingCoordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the zebra crossing
                simEngine.getDataAndStructures().getRoadNetworkManager().addZebraCrossing(bestMatchRUnit);
                //Drawing the zebra crossing with traffic light configuration
                g.drawImage(zebraCrossingImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
                if (model != null) {
                    if (zebraCrossingTrafficLightIdIndex == 0) {
                        zebraCrossingTrafficLightIdIndex++;
                    } else {
                        model.addRow(new Object[]{"ZTL-" + zebraCrossingTrafficLightIdIndex, false, false, false, false, false, false, false, false, false, false});
                        zebraCrossingTrafficLightIdIndex++;
                    }
                }
            }
        }

        if (configButtonSelected.equals(ConfigButtonSelected.blockage)) {
            RUnit bestMatchRUnit = fetchBestMatchRunit(blockageCoordinates);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Updating the best match rUnit with the blockage
                simEngine.getDataAndStructures().getRoadNetworkManager().addBlockage(bestMatchRUnit);
                //Drawing the blockage
                g.drawImage(blockageImage, bestMatchRUnit.getX(), bestMatchRUnit.getY(), 5, 5, this);
            }
        }


        if (configButtonSelected.equals(ConfigButtonSelected.vehicleFactory)) {
            RUnit bestMatchRUnit = fetchBestMatchRunit(null);
            if (bestMatchRUnit == null) {
                System.out.println("No matching Runit"); //TODO : Dialog Box
            } else {
                //Adding vehicle factory and dataStructures
                simEngine.getDataAndStructures().getVehicleFactoryManager().addVehicleFactory(roadNetworkManager.getRoadNetwork().getrUnitHashtable().get(String.valueOf(bestMatchRUnit.getId())));
                //simEngine.getDataAndStructures().getRoadNetworkManager().addVehicleFactory(bestMatchRUnit);
            }
        }
    }

    private RUnit fetchBestMatchRunit(List<Coordinates> coordinates) {
        for (RUnit rUnit : simEngine.getDataAndStructures().getRoadNetworkManager().getRoadNetwork().getrUnitHashtable().values()) {
            Rectangle rectangle = new Rectangle(rUnit.getX(), rUnit.getY(), rUnitImage.getWidth(this), rUnitImage.getHeight(this));
            if (rectangle.contains(currentX, currentY)) {
                if (coordinates != null) {
                    coordinates.add(new Coordinates(rUnit.getX(), rUnit.getY()));
                }
                if (roadNetworkManager.addZebraCrossing(rUnit)) {
                    return rUnit;
                } else {
                    return null;
                }

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
        for (Coordinates coordinate : trafficLightCoordinates) {
            g2D.drawImage(trafficLightImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing zebra crossing
        for (Coordinates coordinate : zebraCrossingCoordinates) {
            g2D.drawImage(zebraCrossingImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Drawing blockages
        for (Coordinates coordinate : blockageCoordinates) {
            g2D.drawImage(blockageImage, coordinate.getX(), coordinate.getY(), this);
        }

        //Move Vehicles on RUnits on UI

        Graphics2D g2d = (Graphics2D) g;
        for (ObjectInSpace objectInSpace : simEngine.getDataAndStructures().getSpaceManager().getObjects()) {
            g2d.drawImage(carImage, objectInSpace.getX(), objectInSpace.getY(), this);
        }

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

    public void addBlockageButtonActionListener() {
        addMouseListener(new BlockageButtonListener(this));
    }

    public void addVehicleFactoryButtonActionListener() {
        addMouseListener(new VehicleFactoryButtonListener(this));
    }
}