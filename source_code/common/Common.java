package common;

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

    public static Coordinates getNextPointFromTo(Coordinates A, Coordinates B)
    {

        double angle = Math.atan2(B.getY() - A.getY(), B.getX() - A.getX());
        double xVel = Math.round(Math.cos(angle));
        double yVel = Math.round( Math.sin(angle));

        return new Coordinates((int)(A.getX()+xVel), (int)( A.getY()+yVel));
    }

    public static double normalizeAngle(double angle)
    {
        double newAngle = angle;
        while (newAngle <= -180) newAngle += 360;
        while (newAngle > 180) newAngle -= 360;
        return newAngle;
    }

    public static Coordinates getAdjacentPointToB(Coordinates A, Coordinates B, double distance, double bearing)
    {
        //get the angle of line from A to B
        double ABAngle=(Math.atan2(B.getY()-A.getY(), B.getX()-A.getX())*180)/Math.PI;

        //add bearing to angle and normalize to -179 to 180
        double newAngle = normalizeAngle(ABAngle + bearing);

        //get the new x and y
        double x = distance * Math.cos(newAngle*(Math.PI/180));
        double y = distance * Math.sin(newAngle*(Math.PI/180));

        return new Coordinates((int)round((B.getX()+x), 0), (int)round((B.getY() + y), 0));


    }
}
