package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class MaceBoost extends Module {
    
    public MaceBoost() {
        super("MaceBoost", "Amplifies mace damage by simulating high-velocity impacts", Category.COMBAT);
    }

    @Override
    public void tick() {
        mc.player.setOnGround(false);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}