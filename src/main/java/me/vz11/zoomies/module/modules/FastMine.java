package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.DropdownSetting;
import me.vz11.zoomies.util.RayTraceUtil;

public class FastMine extends Module {
    public final DropdownSetting mode = new DropdownSetting("Mode", "Instant", new String[]{"Instant", "Fast", "Custom"});
    public final DoubleSetting speed = new DoubleSetting("Speed", 1.0, 0.1, 5.0, 1);
    private final BooleanSetting onlyOres = new BooleanSetting("Only Ores", false);

    public FastMine() {
        super("Fast Mine", "Lets you mine faster", Category.WORLD, true);
        settings.add(mode);
        settings.add(speed);
        settings.add(onlyOres);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public boolean shouldFastMine() {
        if (!enabled) return false;
        
        String blockId = String.valueOf(RayTraceUtil.getLookedAtBlock(3.0));
        
        if (onlyOres.value) {
            return blockId.contains("ore") || 
                   blockId.contains("diamond") || 
                   blockId.contains("emerald") || 
                   blockId.contains("gold") || 
                   blockId.contains("iron") || 
                   blockId.contains("coal") || 
                   blockId.contains("lapis") || 
                   blockId.contains("redstone") || 
                   blockId.contains("quartz");
        }
        
        return true;
    }
}
