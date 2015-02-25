package managers.globalconfig;

import common.Common;
import managers.vehicle.Driver;

/**
 * Created by naveena on 09/02/15.
 */
public class DriverBehavior {

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
        //Range taken:
        //0.3 - 0.7
        //0.8 - 1.2
        //1.3 - 1.7
        this.percentageReckless = 0.33;
        this.percentageNormal = 0.33;
        this.percentageCautious = 0.34;

        this.recklessMinSpeedOffset = 1.3;
        this.recklessMaxSpeedOffset = 1.7;
        this.recklessMinReactionTimeOffset = 0.3;//reckless decreases RUnits to look ahead when stopping
        this.recklessMaxReactionTimeOffset = 0.7;
        this.recklessMinVisibilityOffset = 0.3;
        this.recklessMaxVisibilityOffset = 0.7;

        this.normalMinSpeedOffset = 0.8;
        this.normalMaxSpeedOffset = 1.2;
        this.normalMinReactionTimeOffset = 0.8;
        this.normalMaxReactionTimeOffset = 1.2;
        this.normalMinVisibilityOffset = 0.8;
        this.normalMaxVisibilityOffset = 1.2;

        this.cautiousMinSpeedOffset = 0.3;
        this.cautiousMaxSpeedOffset = 0.7;
        this.cautiousMinReactionTimeOffset = 1.3;
        this.cautiousMaxReactionTimeOffset = 1.7;
        this.cautiousMinVisibilityOffset = 1.3;
        this.cautiousMaxVisibilityOffset = 1.7;
    }

    public void setPercentageReckless(double percentageReckless) {
        if(percentageCautious+percentageNormal+percentageReckless==1){
        }else throw new IllegalArgumentException("The sum of driver percentages is not equal to 1");
        this.percentageReckless = percentageReckless;
    }

    public void setPercentageNormal(double percentageNormal) {
        if(percentageCautious+percentageNormal+percentageReckless==1){
        }else throw new IllegalArgumentException("The sum of driver percentages is not equal to 1");
        this.percentageNormal = percentageNormal;
    }

    public void setPercentageCautious(double percentageCautious) {
        if(percentageCautious+percentageNormal+percentageReckless==1){
        }else throw new IllegalArgumentException("The sum of driver percentages is not equal to 1");
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
