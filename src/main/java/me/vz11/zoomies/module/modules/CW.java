package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;
import net.minecraft.client.MinecraftClient;

public class CW extends Module {
    public CW() {
        super("CW", "Crystals for you", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void tick() {
        super.tick();
        if (InteractionUtil.holding("end_crystal")) {
            System.out.println(MinecraftClient.getInstance().crosshairTarget);
        }
    }
}
