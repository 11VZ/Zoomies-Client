package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class AutoWalk extends Module
{

    public AutoWalk()
    {
        super("Auto Walk", "Automatically moves forward.", Category.MOVEMENT);
    }

    @Override
    public void tick()
    {
        mc.options.forwardKey.setPressed(true);
    }
    
    @Override
    public void onDisable() 
    {
    	super.onDisable();
    	mc.options.forwardKey.setPressed(false);
    }
}
