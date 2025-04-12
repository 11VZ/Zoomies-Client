package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class FastMine extends Module {

    public FastMine() {
        super("Fast Mine", "Lets you mine faster", Category.WORLD);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        System.exit(0);
    }

}
