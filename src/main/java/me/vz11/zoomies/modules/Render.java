package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import net.minecraft.client.MinecraftClient;

public class Render extends Module
{    
    public final BooleanSetting explosions = new BooleanSetting("Explosions", false);
    public final BooleanSetting items = new BooleanSetting("Items", false);
    public final BooleanSetting crystals = new BooleanSetting("Crystals", false);
    
    public Render()
    {
        super("NoRender", "Prevents certain renders from happening.", Category.RENDER, true);
        settings.add(explosions);
        settings.add(items);
        settings.add(crystals);
    }
}
