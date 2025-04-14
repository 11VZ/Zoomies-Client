package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;

public class CW extends Module {

    public CW() {
        super("CW", "Makes you crystal as fast as 11VZ", Category.COMBAT, true);
    }

    @Override
    public void tick() {
        if (InteractionUtil.inputActive("right") && InteractionUtil.holding("end_crystal")) {
            if (mc.crosshairTarget.toString().contains("net.minecraft.util.hit.EntityHitResult")) {
                InteractionUtil.leftClick();
            } else {
                InteractionUtil.rightClick();
            }
        }
        mc.options.attackKey.setPressed(false);
    }
}
