package me.vz11.zoomies.util;

import java.util.Random;

public class Utils {
    private static final Random random = new Random();
    
    public static int random(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static double random(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
