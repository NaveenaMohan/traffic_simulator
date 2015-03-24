package managers.runit;

/**
 * Created by naveena on 09/02/15.
 */
public class SpeedLimitSign extends TrafficSign {

    private int speedLimit;

    public SpeedLimitSign(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }
}
