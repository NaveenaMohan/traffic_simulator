package tests;

import dataAndStructures.DataAndStructures;
import dataAndStructures.IDataAndStructures;
import engine.SimEngine;
import managers.globalconfig.*;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehiclefactory.VehicleFactoryManager;
import org.junit.Assert;
import org.junit.Test;
import reports.DCP;
import ui.components.DrawingBoard;
import ui.serialization.ExportImportObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExportImportObjectTest {

    @Test
    public void testExportImportObjectConstructor() {
        IDataAndStructures iDataAndStructures = new DataAndStructures(new RoadNetworkManager(new RoadNetwork()), new VehicleFactoryManager(),
                new GlobalConfigManager(1, 5.0, new ClimaticCondition(), new DriverBehavior(), new VehicleDensity(), new Route()));
        DrawingBoard drawingBoard = new DrawingBoard(new DefaultTableModel(0, 11), iDataAndStructures.getRoadNetworkManager(), new SimEngine(iDataAndStructures, new DCP(iDataAndStructures)), new JLabel());
        ExportImportObject exportImportObject = new ExportImportObject(iDataAndStructures, drawingBoard);
        Assert.assertEquals(exportImportObject.getEngineDataStructures(), iDataAndStructures);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getSingleLaneRUnits().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getDoubleLaneRUnits().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getChangeableLaneRUnits().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getTrafficLightCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getZebraCrossingCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getBlockageCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getStopCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getVehicleFactoryCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getStraightCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getLeftCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getRightCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getWelcomeCoordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getSpeed20Coordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getSpeed30Coordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getSpeed50Coordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getSpeed60Coordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getSpeed70Coordinates().size(), 0);
        Assert.assertEquals(exportImportObject.getUiDataStructures().getSpeed90Coordinates().size(), 0);
    }

}