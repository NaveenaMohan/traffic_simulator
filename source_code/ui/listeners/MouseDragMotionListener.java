package ui.listeners;

import ui.components.DrawingBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by naveena on 18/02/15.
 */
public class MouseDragMotionListener extends MouseMotionAdapter {
    private DrawingBoard drawingBoard;

    public MouseDragMotionListener(DrawingBoard drawingBoard) {
        this.drawingBoard = drawingBoard;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(drawingBoard.isDraw()){
            //Sets the current coordinates for paint function
            drawingBoard.setCurrentX(e.getX());
            drawingBoard.setCurrentY(e.getY());
            drawingBoard.repaint();
        }
    }

}
