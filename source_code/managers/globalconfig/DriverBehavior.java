package managers.globalconfig;

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
        this.percentageCautious = 0.33;

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

    public double getRecklessMinSpeedOffset() {
        return recklessMinSpeedOffset;
    }

    public double getRecklessMaxSpeedOffset() {
        return recklessMaxSpeedOffset;
    }

    public double getRecklessMinReactionTimeOffset() {
        return recklessMinReactionTimeOffset;
    }

    public double getRecklessMaxReactionTimeOffset() {
        return recklessMaxReactionTimeOffset;
    }

    public double getRecklessMinVisibilityOffset() {
        return recklessMinVisibilityOffset;
    }

    public double getRecklessMaxVisibilityOffset() {
        return recklessMaxVisibilityOffset;
    }

    public double getNormalMinSpeedOffset() {
        return normalMinSpeedOffset;
    }

    public double getNormalMaxSpeedOffset() {
        return normalMaxSpeedOffset;
    }

    public double getNormalMinReactionTimeOffset() {
        return normalMinReactionTimeOffset;
    }

    public double getNormalMaxReactionTimeOffset() {
        return normalMaxReactionTimeOffset;
    }

    public double getNormalMinVisibilityOffset() {
        return normalMinVisibilityOffset;
    }

    public double getNormalMaxVisibilityOffset() {
        return normalMaxVisibilityOffset;
    }

    public double getCautiousMinSpeedOffset() {
        return cautiousMinSpeedOffset;
    }

    public double getCautiousMaxSpeedOffset() {
        return cautiousMaxSpeedOffset;
    }

    public double getCautiousMinReactionTimeOffset() {
        return cautiousMinReactionTimeOffset;
    }

    public double getCautiousMaxReactionTimeOffset() {
        return cautiousMaxReactionTimeOffset;
    }

    public double getCautiousMinVisibilityOffset() {
        return cautiousMinVisibilityOffset;
    }

    public double getCautiousMaxVisibilityOffset() {
        return cautiousMaxVisibilityOffset;
    }
}
