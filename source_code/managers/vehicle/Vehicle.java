package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.globalconfig.VehicleType;
import managers.runit.IRUnitManager;
import managers.runit.RUnit;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.space.VehicleDirection;

/**
 * Created by naveena on 08/02/15.
 */
public class Vehicle implements IVehicleManager {

    private int vehID;
    private IRUnitManager rUnit;
    private Driver driver;
    private VehicleMotor vehicleMotor;
    private VehicleState vehicleState;
    private VehiclePerception vehiclePerception;
    private VehicleType vehicleType;

    private double timeCreated;


    private double previousTime;

    public Vehicle(int vehID, RUnit rUnit, Driver driver, int initialSpeed, String destination
            , ObjectInSpace objectInSpace, double maxAcceleration, double maxDeceleration, double timeCreated, double maximumVelocity) {
        this.vehID = vehID;
        this.rUnit = rUnit;
        this.driver = driver;
        this.timeCreated = timeCreated;
        previousTime=timeCreated;

        this.vehicleMotor = new VehicleMotor(maxAcceleration, maxDeceleration, initialSpeed, destination, objectInSpace, maximumVelocity,
                (objectInSpace.getVehicleType()==VehicleType.emergency ? true : false));
        this. vehicleState = new VehicleState();
        this.vehiclePerception=new VehiclePerception();
    }

    public double getTimeCreated() {
        return timeCreated;
    }

    public VehicleMotor getVehicleMotor() {
        return vehicleMotor;
    }

    public IRUnitManager getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public VehicleDirection getDirection() {
        return vehicleMotor.getObjectInSpace().getDirection();
    }

    public String getDestination() {
        return vehicleMotor.getDestination();
    }

    public double getDepthInCurrentRUnit() {
        return vehicleMotor.getDepthInCurrentRUnit();
    }

    @Override
    public void move(ISpaceManager spaceManager, double time, IDataAndStructures dataAndStructures) {



        //perceive the world and update your state
        vehiclePerception.see(
                vehID,
                rUnit,//your position
                driver.getVision(100, dataAndStructures.getGlobalConfigManager().getClimaticCondition().getVisibility()), //rUnits of vision
                vehicleState,
                spaceManager,
                dataAndStructures,
                vehicleMotor.getObjectInSpace()
        );

        //prepare the acceleration and rUnit position
        rUnit = vehicleMotor.prepareAction(
                rUnit,
                driver,
                vehicleState,
                dataAndStructures.getGlobalConfigManager().getClimaticCondition().getSlipperiness(),
                spaceManager,
                dataAndStructures.getGlobalConfigManager().getCurrentSecond()
        );

        //move
        rUnit = vehicleMotor.performAction(time-previousTime,dataAndStructures,rUnit, vehicleState);

        //clear state objects
        vehicleState.cleanObjectsAhead();

       previousTime= time;

    }

    public String getCurrentStrategy(){return vehicleMotor.currentStrategy;}
    @Override
    public boolean isVisible(int minX, int maxX, int minY, int maxY) {
        return false;
    }

    public boolean getMadeDestination(){
        return vehicleMotor.isMadeDestination();
    }
    public double getArrivalDestTime(){
        return vehicleMotor.getArrivalDestTime();
    }

    @Override
    public Vehicle getVehicle() {
        return this;
    }

    @Override
    public ObjectInSpace getObjectInSpace() {
        return vehicleMotor.getObjectInSpace();
    }

    @Override
    public double getCurrentVelocity() {
        return vehicleMotor.getCurrentVelocity();
    }

    @Override
    public int getVehID() {
        return vehID;
    }

    @Override
    public double getCurrentAcceleration() { //ADDED BY LORENA TO TEST ACCELERATION IN THE REPORTS
        return vehicleMotor.getCurrentAcceleration();
    } //ADDED IN ORDER TO REPORT

    @Override
    public boolean isVisible() {
        return getObjectInSpace().isVisible();
    }
}
