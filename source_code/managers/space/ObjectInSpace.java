package managers.space;

import managers.globalconfig.VehicleType;

import java.awt.*;

/**
 * Created by naveena on 08/02/15.
 */
public class ObjectInSpace {

    private int id;
    private int x;
    private int y;
    private int z;
    private int width;
    private int height;
    private int length;
    private boolean isVisible;
    private VehicleDirection direction;
    private VehicleType vehicleType;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public ObjectInSpace(int id, int x, int y, int z, int width, int length, int height, VehicleDirection direction,  VehicleType vehicleType) {
        this.id=id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.length = length;
        this.direction = direction;
        this.vehicleType=vehicleType;
        isVisible = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public VehicleDirection getDirection() {
        return direction;
    }

    public void setDirection(VehicleDirection direction) {
        this.direction = direction;
    }


    public Rectangle getBounds() {
        //this must be changed in the future if we implement direction as well
        return new Rectangle(x, y, width*2, length*2);
    }

}
