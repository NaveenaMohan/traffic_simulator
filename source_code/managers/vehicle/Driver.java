package managers.vehicle;

import managers.globalconfig.DriverBehaviorType;

import java.io.Serializable;

/**
 * Created by naveena on 09/02/15.
 */
public class Driver implements Serializable {

    private double speedOffset=1;
    private double visionOffset=1;
    private double reactionTimeOffset=1;
    private DriverBehaviorType driverBehaviorType= DriverBehaviorType.normal;

    public Driver(double speedOffset, double visionOffset, double reactionTimeOffset, DriverBehaviorType driverBehaviorType) {
        this.speedOffset = speedOffset;
        this.visionOffset = visionOffset;
        this.reactionTimeOffset = reactionTimeOffset;
        this.driverBehaviorType = driverBehaviorType;
    }

    public double getVision(double maxVision, double visibilityOffset)
    {
        //returns the vision ahead in metres
        return maxVision*visionOffset*(1-visibilityOffset);
    }

    public double getSpeed(double slipperinessOffset, double requiredSpeed)
    {
        return requiredSpeed*speedOffset*(1-slipperinessOffset);
    }

    public double getDecelerationSafeDistance
            (double currentVelocity, double requiredVelocity,
             double distance, double slipperinessOffset, VehicleMemoryObject obstacle)
    {
        return Math.max((currentVelocity - requiredVelocity)
                *(1-slipperinessOffset)*reactionTimeOffset, distance)+ getStopDistance(slipperinessOffset, obstacle);
    }

    public double getStopDistance(double slipperinessOffset, VehicleMemoryObject obstacle)
    {
        //if the obstacle is not passable - maintain a safe distance
        if(!obstacle.isPassable())
            switch(driverBehaviorType)
            {
                case reckless:
                    return 4*(1+slipperinessOffset);
                case normal:
                    return 5*(1+slipperinessOffset);
                case cautious:
                    return 6*(1+slipperinessOffset);
                default:
                    return 5*(1+slipperinessOffset);
            }
        else
            return 0;

    }
}
