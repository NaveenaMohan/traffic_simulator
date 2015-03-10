package managers.globalconfig;

/**
 * Created by Fabians on 03/03/2015.
 */
public class VehicleTypeStats {
    //this class contains all the standard types for all vehicle types
    public static int getWidth(VehicleType vehicleType)
    {
        switch(vehicleType)
        {
            case car:
                return 2;
            case emergency:
                return 2;
            case heavyLoad:
                return 2;
            default:
                return 2;
        }
    }
    public static int getLength(VehicleType vehicleType)
    {
        switch(vehicleType)
        {
            case car:
                return 3;
            case emergency:
                return 3;
            case heavyLoad:
                return 4;
            default:
                return 3;
        }
    }
    public static int getHeight(VehicleType vehicleType)
    {
        switch(vehicleType)
        {
            case car:
                return 2;
            case emergency:
                return 2;
            case heavyLoad:
                return 2;
            default:
                return 2;
        }
    }
    public static int getMaxVelocity(VehicleType vehicleType)
    {
        switch(vehicleType)
        {
            case car:
                return (120*1000)/3600;
            case emergency:
                return (150*1000)/3600;
            case heavyLoad:
                return (70*1000)/3600;
            default:
                return (120*1000)/3600;
        }
    }
    public static double getMaxAcceleration(VehicleType vehicleType)
    {
        switch(vehicleType)
        {
            case car:
                return 2.7;
            case emergency:
                return 4;
            case heavyLoad:
                return 1.3;
            default:
                return 2.7;
        }
    }

    public static double getMaxDeceleration(VehicleType vehicleType)
    {
        switch(vehicleType)
        {
            case car:
                return -500;
            case emergency:
                return -500;
            case heavyLoad:
                return -500;
            default:
                return -500;
        }
    }
}
