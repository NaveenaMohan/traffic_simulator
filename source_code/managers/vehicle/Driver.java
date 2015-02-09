package managers.vehicle;

import managers.globalconfig.DriverBehavior;

/**
 * Created by naveena on 09/02/15.
 */
public class Driver {

    private double speedOffset;
    private double visibilityOffset;
    private double reactionTimeOffset;
    private DriverBehavior driverBehavior;

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

    public DriverBehavior getDriverBehavior() {
        return driverBehavior;
    }

    public void setDriverBehavior(DriverBehavior driverBehavior) {
        this.driverBehavior = driverBehavior;
    }
}
