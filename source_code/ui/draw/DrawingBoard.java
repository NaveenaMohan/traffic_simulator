package ui.draw;

import engine.SimEngine;
import managers.roadnetwork.RoadNetworkManager;
import managers.runit.RUnit;
import managers.space.ObjectInSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.List;

public class DrawingBoard extends JPanel implements ActionListener {
    BufferedImage bi;
    private int x, y;
    private List<Coordinates> coordinates;
    private RoadNetworkManager roadNetworkManager;
    private SimEngine simEngine;
    private RUnit previousRUnit;

    public DrawingBoard(List<Coordinates> coordinates, RoadNetworkManager roadNetworkManager, SimEngine simEngine) {
        this.coordinates = coordinates;
        this.roadNetworkManager = roadNetworkManager;
        this.simEngine = simEngine;
        setBackground(Color.white);
        setBounds(286, 79, 1021, 348);
        setLayout(null);
    }

    public void addMouseMotionListener() {
        addMouseMotionListener(new MouseMotionHandler());

        //TODO Change Image File Location
        Image image = getToolkit().getImage("C:/Users/Fabians/traffic_simulator/source_code/road.png");

        MediaTracker mt = new MediaTracker(this);
        mt.addImage(image, 1);
        try {
            mt.waitForAll();
        } catch (Exception e) {
            System.out.println("Exception while loading image.");
        }

        if (image.getWidth(this) == -1) {
            System.out.println("no gif file");
            System.exit(0);
        }

        bi = new BufferedImage(image.getWidth(this), image.getHeight(this),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D big = bi.createGraphics();
        big.drawImage(image, 0, 0, this); // TODO : Remove the initial square
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        //Drawing the road
        for (Coordinates coordinate : coordinates) {
            g2D.drawImage(bi, coordinate.getX(), coordinate.getY(), this);
        }
        g2D.drawImage(bi, x, y, this);
        coordinates.add(new Coordinates(x, y));
        //Add and Return RUnit for single lane and store it as previous RUnit TODO : Change storage of previous RUnit
        previousRUnit = roadNetworkManager.addSingleLane(x, y, previousRUnit);
    }

    public void paint(Graphics g) {
        super.paint(g);
        //Move Vehicles on RUnits on UI

        Graphics2D g2d = (Graphics2D) g;
        Image image = getToolkit().getImage("C:/Users/Fabians/traffic_simulator/source_code/car.png");
        for (ObjectInSpace objectInSpace : simEngine.getDataAndStructures().getSpaceManager().getObjects()) {
            g2d.drawImage(image, objectInSpace.getX(), objectInSpace.getY(), this);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simEngine.performAction();
        repaint();
    }


    class MouseMotionHandler extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            repaint();
        }
    }

}