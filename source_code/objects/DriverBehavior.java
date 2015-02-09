package objects;

/**
 * Created by naveena on 09/02/15.
 */
public class DriverBehavior {

    private DriverBehaviorType driverBehaviorType;
    private double minSpeedOffset;
    private double maxSpeedOffset;
    private double minReactionTimeOffset;
    private double maxReactionTimeOffset;
    private double minVisibilityOffset;
    private double maxVisibilityOffset;

    public DriverBehaviorType getDriverBehaviorType() {
        return driverBehaviorType;
    }

    public void setDriverBehaviorType(DriverBehaviorType driverBehaviorType) {
        this.driverBehaviorType = driverBehaviorType;
    }

    public double getMinSpeedOffset() {
        return minSpeedOffset;
    }

    public void setMinSpeedOffset(double minSpeedOffset) {
        this.minSpeedOffset = minSpeedOffset;
    }

    public double getMaxSpeedOffset() {
        return maxSpeedOffset;
    }

    public void setMaxSpeedOffset(double maxSpeedOffset) {
        this.maxSpeedOffset = maxSpeedOffset;
    }

    public double getMinReactionTimeOffset() {
        return minReactionTimeOffset;
    }

    public void setMinReactionTimeOffset(double minReactionTimeOffset) {
        this.minReactionTimeOffset = minReactionTimeOffset;
    }

    public double getMaxReactionTimeOffset() {
        return maxReactionTimeOffset;
    }

    public void setMaxReactionTimeOffset(double maxReactionTimeOffset) {
        this.maxReactionTimeOffset = maxReactionTimeOffset;
    }

    public double getMinVisibilityOffset() {
        return minVisibilityOffset;
    }

    public void setMinVisibilityOffset(double minVisibilityOffset) {
        this.minVisibilityOffset = minVisibilityOffset;
    }

    public double getMaxVisibilityOffset() {
        return maxVisibilityOffset;
    }

    public void setMaxVisibilityOffset(double maxVisibilityOffset) {
        this.maxVisibilityOffset = maxVisibilityOffset;
    }
}
