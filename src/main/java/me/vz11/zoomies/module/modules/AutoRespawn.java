package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class AutoRespawn extends Module 
{
    public AutoRespawn()
    {
        super("Auto Respawn", "Automatically respawns you after death.", Category.PLAYER, false);
    }

    @Override
    public void tick() {
        if (mc.player.getHealth() <= 0) {
            mc.player.requestRespawn();
        }
    }
}
