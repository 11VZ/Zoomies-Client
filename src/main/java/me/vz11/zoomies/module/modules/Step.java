package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;
import net.minecraft.entity.attribute.EntityAttributes;

public class Step extends Module
{
    DoubleSetting stepHeight = new DoubleSetting("Height", 1, 1, 10, 0);
    double old = 0.6;

    public Step()
    {
        super("Step", "Allows you to step up full blocks.", Category.MOVEMENT, true);
        settings.add(stepHeight);
    }
    
    @Override
    public void onEnable()
    {
    	super.onEnable();
    	old = mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).getBaseValue();
    }

    @Override
    public void tick()
    {	
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(stepHeight.value);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(old);
    }
}
