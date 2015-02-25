package managers.vehicle;

import dataAndStructures.IDataAndStructures;
import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.IGlobalConfigManager;
import managers.globalconfig.VehicleType;
import managers.runit.IRUnitManager;
import managers.runit.RUnit;
import managers.space.ISpaceManager;
import managers.space.ObjectInSpace;
import managers.space.SpaceManager;

import javax.imageio.spi.IIOServiceProvider;

/**
 * Created by naveena on 08/02/15.
 */
public class Vehicle implements IVehicleManager {

    private int vehID;
    private IRUnitManager rUnit;
    private Driver driver;
    private VehicleType vehicleType;
    private String destination;
    private boolean arrivedToDestination;
    private ObjectInSpace objectInSpace;
    private double timeCreated;
    private VehicleMotor vehicleMotor;

    private double previousTime;

    public Vehicle(int vehID, RUnit rUnit, Driver driver, int maxSpeedLimit, VehicleType vehicleType, String destination
            , ObjectInSpace objectInSpace, double maxAcceleration, double maxDeceleration, double timeCreated) {
        this.vehID = vehID;
        this.rUnit = rUnit;
        this.driver = driver;
        this.vehicleType = vehicleType;
        this.destination = destination;
        this.objectInSpace = objectInSpace;
        this.timeCreated = timeCreated;
        previousTime=timeCreated;
        this.vehicleMotor = new VehicleMotor(maxAcceleration, maxDeceleration, maxSpeedLimit, vehID);
    }

    public IRUnitManager getrUnit() {
        return rUnit;
    }

    public void setrUnit(RUnit rUnit) {
        this.rUnit = rUnit;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public void move(ISpaceManager spaceManager, double time, IDataAndStructures dataAndStructures) {
        VehicleState vehicleState = new VehicleState();//create a disposable vehicle state object
        //this object gets destroyed and recreated on every move


        //perceive the world and update your state
        VehiclePerception.See(
                vehID,
                rUnit,//your position
                50, //rUnits of vision
                vehicleState,
                spaceManager,
                dataAndStructures,
                objectInSpace
        );

        //prepare the acceleration and rUnit position
        rUnit = vehicleMotor.PrepareAction(rUnit, driver, vehicleState, time-previousTime);

        //move
        rUnit = vehicleMotor.performAction(time-previousTime,dataAndStructures,spaceManager,rUnit,objectInSpace);

        //adjust your position in space
        objectInSpace.setX(rUnit.getX());
        objectInSpace.setY(rUnit.getY());


        previousTime= time;

    }

    @Override
    public boolean isVisible(int minX, int maxX, int minY, int maxY) {
        return false;
    }

    @Override
    public Vehicle getVehicle() {
        return this;
    }

    @Override
    public ObjectInSpace getObjectInSpace() {
        return objectInSpace;
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
}
