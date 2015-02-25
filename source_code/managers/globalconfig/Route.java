package managers.globalconfig;

/**
 * Created by naveena on 09/02/15.
 */
public class Route {

    private String destination;
    private double trafficPercent;

    public Route() {
    }

    public Route(String destination, double trafficPercent) {
        this.destination = destination;
        setTrafficPercent(trafficPercent);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getTrafficPercent() {
        return trafficPercent;
    }

    public void setTrafficPercent(double trafficPercent) {
        if(trafficPercent>=0 && trafficPercent<=1){
            this.trafficPercent = trafficPercent;
        }else throw new IllegalArgumentException("Error setting traffic percentage");
    }
}
