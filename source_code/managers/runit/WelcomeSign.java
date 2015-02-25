package managers.runit;

/**
 * Created by naveena on 09/02/15.
 */
public class WelcomeSign extends TrafficSign {

    private String location;

    public WelcomeSign(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
