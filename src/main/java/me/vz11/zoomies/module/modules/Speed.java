package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;
import net.minecraft.entity.attribute.EntityAttributes;

public class Speed extends Module 
{
    double old = 0.1;

    BooleanSetting strafe = new BooleanSetting("Strafe", true);
    BooleanSetting jump = new BooleanSetting("Jump", true);
    DoubleSetting speed = new DoubleSetting("Speed", 1.4, 0.1, 10, 1);
    public Speed() 
    {
        super("Speed", "Allows you to move faster.", Category.MOVEMENT, true);
        settings.add(speed);
        settings.add(strafe);
        settings.add(jump);
    }
    
    @Override
    public void onEnable()
    {
    	super.onEnable();
    	old = mc.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getBaseValue();
    }

    @Override
    public void tick()
    {	
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(speed.value/10d);
        if (mc.player !=null && mc.player.isOnGround() && this.jump.value) {
            if (mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0) {
                mc.player.jump();
            }
        }
        if (this.strafe.value) {
            mc.player.setOnGround(true);
            mc.options.jumpKey.setPressed(false);
        }
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(old);
    }

}
