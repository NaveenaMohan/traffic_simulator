package managers.runit;

/**
 * Created by naveena on 09/02/15.
 */
public class DirectionSign extends TrafficSign {

    private String location;
    private DirectionSignType directionSignType;

    public DirectionSign(String location, DirectionSignType directionSignType) {
        this.location = location;
        this.directionSignType = directionSignType;
    }

    public DirectionSignType getDirectionSignType() {
        return directionSignType;
    }

    public void setDirectionSignType(DirectionSignType directionSignType) {
        this.directionSignType = directionSignType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
