package ui.fileops;

import dataAndStructures.IDataAndStructures;
import managers.space.ObjectInSpace;
import managers.vehicle.IVehicleManager;
import ui.components.DrawingBoard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by naveena on 15/03/15.
 */
public class ExportImportObject implements Serializable {
    private IDataAndStructures engineDataStructures;
    private UIDataStructures uiDataStructures = new UIDataStructures();

    public ExportImportObject(IDataAndStructures dataAndStructures, DrawingBoard drawingBoard) {
        engineDataStructures = dataAndStructures;
        engineDataStructures.setVehicleManagerList((new ArrayList<IVehicleManager>()));
        engineDataStructures.getSpaceManager().setObjects(new ArrayList<ObjectInSpace>());


        uiDataStructures.setSingleLaneRUnits(drawingBoard.getSingleLaneRUnits());
        uiDataStructures.setDoubleLaneRUnits(drawingBoard.getDoubleLaneRUnits());
        uiDataStructures.setTrafficLightCoordinates(drawingBoard.getTrafficLightCoordinates());
        uiDataStructures.setZebraCrossingCoordinates(drawingBoard.getZebraCrossingCoordinates());
        uiDataStructures.setBlockageCoordinates(drawingBoard.getBlockageCoordinates());
        uiDataStructures.setStopCoordinates(drawingBoard.getStopCoordinates());
        uiDataStructures.setVehicleFactoryCoordinates(drawingBoard.getVehicleFactoryCoordinates());
        uiDataStructures.setLeftCoordinates(drawingBoard.getLeftCoordinates());
        uiDataStructures.setRightCoordinates(drawingBoard.getRightCoordinates());
        uiDataStructures.setStraightCoordinates(drawingBoard.getStraightCoordinates());
        uiDataStructures.setSpeed20Coordinates(drawingBoard.getSpeed20Coordinates());
        uiDataStructures.setSpeed30Coordinates(drawingBoard.getSpeed30Coordinates());
        uiDataStructures.setSpeed50Coordinates(drawingBoard.getSpeed50Coordinates());
        uiDataStructures.setSpeed60Coordinates(drawingBoard.getSpeed60Coordinates());
        uiDataStructures.setSpeed70Coordinates(drawingBoard.getSpeed70Coordinates());
        uiDataStructures.setSpeed90Coordinates(drawingBoard.getSpeed90Coordinates());
        uiDataStructures.setWelcomeCoordinates(drawingBoard.getWelcomeCoordinates());
    }

    public IDataAndStructures getEngineDataStructures() {
        return engineDataStructures;
    }

    public void setEngineDataStructures(IDataAndStructures engineDataStructures) {
        this.engineDataStructures = engineDataStructures;
    }

    public UIDataStructures getUiDataStructures() {
        return uiDataStructures;
    }

    public void setUiDataStructures(UIDataStructures uiDataStructures) {
        this.uiDataStructures = uiDataStructures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportImportObject that = (ExportImportObject) o;

        if (engineDataStructures != null ? !engineDataStructures.equals(that.engineDataStructures) : that.engineDataStructures != null)
            return false;
        if (uiDataStructures != null ? !uiDataStructures.equals(that.uiDataStructures) : that.uiDataStructures != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = engineDataStructures != null ? engineDataStructures.hashCode() : 0;
        result = 31 * result + (uiDataStructures != null ? uiDataStructures.hashCode() : 0);
        return result;
    }
}
