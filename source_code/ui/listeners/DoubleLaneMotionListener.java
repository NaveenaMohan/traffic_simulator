package ui.listeners;

import ui.ConfigButtonSelected;
import ui.components.DrawingBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by somya on 27/02/15.
 */

public class DoubleLaneMotionListener implements MouseListener {

    private DrawingBoard drawingBoard;

    public DoubleLaneMotionListener(DrawingBoard drawingBoard) {
        this.drawingBoard = drawingBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addDoubleLane);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.noOption);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
