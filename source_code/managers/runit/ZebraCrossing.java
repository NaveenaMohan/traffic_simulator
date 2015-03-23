package managers.runit;

import java.io.Serializable;

/**
 * Created by naveena on 09/02/15.
 */
public class ZebraCrossing implements Serializable {


    private TrafficLight trafficLight;

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
