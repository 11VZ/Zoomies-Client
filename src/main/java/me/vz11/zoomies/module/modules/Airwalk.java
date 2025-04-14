package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class Airwalk extends Module {
    public Airwalk() {
        super("Airwalk", "Lets you walk in the air!", Category.MOVEMENT, true);
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
        double velX = mc.player.getVelocity().x;
        double velZ = mc.player.getVelocity().z;
        mc.player.setVelocity(velX, 0, velZ);
        super.tick();
        if(mc.options.sneakKey.isPressed()) {
            mc.player.setVelocity(velX, -0.5, velZ);
        } else if (mc.options.jumpKey.isPressed()) {
            mc.player.setVelocity(velX, 0.5, velZ);
        }
    }
}
