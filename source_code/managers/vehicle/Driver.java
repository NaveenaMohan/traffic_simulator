package managers.vehicle;

import managers.globalconfig.DriverBehavior;
import managers.globalconfig.DriverBehaviorType;

/**
 * Created by naveena on 09/02/15.
 */
public class Driver {

    private double speedOffset=1;
    private double visibilityOffset=1;
    private double reactionTimeOffset=1;
    private DriverBehaviorType driverBehaviorType= DriverBehaviorType.normal;

    public Driver(double speedOffset, double visibilityOffset, double reactionTimeOffset, DriverBehaviorType driverBehaviorType) {
        this.speedOffset = speedOffset;
        this.visibilityOffset = visibilityOffset;
        this.reactionTimeOffset = reactionTimeOffset;
        this.driverBehaviorType = driverBehaviorType;
    }

    public double getSpeedOffset() {
        return speedOffset;
    }

    public void setSpeedOffset(double speedOffset) {
        this.speedOffset = speedOffset;
    }

    public double getVisibilityOffset() {
        return visibilityOffset;
    }

    public void setVisibilityOffset(double visibilityOffset) {
        this.visibilityOffset = visibilityOffset;
    }

    public double getReactionTimeOffset() {
        return reactionTimeOffset;
    }

    public void setReactionTimeOffset(double reactionTimeOffset) {
        this.reactionTimeOffset = reactionTimeOffset;
    }

    public DriverBehaviorType getDriverBehaviorType() {
        return driverBehaviorType;
    }

    public void setDriverBehaviorType(DriverBehaviorType driverBehaviorType) {
        this.driverBehaviorType = driverBehaviorType;
    }
}
