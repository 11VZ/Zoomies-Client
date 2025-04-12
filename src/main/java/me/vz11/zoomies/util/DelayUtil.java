package me.vz11.zoomies.util;

import java.util.HashMap;
import java.util.Map;

public class DelayUtil {
    private static final Map<String, Long> delayTimestamps = new HashMap<>();

    public static void delay(String moduleId, long millis) {
        delayTimestamps.put(moduleId, System.currentTimeMillis() + millis);
    }

    public static boolean isDelayOver(String moduleId) {
        Long endTime = delayTimestamps.get(moduleId);
        if (endTime == null) return true;

        if (System.currentTimeMillis() >= endTime) {
            delayTimestamps.remove(moduleId);
            return true;
        }
        return false;
    }
}
