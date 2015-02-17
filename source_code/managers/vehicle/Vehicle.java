package managers.vehicle;

import managers.globalconfig.ClimaticCondition;
import managers.globalconfig.IGlobalConfigManager;
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
    private RUnit rUnit;
    private Driver driver;
    private int maxSpeedLimit;
    private VehicleType vehicleType;
    private String destination;
    private ObjectInSpace objectInSpace;
    private double timeCreated;

    private double maxAcceleration;//maximum acceleration the car can get
    private double maxDeceleration;
    private double currentVelocity;//current speed in metres
    private double currentAcceleration;
    private double depthInCurrentRUnit;//if the RUnit is say 100m long we need to keep track of how far in the vehicle is
    private double previousTime;
    private ObjectAhead objectAhead;

    public Vehicle(int vehID, RUnit rUnit, Driver driver, int maxSpeedLimit, VehicleType vehicleType, String destination
            , ObjectInSpace objectInSpace, double maxAcceleration, double timeCreated) {
        this.vehID = vehID;
        this.rUnit = rUnit;
        this.driver = driver;
        this.maxSpeedLimit = maxSpeedLimit;
        this.vehicleType = vehicleType;
        this.destination = destination;
        this.objectInSpace = objectInSpace;
        this.maxAcceleration = maxAcceleration;
        this.timeCreated=timeCreated;
        currentVelocity = 0;
        depthInCurrentRUnit = 0;
        maxDeceleration = -10;

    }

    public RUnit getrUnit() {
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

    public int getMaxSpeedLimit() {
        return maxSpeedLimit;
    }

    public void setMaxSpeedLimit(int maxSpeedLimit) {
        this.maxSpeedLimit = maxSpeedLimit;
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

    public void move(ISpaceManager spaceManager, double time, IGlobalConfigManager globalConfigManager) {

        System.out.println("-----Move veh: " + vehID);
        //adjust time
        time = time-timeCreated;

        //get Acceleration based on obstacles ahead and speed limits
        currentAcceleration = getNewAcceleration(globalConfigManager, spaceManager, time);//pass metres of vision to it

        //advance by transforming acceleration into speed, and rUnits travelled
        Advance(time, globalConfigManager, spaceManager);

        //update the previously taken time for acceleration purposes
        previousTime = time;

        System.out.println("t: " + time + " vID: " + vehID + " rUnit: " + rUnit.getId() + " v: " + currentVelocity + " depth: " + depthInCurrentRUnit
        + " a: " + currentAcceleration);

    }

    @Override
    public boolean isVisible(int minX, int maxX, int minY, int maxY) {
        return false;
    }

    @Override
    public Vehicle getVehicle() {
        return this;
    }

    private boolean isObstacle(RUnit unit, ISpaceManager spaceManager) {
        //check if you will fit in that rUnit


        //check for other cars
        if(!spaceManager.checkFit(objectInSpace))
            return true;

        //check for end of the road
        if(rUnit.getNextRUnitList().size()<0)
            return true;

        return false;
    }

    private double aimForSpeed(double requiredVelocity, double distance)//returns the acceleration
    {
        //this function gradually changes the vehicle speed to match the requiredSpeed in distance metres

        //to get the acceleration we will use a kinematic equation
        //finalVelocity^2=initialVelocity^2 + 2*acceleration*distance
        //Hence acceleration=(finalVelocity^2-initialVelocity^2)/2*distance

        double acceleration = (Math.sqrt(requiredVelocity) - Math.sqrt(currentVelocity)) / 2 * distance;

        return Math.max(Math.min(acceleration, maxAcceleration), maxDeceleration);
    }

    private double getVelocity(double acceleration, double time) {
        //velocity can't be below 0
        return Math.max(0,
                currentVelocity + acceleration * (time - previousTime));
    }

    private double getNewAcceleration(IGlobalConfigManager globalConfigManager, ISpaceManager spaceManager, double time) {
        //looks at obstacles ahead to give you a proposed acceleration to either slow down or speed up
        double proposedAcceleration = 0;


        if (rUnit.getNextRUnitList().size() > 0) {
            //check how many rUnits is vision metres
            int rUnitsVision = (int) (500 / globalConfigManager.getMetresPerRUnit());

            RUnit temprUnit = rUnit;

            //aim to reach the speed limit speed
            proposedAcceleration = aimForSpeed(maxSpeedLimit, 1);

            for (int i = 0; i < rUnitsVision; i++) {
                temprUnit = temprUnit.getNextRUnitList().get(0);
                if (isObstacle(temprUnit, spaceManager))//if there is an obstacle ahead
                {
                    System.out.println("Obstacle detected in " + i);
                    if (objectAhead == null)
                        objectAhead = new ObjectAhead(
                                (i + 1) * globalConfigManager.getMetresPerRUnit(),//distance to the obstacle
                                time//current time
                        );
                    else
                        objectAhead.recalculateSpeed(
                                currentVelocity,
                                (i + 1) * globalConfigManager.getMetresPerRUnit(),
                                time);

                    //aim to match the obstacle speed by the time you get to it
                    proposedAcceleration = aimForSpeed(objectAhead.getSpeed(), objectAhead.getDistance());

                    break;//break out of the loop, you have found an obstacle
                }

            }

        }

        return proposedAcceleration;
    }


    private void Advance(double time, IGlobalConfigManager globalConfigManager, ISpaceManager spaceManager) {
        //if there is where to go, this function advances your R unit and acceleration

        //get distance to be travelled in the timePassed with proposedVelocity
        double distanceTravelled = currentVelocity * (time - previousTime);

        //get the amount of rUnits to travel
        int RUnitsTravelled = (int) (Math.floor((distanceTravelled + depthInCurrentRUnit) / globalConfigManager.getMetresPerRUnit()));


        //update currentVelocity but only if you
        currentVelocity = getVelocity(currentAcceleration, time);

        //get the depth
        depthInCurrentRUnit = (distanceTravelled + depthInCurrentRUnit) % globalConfigManager.getMetresPerRUnit();;

        //travel the rUnits
        for (int i = 0; i < RUnitsTravelled; i++) {//go through as many metres as many you have travelled since last move
            if (rUnit.getNextRUnitList().size() > 0) {
                if (spaceManager.checkFit(vehID, rUnit.getX(), rUnit.getY(), objectInSpace.getWidth(), objectInSpace.getLength())) {
                    rUnit = rUnit.getNextRUnitList().get(0);
                } else {
                    //if you encounter an obstacle then at least you reach the end of the current RUnit
                    depthInCurrentRUnit = globalConfigManager.getMetresPerRUnit();
                    //you hit it so your velocity is now 0
                    currentVelocity = 0;
                }


            }
            break;

        }


        //adjust your position in space
        objectInSpace.setX(rUnit.getX());
        objectInSpace.setY(rUnit.getY());


    }


}
