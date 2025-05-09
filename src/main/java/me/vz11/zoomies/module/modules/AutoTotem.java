package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module 
{
	public BooleanSetting overrideItems = new BooleanSetting("Override other items", true);
	
    public AutoTotem()
    {
        super("Auto Totem", "Automatically puts totems in offhand.", Category.COMBAT, true);
        settings.add(overrideItems);
    }
    
    @Override
    public void tick()
    {
    	if (mc.player.getInventory().offHand.get(0).getItem() != Items.TOTEM_OF_UNDYING)
    	{
    		if((!mc.player.getInventory().offHand.isEmpty()) && !overrideItems.value) return;
    		for (int i = 0; i <= 35; i++)
    		{
    			if (mc.player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING)
    			{
    				mc.interactionManager.clickSlot(0, i, 8, SlotActionType.SWAP, mc.player);
    				mc.interactionManager.clickSlot(0, 45, 8, SlotActionType.SWAP, mc.player);
    				mc.interactionManager.clickSlot(0, i, 8, SlotActionType.SWAP, mc.player);
    			}
    		}
    	}
    }
}
