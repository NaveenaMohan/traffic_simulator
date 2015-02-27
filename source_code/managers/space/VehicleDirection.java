package managers.space;

/**
 * Created by naveena on 08/02/15.
 */
public class VehicleDirection {

    private double difX;
    private double difY;
    private double angle;

    public VehicleDirection(int x1, int y1, int x2, int y2) {
        this.difX=x2-x1;
        this.difY=y2-y1;
        angle=(Math.atan2(difY, difX)*180)/Math.PI;
    }

    public double getAngle() {
        return angle;
    }
}
