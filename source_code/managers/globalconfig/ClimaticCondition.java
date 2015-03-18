package managers.globalconfig;

import java.io.Serializable;

/**
 * Created by naveena on 09/02/15.
 */
public class ClimaticCondition implements Serializable{

    private double visibility;
    private double slipperiness;

    public ClimaticCondition() {
        this.setSlipperiness(0.2); //if user did NOT customize Climatic Conditions, sets default 0.2 & 0.2
        this.setVisibility(0.2);
    }

    public void setWeatherType(WeatherType weatherType) {
        switch (weatherType){
            //0 is not slippery
            //0 means very good visibility

            case rainy: this.setSlipperiness(0.5);
                this.setVisibility(0.5);
                break;
            case snowy: this.setSlipperiness(0.9);
                this.setVisibility(0.7);
                break;
            case sunny: this.setSlipperiness(0);
                this.setVisibility(0);
                break;
        }
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getSlipperiness() {
        return slipperiness;
    }

    public void setSlipperiness(double slipperiness) {
        this.slipperiness = slipperiness;
    }
}

