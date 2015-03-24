package tests;

import managers.runit.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ui.ConfigButtonSelected;
import ui.Coordinates;
import ui.Traffic_Simulator;
import ui.components.DrawingBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawingBoardTest {

    private Traffic_Simulator window;
    private DrawingBoard drawingBoard;

    @Before
    public void setUp() throws Exception {
        window = new Traffic_Simulator();
        window.getTrafficSimulatorFrame().setVisible(true);
        drawingBoard = window.getDrawingBoard();
        drawingBoard.initializeAndLoadImages();
    }

    @Test
    public void testInitialize() {
        //Asserting whether couple of images were loaded
        Assert.assertNotNull(drawingBoard.getBufferedRoadImage());
        Assert.assertNotNull(drawingBoard.getCarImage());
        Assert.assertNotNull(drawingBoard.getSpeed20Image());
        Assert.assertNotNull(drawingBoard.getTrafficLightImage());
        Assert.assertNotNull(drawingBoard.getVehicleFactoryImage());
        Assert.assertNotNull(drawingBoard.getBlockageImage());
    }

    @Test
    public void testPaintComponentForSingleRoad() {
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addSingleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getSingleLaneRUnits().size(), 0);
    }

    @Test
    public void testPaintComponentForDoubleRoad() {
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getDoubleLaneRUnits().size(), 0);
        Assert.assertNotEquals(drawingBoard.getChangeAbleLaneRUnits().size(), 0);
    }

    @Test
    public void testPaintComponentTrafficLight() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a traffic light
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.trafficLight);
        drawingBoard.getModel().addRow(new Object[]{"TL-1", false, true, false, true, false, false, true, false, true, false});
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getTrafficLightCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                Assert.assertNotNull(rUnit.getTrafficLight());
            }
        }
        TrafficLight trafficLight = drawingBoard.getRoadNetworkManager().getRoadNetwork().getTrafficLightHashtable().get("TL-1");
        Assert.assertEquals(trafficLight.getCycle(), Arrays.asList(false, false, false, false, false, false, false, false, false, false));
    }

    @Test
    public void testPaintComponentZebraCrossing() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.getModel().addRow(new Object[]{"ZTL-1", false, true, false, true, false, false, true, false, true, false});
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a zebra crossing
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.zebraCrossing);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getZebraCrossingCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                Assert.assertNotNull(rUnit.getZebraCrossing());
            }
        }
        TrafficLight trafficLight = drawingBoard.getRoadNetworkManager().getRoadNetwork().getTrafficLightHashtable().get("ZTL-1");
        Assert.assertEquals(trafficLight.getCycle(), Arrays.asList(false, false, false, false, false, false, false, false, false, false));

    }

    @Test
    public void testPaintComponentBlockage() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a blockage
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.blockage);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getBlockageCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                Assert.assertNotNull(rUnit.getBlockage());
            }
        }
    }

    @Test
    public void testPaintComponentVehicleFactory() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a vehicle factory
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.vehicleFactory);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getVehicleFactoryCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getSimEngine().getDataAndStructures().getVehicleFactoryManager().getVehicleFactoryList().get(0).getrUnit().getX(), 100);
        Assert.assertEquals(drawingBoard.getSimEngine().getDataAndStructures().getVehicleFactoryManager().getVehicleFactoryList().get(0).getrUnit().getY(), 150);
    }

    @Test
    public void testPaintComponentStop() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a stop
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.stop);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getStopCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof StopSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
            }
        }
    }

    @Test
    public void testPaintComponentLeft() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a left sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.left);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getLeftCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof DirectionSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                DirectionSign directionSign = (DirectionSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getDirectionSignType(), DirectionSignType.left);
            }
        }
    }

    @Test
    public void testPaintComponentRight() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a right sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.right);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getRightCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof DirectionSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                DirectionSign directionSign = (DirectionSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getDirectionSignType(), DirectionSignType.right);
            }
        }
    }

    @Test
    public void testPaintComponentStraight() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a straight sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.straight);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getStraightCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof DirectionSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                DirectionSign directionSign = (DirectionSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getDirectionSignType(), DirectionSignType.straight);
            }
        }
    }

    @Test
    public void testPaintComponentWelcome() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a stop
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.welcome);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getWelcomeCoordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof WelcomeSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
            }
        }
    }

    @Test
    public void testPaintComponentSpeed20() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a speed20 sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.speed20);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getSpeed20Coordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof SpeedLimitSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                SpeedLimitSign directionSign = (SpeedLimitSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getSpeedLimit(), 20);
            }
        }
    }

    @Test
    public void testPaintComponentSpeed30() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a speed30 sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.speed30);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getSpeed30Coordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof SpeedLimitSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                SpeedLimitSign directionSign = (SpeedLimitSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getSpeedLimit(), 30);
            }
        }
    }

    @Test
    public void testPaintComponentSpeed50() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a speed50 sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.speed50);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getSpeed50Coordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof SpeedLimitSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                SpeedLimitSign directionSign = (SpeedLimitSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getSpeedLimit(), 50);
            }
        }
    }

    @Test
    public void testPaintComponentSpeed60() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a speed60 sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.speed60);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getSpeed60Coordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof SpeedLimitSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                SpeedLimitSign directionSign = (SpeedLimitSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getSpeedLimit(), 60);
            }
        }
    }

    @Test
    public void testPaintComponentSpeed70() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a speed70 sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.speed70);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getSpeed70Coordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof SpeedLimitSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                SpeedLimitSign directionSign = (SpeedLimitSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getSpeedLimit(), 70);
            }
        }
    }

    @Test
    public void testPaintComponentSpeed90() {
        //Draw a double Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        //Configure a speed90 sign
        drawingBoard.setDraw(false);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.speed90);
        drawingBoard.paintComponent(graphics2D);
        drawingBoard.paint(graphics2D);
        Assert.assertNotEquals(drawingBoard.getSpeed90Coordinates().size(), 0);
        for (IRUnitManager rUnit : drawingBoard.getSingleLaneRUnits()) {
            if (rUnit.getX() == 100 && rUnit.getY() == 150) {
                boolean checkSign = false;
                if (rUnit.getTrafficSign() instanceof SpeedLimitSign) {
                    checkSign = true;
                }
                Assert.assertTrue(checkSign);
                SpeedLimitSign directionSign = (SpeedLimitSign) rUnit.getTrafficSign();
                Assert.assertEquals(directionSign.getSpeedLimit(), 90);
            }
        }
    }

    @Test
    public void testBestMatchRUnit() {
        //Draw a single Lane
        Graphics2D graphics2D = (Graphics2D) drawingBoard.getDrawingBoardPanel().getGraphics();
        drawingBoard.setDraw(true);
        drawingBoard.setPreviousRUnit(null);
        drawingBoard.setCurrentX(100);
        drawingBoard.setCurrentY(150);
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addSingleLane);
        drawingBoard.paintComponent(graphics2D);

        IRUnitManager bestMatchRunit = drawingBoard.fetchBestMatchRUnit();
        Assert.assertEquals(bestMatchRunit.getX(), 100);
        Assert.assertEquals(bestMatchRunit.getY(), 150);
        java.util.List<Coordinates> coordinatesList = new ArrayList<Coordinates>();
        bestMatchRunit = drawingBoard.fetchAndAddBestMatchRUnit(coordinatesList);
        Assert.assertEquals(bestMatchRunit.getX(), 100);
        Assert.assertEquals(bestMatchRunit.getY(), 150);
        Assert.assertTrue(coordinatesList.contains(new Coordinates(bestMatchRunit.getX(), bestMatchRunit.getY())));
    }

    @Test
    public void testGetRepositionedImageCoordinates() {
        Image testImage = drawingBoard.getWelcomeImage();
        int x = 100;
        int y = 150;
        x = x - testImage.getWidth(drawingBoard.getDrawingBoardPanel()) / 2;
        y = y - testImage.getHeight(drawingBoard.getDrawingBoardPanel()) / 2;
        Assert.assertEquals(drawingBoard.getRepositionedImageCoordinates(testImage, 100, 150), new Coordinates(x, y));
    }

    @Test
    public void testAddListeners() {
        drawingBoard.addSingleLaneMouseDragMotionListener();
        drawingBoard.addDoubleLaneMouseDragMotionListener();
        drawingBoard.addTrafficLightButtonListener();
        drawingBoard.addZebraCrossingButtonListener();
        drawingBoard.addBlockageButtonActionListener();
        drawingBoard.addVehicleFactoryButtonActionListener();
        drawingBoard.addStopButtonActionListener();
        drawingBoard.addLeftButtonActionListener();
        drawingBoard.addRightButtonActionListener();
        drawingBoard.addStraightButtonActionListener();
        drawingBoard.addSpeed20ButtonActionListener();
        drawingBoard.addSpeed30ButtonActionListener();
        drawingBoard.addSpeed50ButtonActionListener();
        drawingBoard.addSpeed60ButtonActionListener();
        drawingBoard.addSpeed70ButtonActionListener();
        drawingBoard.addSpeed90ButtonActionListener();
        drawingBoard.addWelcomeDestinationButtonActionListener();
        Assert.assertEquals(drawingBoard.getDrawingBoardPanel().getMouseListeners().length, 17);
        Assert.assertEquals(drawingBoard.getDrawingBoardPanel().getMouseMotionListeners().length, 2);
    }

    @Test
    public void testClean() {
        drawingBoard.clean();
        Assert.assertEquals(drawingBoard.getModel().getRowCount(), 0);
        Assert.assertEquals(drawingBoard.getSingleLaneRUnits().size(), 0);
        Assert.assertEquals(drawingBoard.getDoubleLaneRUnits().size(), 0);
        Assert.assertEquals(drawingBoard.getChangeAbleLaneRUnits().size(), 0);
        Assert.assertEquals(drawingBoard.getTrafficLightCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getZebraCrossingCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getBlockageCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getStopCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getLeftCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getRightCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getStraightCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getVehicleFactoryCoordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getSpeed20Coordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getSpeed30Coordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getSpeed50Coordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getSpeed60Coordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getSpeed70Coordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getSpeed90Coordinates().size(), 0);
        Assert.assertEquals(drawingBoard.getWelcomeCoordinates().size(), 0);

    }
}