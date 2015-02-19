package managers.runit;

/**
 * Created by naveena on 09/02/15.
 */
public class ZebraCrossing {


    private TrafficLight trafficLight;

    public ZebraCrossing() {
    }

    public ZebraCrossing(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }
}
