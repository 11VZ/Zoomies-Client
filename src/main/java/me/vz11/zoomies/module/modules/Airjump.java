package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class Airjump extends Module {
    public Airjump() {
        super("Airjump", "Jump in the air!", Category.MOVEMENT, true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.player.setOnGround(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.setOnGround(false);
    }

    @Override
    public void tick() {
        mc.player.setOnGround(true);
    }
}
