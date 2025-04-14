package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class Fly extends Module {


    public Fly() {
        super("Fly", "Allows you to fly in the air.", Category.MOVEMENT, true);
    }

    @Override
    public void onDisable() {
        mc.player.getAbilities().flying = false;
        super.onDisable();
    }

    @Override
    public void tick() {
        mc.player.getAbilities().flying = true;
    }
}
