package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;

public class Sprint extends Module 
{
    private final BooleanSetting omni = new BooleanSetting("Omni Sprint", false);

    public Sprint() 
    {
        super("Sprint", "Allows you to move faster.", Category.MOVEMENT, false);
        settings.add(omni);
    }
    
    @Override
    public void onEnable()
    {
        super.onEnable();
    }

    @Override
    public void tick()
    {	
        if (mc.player != null) {
            if (this.omni.value == true) {
                mc.player.setSprinting(true);
            } else {
                if (mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0) {
                    mc.player.setSprinting(true);
                } else {
                    mc.player.setSprinting(false);
                }
            }
        }
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        if (mc.player != null) {
            mc.player.setSprinting(false);
        }
    }
}
