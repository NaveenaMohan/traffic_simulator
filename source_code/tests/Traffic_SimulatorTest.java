package tests;

import dataAndStructures.DataAndStructures;
import dataAndStructures.IDataAndStructures;
import engine.SimEngine;
import managers.globalconfig.*;
import managers.roadnetwork.RoadNetwork;
import managers.roadnetwork.RoadNetworkManager;
import managers.vehiclefactory.VehicleFactoryManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import reports.DCP;
import ui.Traffic_Simulator;
import ui.components.DrawingBoard;
import ui.serialization.ExportImportObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Traffic_SimulatorTest {

    private Traffic_Simulator window;

    @Before
    public void setUp() throws Exception {
        window = new Traffic_Simulator();
        window.getTrafficSimulatorFrame().setVisible(true);
    }


    @Test
    public void testSliderValues() {
        Assert.assertEquals(window.getSlippery_slider().getValue(), (int) (window.getClimaticCondition().getVisibility() * 100.0));
        Assert.assertEquals(window.getVisibility_slider().getValue(), (int) (window.getClimaticCondition().getSlipperiness() * 100.0));
        Assert.assertEquals(window.getTrafficDensityRangeSlider().getUpperValue(), (int) ((window.getGlobalConfigManager().getVehicleDensity().getCarDensity() + window.getGlobalConfigManager().getVehicleDensity().getHeavyVehicleDensity()) * 100.0));
        Assert.assertEquals(window.getDriverBehaviourRangeSlider().getValue(), (int) (window.getGlobalConfigManager().getDriverBehaviour().getPercentageCautious() * 100.0));
        Assert.assertEquals(window.getDriverBehaviourRangeSlider().getUpperValue(), (int) ((window.getGlobalConfigManager().getDriverBehaviour().getPercentageCautious() + (window.getGlobalConfigManager().getDriverBehaviour().getPercentageNormal())) * 100.0));
        Assert.assertEquals(window.getVehicleProductionRateSlider().getValue(), (int) (window.getGlobalConfigManager().getVehicleDensity().getCreationRatePerSecond() * 10.0));
        Assert.assertEquals(window.getDestinationTxtBoxField().getText(), window.getGlobalConfigManager().getRoute().getDestination());
        Assert.assertEquals(window.getDestinationDensitySlider().getValue(), (int) (window.getGlobalConfigManager().getRoute().getTrafficPercent() * 100.0));
    }

    @Test
    public void testPopulateEngineAndUIDataStructuresForImport() {
        IDataAndStructures iDataAndStructures = new DataAndStructures(new RoadNetworkManager(new RoadNetwork()), new VehicleFactoryManager(),
                new GlobalConfigManager(1, 5.0, new ClimaticCondition(), new DriverBehavior(), new VehicleDensity(), new Route()));
        DCP dcp = new DCP(iDataAndStructures);
        SimEngine simEngine = new SimEngine(iDataAndStructures, dcp);
        DrawingBoard drawingBoard = new DrawingBoard(new DefaultTableModel(0, 11), iDataAndStructures.getRoadNetworkManager(), simEngine, new JLabel());
        ExportImportObject exportImportObject = new ExportImportObject(iDataAndStructures, drawingBoard);
        window.populateEngineAndUIDataStructuresForImport(exportImportObject);
        Assert.assertEquals(window.getRoadNetworkManager(), iDataAndStructures.getRoadNetworkManager());
        Assert.assertEquals(window.getVehicleFactoryManager(), iDataAndStructures.getVehicleFactoryManager());
        Assert.assertEquals(window.getClimaticCondition(), iDataAndStructures.getGlobalConfigManager().getClimaticCondition());
        Assert.assertEquals(window.getGlobalConfigManager(), iDataAndStructures.getGlobalConfigManager());
        Assert.assertEquals(window.getDataAndStructures(), iDataAndStructures);

        Assert.assertEquals(drawingBoard.getSingleLaneRUnits(), exportImportObject.getUiDataStructures().getSingleLaneRUnits());
        Assert.assertEquals(drawingBoard.getDoubleLaneRUnits(), exportImportObject.getUiDataStructures().getDoubleLaneRUnits());
        Assert.assertEquals(drawingBoard.getChangeAbleLaneRUnits(), exportImportObject.getUiDataStructures().getChangeableLaneRUnits());
        Assert.assertEquals(drawingBoard.getTrafficLightCoordinates(), exportImportObject.getUiDataStructures().getTrafficLightCoordinates());
        Assert.assertEquals(drawingBoard.getZebraCrossingCoordinates(), exportImportObject.getUiDataStructures().getZebraCrossingCoordinates());
        Assert.assertEquals(drawingBoard.getBlockageCoordinates(), exportImportObject.getUiDataStructures().getBlockageCoordinates());
        Assert.assertEquals(drawingBoard.getStopCoordinates(), exportImportObject.getUiDataStructures().getStopCoordinates());
        Assert.assertEquals(drawingBoard.getVehicleFactoryCoordinates(), exportImportObject.getUiDataStructures().getVehicleFactoryCoordinates());
        Assert.assertEquals(drawingBoard.getStraightCoordinates(), exportImportObject.getUiDataStructures().getStraightCoordinates());
        Assert.assertEquals(drawingBoard.getLeftCoordinates(), exportImportObject.getUiDataStructures().getLeftCoordinates());
        Assert.assertEquals(drawingBoard.getRightCoordinates(), exportImportObject.getUiDataStructures().getRightCoordinates());
        Assert.assertEquals(drawingBoard.getWelcomeCoordinates(), exportImportObject.getUiDataStructures().getWelcomeCoordinates());
        Assert.assertEquals(drawingBoard.getSpeed20Coordinates(), exportImportObject.getUiDataStructures().getSpeed20Coordinates());
        Assert.assertEquals(drawingBoard.getSpeed30Coordinates(), exportImportObject.getUiDataStructures().getSpeed30Coordinates());
        Assert.assertEquals(drawingBoard.getSpeed50Coordinates(), exportImportObject.getUiDataStructures().getSpeed50Coordinates());
        Assert.assertEquals(drawingBoard.getSpeed60Coordinates(), exportImportObject.getUiDataStructures().getSpeed60Coordinates());
        Assert.assertEquals(drawingBoard.getSpeed70Coordinates(), exportImportObject.getUiDataStructures().getSpeed70Coordinates());
        Assert.assertEquals(drawingBoard.getSpeed90Coordinates(), exportImportObject.getUiDataStructures().getSpeed90Coordinates());

        Assert.assertEquals(drawingBoard.getSimEngine(), simEngine);
        Assert.assertEquals(drawingBoard.getRoadNetworkManager(), iDataAndStructures.getRoadNetworkManager());

        Assert.assertEquals(window.getSlippery_slider().getValue(), (int) (window.getClimaticCondition().getVisibility() * 100.0));
        Assert.assertEquals(window.getVisibility_slider().getValue(), (int) (window.getClimaticCondition().getSlipperiness() * 100.0));
        Assert.assertEquals(window.getTrafficDensityRangeSlider().getUpperValue(), (int) ((window.getGlobalConfigManager().getVehicleDensity().getCarDensity() + window.getGlobalConfigManager().getVehicleDensity().getHeavyVehicleDensity()) * 100.0));
        Assert.assertEquals(window.getDriverBehaviourRangeSlider().getValue(), (int) (window.getGlobalConfigManager().getDriverBehaviour().getPercentageCautious() * 100.0));
        Assert.assertEquals(window.getDriverBehaviourRangeSlider().getUpperValue(), (int) ((window.getGlobalConfigManager().getDriverBehaviour().getPercentageCautious() + (window.getGlobalConfigManager().getDriverBehaviour().getPercentageNormal())) * 100.0));
        Assert.assertEquals(window.getVehicleProductionRateSlider().getValue(), (int) (window.getGlobalConfigManager().getVehicleDensity().getCreationRatePerSecond() * 10.0));
        Assert.assertEquals(window.getDestinationTxtBoxField().getText(), window.getGlobalConfigManager().getRoute().getDestination());
        Assert.assertEquals(window.getDestinationDensitySlider().getValue(), (int) (window.getGlobalConfigManager().getRoute().getTrafficPercent() * 100.0));

    }

}