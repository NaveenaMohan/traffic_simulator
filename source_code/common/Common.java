package common;

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
}
