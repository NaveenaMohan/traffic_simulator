package managers.runit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveena on 08/02/15.
 */
public class TrafficLight {

    private ArrayList<Boolean> cycle=new ArrayList<Boolean>();
    private int trafficLightID;
    private boolean trafficLightCurrentColor=false;
    private boolean isGreen=false;

    public boolean getTrafficLightCurrentColor() {
        return trafficLightCurrentColor;
    }

    public void setTrafficLightCurrentColor(boolean trafficLightCurrentColor) {
        this.trafficLightCurrentColor = trafficLightCurrentColor;
        if (trafficLightCurrentColor)//if the current color equals true the TL is green, false is red
            setGreen(true);
        else setGreen(false);
    }

    public ArrayList<Boolean> getCycle() {
        return cycle;
    }

    public void setCycle(ArrayList<Boolean> cycle) {
        this.cycle = cycle;
    }

    public int getTrafficLightID() {
        return trafficLightID;
    }

    public void setTrafficLightID(int trafficLightID) {
        this.trafficLightID = trafficLightID;
    }


    private TrafficLightSynchronisation trafficLightSynchronisation;

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
