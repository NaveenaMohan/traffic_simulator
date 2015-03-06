
import ui.ConfigButtonSelected;

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
        drawingBoard.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.noOption);
        drawingBoard.setMousePressed(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
