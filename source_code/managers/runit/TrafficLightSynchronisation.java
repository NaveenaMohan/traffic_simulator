package managers.runit;

import java.io.Serializable;
import java.util.List;

/**
 * Created by naveena on 09/02/15.
 */
public class TrafficLightSynchronisation implements Serializable {

    private List<Boolean> trafficLightPattern;

    public List<Boolean> getTrafficLightPattern() {
        return trafficLightPattern;
    }

    public void setTrafficLightPattern(List<Boolean> trafficLightPattern) {
        this.trafficLightPattern = trafficLightPattern;
    }
}
