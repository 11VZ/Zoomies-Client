package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.util.RayTraceUtil;

public class Prevent extends Module {

    public Prevent() {
        super("Prevent", "Prevents actions in certain circumstances", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        InteractionUtil.registerRightClickCancel();
    }

    @Override
    public void tick() {
        String blockId = String.valueOf(RayTraceUtil.getLookedAtBlock(3.0));
        boolean lookingAtAnchor = blockId.equals("Block{minecraft:respawn_anchor}[charges=1]");

        if (lookingAtAnchor && InteractionUtil.holding("glowstone") && InteractionUtil.inputActive("right")) {
            InteractionUtil.cancelCurrentRightClick();
        }
    }
}
