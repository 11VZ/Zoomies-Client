package me.vz11.zoomies.util;

import me.vz11.zoomies.event.events.PacketEvent;
import me.vz11.zoomies.event.events.GameJoinedEvent;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import java.util.Arrays;
import me.vz11.zoomies.event.Event;
import me.vz11.zoomies.event.EventBus;

public class TickRateUtil {
    public static TickRateUtil INSTANCE = new TickRateUtil();
    
    private final float[] tickRates = new float[20];
    private int nextIndex = 0;
    private long timeLastTimeUpdate = -1;
    private long timeGameJoined;

    public TickRateUtil() {
        EventBus.register(this);
    }

    @Event.EventHandler
    public void onReceivePacket(PacketEvent.Receive event) {
        if (event.packet instanceof WorldTimeUpdateS2CPacket) {
            long now = System.currentTimeMillis();
            if (timeLastTimeUpdate != -1) {
                float timeElapsed = (now - timeLastTimeUpdate) / 1000.0F;
                tickRates[nextIndex] = Math.min(20.0f, 20.0f / timeElapsed);
                nextIndex = (nextIndex + 1) % tickRates.length;
            }
            timeLastTimeUpdate = now;
        }
    }

    @Event.EventHandler
    public void onGameJoined(GameJoinedEvent event) {
        Arrays.fill(tickRates, 0);
        nextIndex = 0;
        timeGameJoined = timeLastTimeUpdate = System.currentTimeMillis();
    }

    public float getTickRate() {
        if (System.currentTimeMillis() - timeGameJoined < 4000) return 20.0f;
        
        int numTicks = 0;
        float sumTickRates = 0.0f;
        for (float tickRate : tickRates) {
            if (tickRate > 0) {
                sumTickRates += tickRate;
                numTicks++;
            }
        }
        return numTicks > 0 ? sumTickRates / numTicks : 0;
    }
}