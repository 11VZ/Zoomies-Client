package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;

public class Rotation extends Module
{
    DoubleSetting pitch = new DoubleSetting("Pitch", 0, 0, 360, 0);
    DoubleSetting yaw = new DoubleSetting("Yaw", 0, 0, 360, 0);
    
    public Rotation()
    {
        super("Rotation", "Locks camera to specified pitch and yaw.", Category.PLAYER, true);
        settings.add(pitch);
        settings.add(yaw);
    }

    @Override
    public void tick()
    {
        mc.player.setPitch((float)pitch.value);
        mc.player.setYaw((float)yaw.value);
    }
}
