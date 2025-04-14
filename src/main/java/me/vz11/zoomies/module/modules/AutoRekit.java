package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.StringSetting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AutoRekit extends Module 
{
    public StringSetting kit = new StringSetting("Command to type", "kit 1");
    public DoubleSetting count2 = new DoubleSetting("Amount of totems", 3, 0, 20, 0);
    
    public AutoRekit()
    {
        super("Auto Rekit", "Automatically rekits when you run out of totems.", Category.COMBAT, true);
        settings.add(kit);
        settings.add(count2);
    }

    @Override
    public void tick() {
        int count = 0;
		for (int i = 0; i < 36; i++)
		{
			ItemStack stack = mc.player.getInventory().getStack(i);
			if (stack.getItem() == Items.TOTEM_OF_UNDYING) count += stack.getCount();
		}

        if (count <= count2.value) {
            mc.player.networkHandler.sendChatCommand(kit.value);
        }
    }
}
