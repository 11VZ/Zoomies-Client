package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.util.RayTraceUtil;
import me.vz11.zoomies.util.ClickCounter;
import me.vz11.zoomies.util.ChatUtils;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.block.BlockState;

public class AnchorMacro extends Module {
    private int step = 0;
    private boolean hasTotem = false;
    private int switchDelay = 0;

    private final DoubleSetting delayAmount = new DoubleSetting("Delay", 1, 0.1, 10, 0);
    private final BooleanSetting simulateClicks = new BooleanSetting("Simulate Clicks", false);
    private final BooleanSetting glowstoneOnly = new BooleanSetting("Glowstone Only", false);

    public AnchorMacro() {
        super("Anchor Macro", "Automatically charges and activates respawn anchors", Category.COMBAT, true);
        settings.add(delayAmount);
        settings.add(simulateClicks);
        settings.add(glowstoneOnly);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        step = 1;
        switchDelay = 0;
    }

    @Override
    public void tick() {
        if (step == 0) return;
        if (mc.player == null) return;
        if (!InteractionUtil.isPlaying()) return;

        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof BlockHitResult blockHit)) return;

        BlockState state = mc.world.getBlockState(blockHit.getBlockPos());
        if (!(state.getBlock() instanceof RespawnAnchorBlock)) {
            step = 1;
            return;
        }

        switch (step) {
            case 1:
                int charges = state.get(RespawnAnchorBlock.CHARGES);
                if (charges > 0) {
                    if (!glowstoneOnly.value) {
                        if (hasTotem) {
                            InteractionUtil.switchToBlock("totem_of_undying");
                        } else {
                            mc.player.getInventory().selectedSlot = 8;
                        }
                        step = 3;
                    } else {
                        step = 1;
                    }
                    break;
                }

                if (!glowstoneOnly.value) {
                    hasTotem = false;
                    for (int i = 0; i < 9; i++) {
                        if (mc.player.getInventory().getStack(i).getItem().toString().contains("totem_of_undying")) {
                            hasTotem = true;
                            break;
                        }
                    }
                }
                InteractionUtil.switchToBlock("glowstone");
                step++;
                break;

            case 2:
                if (InteractionUtil.holding("glowstone")) {
                    if (state.get(RespawnAnchorBlock.CHARGES) == 0) {
                        InteractionUtil.rightClick();
                        if (simulateClicks.value) {
                            mc.options.useKey.setPressed(true);
                            ClickCounter.INSTANCE.registerRightClick();
                            mc.options.useKey.setPressed(false);
                        }
                    }
                    
                    if (!glowstoneOnly.value) {
                        if (hasTotem) {
                            InteractionUtil.switchToBlock("totem_of_undying");
                        } else {
                            mc.player.getInventory().selectedSlot = 8;
                        }
                        step = 3;
                    } else {
                        step = 1;
                    }
                }
                break;

            case 3:
                if (switchDelay++ >= 1) {
                    InteractionUtil.rightClick();
                    if (simulateClicks.value) {
                        mc.options.useKey.setPressed(true);
                        ClickCounter.INSTANCE.registerRightClick();
                        mc.options.useKey.setPressed(false);
                    }
                    step = 1;
                    switchDelay = 0;
                }
                break;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        step = 0;
        switchDelay = 0;
    }
}
