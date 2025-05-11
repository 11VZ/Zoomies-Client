package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class Velocity extends Module
{
    public Velocity()
    {
        super("Velocity", "Prevents player knockback when hit.", Category.COMBAT, true);
    }
    
    @Override
    public void onEnable()
    {
        super.onEnable();
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }
    
    @Override
    public void tick()
    {
        if (mc.player.hurtTime > 0) mc.player.setVelocity(0, mc.player.getVelocity().y - 100, 0);
    }
}
