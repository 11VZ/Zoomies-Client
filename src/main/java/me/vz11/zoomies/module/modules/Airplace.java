package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.util.DelayUtil;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.util.RayTraceUtil;

public class Airplace extends Module {
    private static final String MODULE_ID = "Airplace";
    private final BooleanSetting amount = new BooleanSetting("Only 2 Anchors", true);

    public Airplace() {
        super("Airplace", "Makes airplacing easier", Category.COMBAT);
        settings.add(amount);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void tick() {
        String blockId = String.valueOf(RayTraceUtil.getLookedAtBlock(3.0));
        boolean lookingAtAnchor = blockId.equals("Block{minecraft:respawn_anchor}[charges=1]");

        if (amount.value) {
            if (lookingAtAnchor && InteractionUtil.holding("respawn_anchor") && InteractionUtil.inputActive("right") && DelayUtil.isDelayOver(MODULE_ID)) {
                InteractionUtil.rightClick();
                DelayUtil.delay(MODULE_ID, 1000);
            }
        } else {
            if (lookingAtAnchor && InteractionUtil.holding("respawn_anchor") && InteractionUtil.inputActive("right")) {
                InteractionUtil.rightClick();
            }
        }
    }

}
