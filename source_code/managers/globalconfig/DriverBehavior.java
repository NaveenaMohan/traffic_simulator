package managers.globalconfig;

import common.Common;
import managers.vehicle.Driver;

import java.io.Serializable;

/**
 * Created by naveena on 09/02/15.
 */
public class DriverBehavior implements Serializable {

    private double percentageReckless;
    private double percentageNormal;
    private double percentageCautious;

    private double recklessMinSpeedOffset;
    private double recklessMaxSpeedOffset;
    private double recklessMinReactionTimeOffset;
    private double recklessMaxReactionTimeOffset;
    private double recklessMinVisibilityOffset;
    private double recklessMaxVisibilityOffset;

    private double normalMinSpeedOffset;
    private double normalMaxSpeedOffset;
    private double normalMinReactionTimeOffset;
    private double normalMaxReactionTimeOffset;
    private double normalMinVisibilityOffset;
    private double normalMaxVisibilityOffset;

    private double cautiousMinSpeedOffset;
    private double cautiousMaxSpeedOffset;
    private double cautiousMinReactionTimeOffset;
    private double cautiousMaxReactionTimeOffset;
    private double cautiousMinVisibilityOffset;
    private double cautiousMaxVisibilityOffset;

    public DriverBehavior(){
        this.percentageReckless = 0.33;
        this.percentageNormal = 0.33;
        this.percentageCautious = 0.34;

        this.recklessMinSpeedOffset = 1.01;
        this.recklessMaxSpeedOffset = 1.02;
        this.recklessMinReactionTimeOffset = 0.95;
        this.recklessMaxReactionTimeOffset = 0.99;
        this.recklessMinVisibilityOffset = 0.95;
        this.recklessMaxVisibilityOffset = 0.99;

        this.normalMinSpeedOffset = 0.99;
        this.normalMaxSpeedOffset = 1.01;
        this.normalMinReactionTimeOffset = 0.99;
        this.normalMaxReactionTimeOffset = 1.01;
        this.normalMinVisibilityOffset = 0.99;
        this.normalMaxVisibilityOffset = 1.01;

        this.cautiousMinSpeedOffset = 0.97;
        this.cautiousMaxSpeedOffset = 0.99;
        this.cautiousMinReactionTimeOffset = 1.01;
        this.cautiousMaxReactionTimeOffset = 1.02;
        this.cautiousMinVisibilityOffset = 1;
        this.cautiousMaxVisibilityOffset = 1;
    }

    public void setPercentageReckless(double percentageReckless) {
        this.percentageReckless = percentageReckless;
    }

    public void setPercentageNormal(double percentageNormal) {
        this.percentageNormal = percentageNormal;
    }

    public void setPercentageCautious(double percentageCautious) {
        this.percentageCautious = percentageCautious;
    }

    public double getPercentageReckless() {
        return percentageReckless;
    }

    public double getPercentageNormal() {
        return percentageNormal;
    }

    public double getPercentageCautious() {
        return percentageCautious;
    }

    public double getRandomSpeedOffsetForDriverType(DriverBehaviorType driverBehaviorType)
    {
        switch(driverBehaviorType)
        {
            case normal:
                return Common.randDoubleBetween(normalMinSpeedOffset, normalMaxSpeedOffset);
            case cautious:
                return Common.randDoubleBetween(cautiousMinSpeedOffset, cautiousMaxSpeedOffset);
            case reckless:
                return Common.randDoubleBetween(recklessMinSpeedOffset, recklessMaxSpeedOffset);
            default:
                return 1;
        }
    }

    public double getRandomVisibilityOffsetForDriverType(DriverBehaviorType driverBehaviorType)
    {
        switch(driverBehaviorType)
        {
            case normal:
                return Common.randDoubleBetween(normalMinVisibilityOffset, normalMaxVisibilityOffset);
            case cautious:
                return Common.randDoubleBetween(cautiousMinVisibilityOffset, cautiousMaxVisibilityOffset);
            case reckless:
                return Common.randDoubleBetween(recklessMinVisibilityOffset, recklessMaxVisibilityOffset);
            default:
                return 1;
        }
    }

    public double getRandomReactionTimeOffsetForDriverType(DriverBehaviorType driverBehaviorType)
    {
        switch(driverBehaviorType)
        {
            case normal:
                return Common.randDoubleBetween(normalMinReactionTimeOffset, normalMaxReactionTimeOffset);
            case cautious:
                return Common.randDoubleBetween(cautiousMinReactionTimeOffset, cautiousMaxReactionTimeOffset);
            case reckless:
                return Common.randDoubleBetween(recklessMinReactionTimeOffset, recklessMaxReactionTimeOffset);
            default:
                return 1;
        }
    }
}
