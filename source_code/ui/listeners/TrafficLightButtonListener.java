package ui.listeners;

import ui.ConfigButtonSelected;
import ui.components.DrawingBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by naveena on 18/02/15.
 */
public class TrafficLightButtonListener implements MouseListener {

    private DrawingBoard drawingBoard;

    public TrafficLightButtonListener(DrawingBoard drawingBoard) {
        this.drawingBoard = drawingBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.trafficLight);
        //Sets the current coordinates for paint function
        drawingBoard.setCurrentX(e.getX());
        drawingBoard.setCurrentY(e.getY());
        drawingBoard.getDrawingBoardPanel().repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
