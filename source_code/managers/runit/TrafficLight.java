package managers.runit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public class TrafficLight {

    private List<Boolean> cycle = new ArrayList<Boolean>();
    private String trafficLightID; // Should be string as its prefixed with TL or ZTL
    private boolean trafficLightCurrentColor=false;
    private boolean isGreen=false;
    private TrafficLightSynchronisation trafficLightSynchronisation;

    public boolean getTrafficLightCurrentColor() {
        return trafficLightCurrentColor;
    }

    public void setTrafficLightCurrentColor(boolean trafficLightCurrentColor) {
        this.trafficLightCurrentColor = trafficLightCurrentColor;
        if (trafficLightCurrentColor)//if the current color equals true the TL is green, false is red
            setGreen(true);
        else setGreen(false);
    }

    public List<Boolean> getCycle() {
        return cycle;
    }

    public void setCycle(List<Boolean> cycle) {
        this.cycle = cycle;
    }

    public String getTrafficLightID() {
        return trafficLightID;
    }

    public void setTrafficLightID(String trafficLightID) {
        this.trafficLightID = trafficLightID;
    }

    public TrafficLightSynchronisation getTrafficLightSynchronisation() {
        return trafficLightSynchronisation;
    }

    public void setTrafficLightSynchronisation(TrafficLightSynchronisation trafficLightSynchronisation) {
        this.trafficLightSynchronisation = trafficLightSynchronisation;
    }

    public boolean isGreen() {
        return isGreen;
    }

    private void setGreen(boolean isGreen) {
        this.isGreen = isGreen;
    }
}
