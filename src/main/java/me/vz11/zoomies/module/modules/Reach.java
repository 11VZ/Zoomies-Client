package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;
import net.minecraft.entity.attribute.EntityAttributes;

public class Reach extends Module
{
	DoubleSetting blockRange = new DoubleSetting("Block Range", 4.5, 1, 10, 1);
	DoubleSetting entityRange = new DoubleSetting("Entity Range", 3.0, 1, 10, 1);
    double oldBe = 4.5;
    double oldEe = 3.0;

    public Reach()
    {
        super("Reach", "Extends player interaction distance.", Category.COMBAT, true);
        settings.add(blockRange);
        settings.add(entityRange);
    }
    
    @Override
    public void onEnable()
    {
    	super.onEnable();
    	oldBe = mc.player.getAttributeInstance(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE).getBaseValue();
    	oldEe = mc.player.getAttributeInstance(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE).getBaseValue();
    }

    @Override
    public void tick()
    {	
    	mc.player.getAttributeInstance(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE).setBaseValue(blockRange.value);
    	mc.player.getAttributeInstance(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE).setBaseValue(entityRange.value);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        mc.player.getAttributeInstance(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE).setBaseValue(oldBe);
    	mc.player.getAttributeInstance(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE).setBaseValue(oldEe);
    }
}
