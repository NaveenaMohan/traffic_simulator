package managers.runit;

/**
 * Created by naveena on 08/02/15.
 */
public class TrafficLight {

    private boolean isRed;
    private TrafficLightSynchronisation trafficLightSynchronisation;

    public TrafficLightSynchronisation getTrafficLightSynchronisation() {
        return trafficLightSynchronisation;
    }

    public void setTrafficLightSynchronisation(TrafficLightSynchronisation trafficLightSynchronisation) {
        this.trafficLightSynchronisation = trafficLightSynchronisation;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean isRed) {
        this.isRed = isRed;
    }
}
