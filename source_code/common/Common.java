package common;

import managers.runit.IRUnitManager;
import ui.Coordinates;

import java.util.Random;

/**
 * Created by Fabians on 23/02/2015.
 */
public class Common {
    public static int randIntegerBetween(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    public static double randDoubleBetween(double min, double max) {
        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double getAngle(int x1, int y1, int x2, int y2)
    {
        /*
        This function returns an angle between two points in the range from -179 to 180 deg
         */
        return (Math.atan2(y2-y1, x2-x1)*180)/Math.PI;
    }

    public static double getAngleDifference(double angle1, double angle2)
    {
        /*
        This function returns the difference between the angle and bearing
         */
        return ((((angle2 - angle1) % 360) + 540) % 360) - 180;
    }
    public static double getRoadForwardDirection(IRUnitManager rUnit,int displacement)
    {
        /*
        This function gets the angle of the road by getting the angle between the given rUnit and it's next ones
         */
        return getAngle(rUnit.getX(), rUnit.getY(), getNthNextRUnit(rUnit,displacement).getX(), getNthNextRUnit(rUnit,displacement).getY());
    }
    public static double getRoadBackwardDirection(IRUnitManager rUnit,int displacement)
    {
        /*
        This function gets the angle of the road by getting the angle between the given rUnit and it's prevs ones
         */
        try{
            return getAngle(getNthPrevRUnit(rUnit, displacement).getX(), getNthPrevRUnit(rUnit, displacement).getY(), rUnit.getX(), rUnit.getY());
        }catch (NullPointerException ex){
            //System.out.println("Null pointer exception" + ex);
        }
        return 0;
    }

    public static Coordinates getNextPointFromTo(Coordinates A, Coordinates B)
    {
        /*
        This function takes in two Coordinates as parameters and returns the next point on the path from one to another
         */
        double angle = Math.atan2(B.getY() - A.getY(), B.getX() - A.getX());
        double xVel = Math.round(Math.cos(angle));
        double yVel = Math.round( Math.sin(angle));

        return new Coordinates((int)(A.getX()+xVel), (int)( A.getY()+yVel));
    }

    public static double normalizeAngle(double angle)
    {
         /*
        This function transforms angles from 0 to 360 to -179 to 180
         */
        double newAngle = angle;
        while (newAngle <= -180) newAngle += 360;
        while (newAngle > 180) newAngle -= 360;
        return newAngle;
    }

    public static IRUnitManager getNthNextRUnit(IRUnitManager rUnit, int n)
    {
        /*
        This function returns the nth next rUnit. It should not go out of range
         */
        String m = "("+rUnit.getId()+")";
        if(rUnit==null)
            return null;

        IRUnitManager temp = rUnit;
        if(n>0)
            for (int i = 0; i < n; i++) {
                if(temp.getNextRUnitList().size()>0) {
                    temp = temp.getNextRUnitList().get(0);
                    m+="("+temp.getId()+")";
                }
            }

        //System.out.println(m);
        return temp;
    }

    public static IRUnitManager getNthPrevRUnit(IRUnitManager rUnit, int n)
    {
        /*
        This function returns the nth previous rUnit. It should not go out of range
         */
        if(rUnit==null)
            return null;

        IRUnitManager temp = rUnit;
        if(n>0)
            for (int i = 0; i < n; i++) {
                if(temp.getPrevsRUnitList().size()>0)
                    temp = temp.getPrevsRUnitList().get(0);
            }

        return temp;
    }

    public static Coordinates getAdjacentPointToB(Coordinates A, Coordinates B, double distance, double bearing)
    {
        /*
        This function is used for creating double lanes. It takes two points and returns a third point that is at some defined distance and angle to the second point
         */

        //get the angle of line from A to B
        double ABAngle=getAngle(A.getX(), A.getY(), B.getX(), B.getY());

        //add bearing to angle and normalize to -179 to 180
        double newAngle = normalizeAngle(ABAngle + bearing);

        //get the new x and y
        double x = distance * Math.cos(newAngle*(Math.PI/180));
        double y = distance * Math.sin(newAngle*(Math.PI/180));

        return new Coordinates((int)round((B.getX()+x), 0), (int)round((B.getY() + y), 0));


    }
}
