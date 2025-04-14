package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.RayTraceUtil;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.util.DelayUtil;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;

public class AnchorMacro extends Module {

    // Settings
    private final BooleanSetting glowstone = new BooleanSetting("Glowstone Only", false);
    private final DoubleSetting switchToGlowstoneDelay = new DoubleSetting("Switch Delay", 20.0, 0.0, 500.0, 1);
    private final DoubleSetting rightClickDelay = new DoubleSetting("Click Delay", 40.0, 0.0, 500.0, 1);
    private final DoubleSetting switchBackDelay = new DoubleSetting("Switch Back Delay", 100.0, 0.0, 500.0, 1);
    private final DoubleSetting finalRightClickDelay = new DoubleSetting("Final Right Click Delay", 50.0, 0.0, 500.0, 1);

    // Delay Identifiers
    private static final String SWITCH_GLOWSTONE_ID = "anchorMacro_switchGlowstone";
    private static final String RIGHT_CLICK_ID = "anchorMacro_rightClick";
    private static final String SWITCH_BACK_ID = "anchorMacro_switchBack";
    private static final String FINAL_RIGHT_CLICK_ID = "anchorMacro_finalRightClick";

    private boolean macroInProgress = false;

    public AnchorMacro() {
        super("Anchor Macro", "Charges a respawn anchor with glowstone when looking at it.", Category.COMBAT, true);
        settings.add(glowstone);
        settings.add(switchToGlowstoneDelay);
        settings.add(rightClickDelay);
        settings.add(switchBackDelay);
        settings.add(finalRightClickDelay);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        startMacro();
    }

    @Override
    public void tick() {
        if (!isLookingAtUnchargedAnchor()) {
            resetMacro();
            return;
        }

        if (InteractionUtil.inputActive("right") && macroInProgress) {
            if (DelayUtil.isDelayOver(SWITCH_GLOWSTONE_ID)) {
                InteractionUtil.switchToBlock("glowstone");
                DelayUtil.delay(RIGHT_CLICK_ID, (long) switchToGlowstoneDelay.value);
            }

            if (DelayUtil.isDelayOver(RIGHT_CLICK_ID)) {
                InteractionUtil.rightClick();
                DelayUtil.delay(SWITCH_BACK_ID, (long) rightClickDelay.value);
            }

            if (DelayUtil.isDelayOver(SWITCH_BACK_ID)) {
                InteractionUtil.switchToBlock("totem_of_undying");
                DelayUtil.delay(FINAL_RIGHT_CLICK_ID, (long) switchBackDelay.value);
            }

            if (DelayUtil.isDelayOver(FINAL_RIGHT_CLICK_ID)) {
                InteractionUtil.rightClickItem();
                resetMacro();  // End the macro after completing all actions
            }
        }
    }

    private void startMacro() {
        macroInProgress = true;
        DelayUtil.delay(SWITCH_GLOWSTONE_ID, 0);  // Start immediately
    }

    private boolean isLookingAtUnchargedAnchor() {
        String blockId = String.valueOf(RayTraceUtil.getLookedAtBlock(5.0));
        return blockId.equals("Block{minecraft:respawn_anchor}[charges=0]");
    }

    private void resetMacro() {
        macroInProgress = false;
        DelayUtil.delay(SWITCH_GLOWSTONE_ID, 0);
        DelayUtil.delay(RIGHT_CLICK_ID, 0);
        DelayUtil.delay(SWITCH_BACK_ID, 0);
        DelayUtil.delay(FINAL_RIGHT_CLICK_ID, 0);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        resetMacro();
    }
}
