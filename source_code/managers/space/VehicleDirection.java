package managers.space;

import common.Common;

import java.io.Serializable;

/**
 * Created by naveena on 08/02/15.
 */
public class VehicleDirection implements Serializable {

    private double angle;

    public VehicleDirection(int x1, int y1, int x2, int y2) {

        angle = Common.getAngle(x1, y1, x2, y2);
    }

    public double getAngle() {
        return angle;
    }

    public double getDifference(double bearing) {
        return Common.getAngleDifference(angle, bearing);
    }
}
