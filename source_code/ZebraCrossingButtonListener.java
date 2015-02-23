import ui.ConfigButtonSelected;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by naveena on 18/02/15.
 */
public class ZebraCrossingButtonListener implements MouseListener {

    private DrawingBoard drawingBoard;

    public ZebraCrossingButtonListener(DrawingBoard drawingBoard) {
        this.drawingBoard = drawingBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.zebraCrossing);
        //Sets the current coordinates for paint function
        drawingBoard.setCurrentX(e.getX());
        drawingBoard.setCurrentY(e.getY());
        drawingBoard.repaint();
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