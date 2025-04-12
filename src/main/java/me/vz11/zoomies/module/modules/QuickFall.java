package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import net.minecraft.util.math.Vec3d;

public class QuickFall extends Module {


    public QuickFall() {
        super("QuickFall", "Makes you fall really quickly", Category.MOVEMENT);
    }

    
    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.player != null) {
            Vec3d currentVelocity = mc.player.getVelocity();
            mc.player.setVelocity(new Vec3d(currentVelocity.x, 0, currentVelocity.z));
        }
        
    }

    @Override
    public void tick() {
        if (mc.player == null) return;
        if (!mc.player.isOnGround() && mc.player != null && mc.player.fallDistance > 0.1f) {
            Vec3d currentVelocity = mc.player.getVelocity();
            mc.player.setVelocity(new Vec3d(currentVelocity.x, -10, currentVelocity.z));
        }
    }
}
