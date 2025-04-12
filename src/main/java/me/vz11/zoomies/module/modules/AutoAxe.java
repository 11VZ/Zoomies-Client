package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;

public class AutoAxe extends Module {
    public AutoAxe() {
        super("Auto Axe", "Automatically axes someone when looking at them", Category.COMBAT);
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
        if (InteractionUtil.inputActive("B")) {
            InteractionUtil.rightClickItem();
        }
    }
}
