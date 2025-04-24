package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class Criticals extends Module
{
    public Criticals()
    {
        super("Criticals", "Critical smacks.", Category.COMBAT, true);
    }
    
    @Override
    public void onEnable()
    {
    	super.onEnable();
    }

    @Override
    public void tick()
    {	

    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }
}
