package me.vz11.zoomies.util;

import java.util.ArrayList;
import java.util.List;

public class ClickCounter {
    public static final ClickCounter INSTANCE = new ClickCounter();
    
    private final List<Long> leftClicks = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();
    
    public synchronized void registerLeftClick() {
        leftClicks.add(System.currentTimeMillis());
    }
    
    public synchronized void registerRightClick() {
        rightClicks.add(System.currentTimeMillis());
    }
    
    public int getLeftCPS() {
        return calculateCPS(leftClicks);
    }
    
    public int getRightCPS() {
        return calculateCPS(rightClicks);
    }
    
    private int calculateCPS(List<Long> clicks) {
        long currentTime = System.currentTimeMillis();
        clicks.removeIf(time -> currentTime - time > 1000);
        return clicks.size();
    }
}