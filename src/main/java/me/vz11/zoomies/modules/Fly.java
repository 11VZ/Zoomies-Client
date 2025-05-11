package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.DropdownSetting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Fly extends Module {

    public final DropdownSetting mode = new DropdownSetting("Mode", "Velocity", new String[]{"Velocity", "Creative"});
    private final DoubleSetting flySpeed = new DoubleSetting("Fly Speed", 1.0, 0.1, 5.0, 1);
    private boolean isFlying;

    public Fly() {
        super("Fly", "Allows you to fly in the air.", Category.MOVEMENT, true);
        settings.add(flySpeed);
        settings.add(mode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        isFlying = true;
        if (mc.player != null) {
            mc.player.setOnGround(false);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        isFlying = false;
        if (mc.player != null) {
            mc.player.setVelocity(0, 0, 0);
            mc.player.getAbilities().flying = false;
        }
    }

    @Override
    public void tick() {
        PlayerEntity player = mc.player;
        if (player != null && isFlying) {
            if (mode.value.equals("Creative")) {
                mc.player.getAbilities().flying = true;
            } else {
                mc.player.getAbilities().flying = false;
                double speed = flySpeed.value;
                
                double yawRad = Math.toRadians(player.getYaw());
                double forward = 0.0;
                double right = 0.0;
                
                if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) forward += 1.0;
                if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) forward -= 1.0;
                if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) right -= 1.0;
                if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) right += 1.0;
                
                double x = -Math.sin(yawRad) * forward + Math.cos(yawRad) * right;
                double z = Math.cos(yawRad) * forward + Math.sin(yawRad) * right;
                
                double y = (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS ? 1.0 : 0.0) - 
                           (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS ? 1.0 : 0.0);
                
                x *= speed;
                y *= speed;
                z *= speed;

                player.setVelocity(x, y, z);
                player.setOnGround(false);
            }
        }
    }
}
