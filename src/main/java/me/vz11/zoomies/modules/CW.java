package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.util.RayTraceUtil;
import me.vz11.zoomies.module.settings.Setting;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.util.ClickCounter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.entity.Entity;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class CW extends Module {
    private final DoubleSetting speed = new DoubleSetting("Speed", 0, 0, 100, 1);
    private final BooleanSetting simulateClicks = new BooleanSetting("Simulate Clicks", false);
    private long lastClickTime = 0;

    public CW() {
        super("CW", "Makes you crystal as fast as 11VZ", Category.COMBAT, true);
        settings.add(speed);
        settings.add(simulateClicks);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        lastClickTime = System.currentTimeMillis();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void tick() {
        if (mc.getWindow() == null) return;
        long windowHandle = mc.getWindow().getHandle();
        boolean isRightMouseButtonPressed = GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        if (mc.player == null || !InteractionUtil.isPlaying()) return;
        if (!isRightMouseButtonPressed || !InteractionUtil.holding("end_crystal")) return;

            long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < (long) speed.value) return;

        if (RayTraceUtil.isLookingAtCrystal(5.0)) {
            Entity entity = RayTraceUtil.getLookedAtEntity(5.0);
            if (entity != null) {
                InteractionUtil.breakCrystal(entity);
                    if (simulateClicks.value) {
                        ClickCounter.INSTANCE.registerLeftClick();
                }
                lastClickTime = currentTime;
                    }
                } else {
            HitResult hit = mc.crosshairTarget;
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockState state = mc.world.getBlockState(((BlockHitResult) hit).getBlockPos());
                if (state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK) {
                    InteractionUtil.rightClick();
                    if (simulateClicks.value) {
                        ClickCounter.INSTANCE.registerRightClick();
                        mc.options.useKey.setPressed(true);
                        mc.options.useKey.setPressed(false);
                    }
                    lastClickTime = currentTime;
                }
            }
        }
    }
}
