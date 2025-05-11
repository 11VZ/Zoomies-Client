package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.util.Timer;

public class AutoAFK extends Module
{
    public DoubleSetting delay = new DoubleSetting("Delay (Seconds)", 1, 0.1, 10, 1);
    private Timer timer = new Timer();

    public AutoAFK()
    {
        super("Auto AFK", "Automatically jumps to prevent AFK checks.", Category.MOVEMENT, false);
        settings.add(delay);
    }

    @Override
    public void tick()
    {
        if (timer.hasTimeElapsed((long)delay.value * 1000, true) && mc.player.isOnGround() && mc.player.hasVehicle() == false) 
        {
        	mc.player.jump();
        }
    }
}
