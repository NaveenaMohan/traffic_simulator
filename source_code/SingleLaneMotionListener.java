import ui.ConfigButtonSelected;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by naveena on 18/02/15.
 */
public class SingleLaneMotionListener implements MouseListener {

    private DrawingBoard drawingBoard;

    public SingleLaneMotionListener(DrawingBoard drawingBoard) {
        this.drawingBoard = drawingBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addSingleLane);
        drawingBoard.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drawingBoard.setConfigButtonSelected(ConfigButtonSelected.addSingleLane);
        drawingBoard.setMousePressed(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
