package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.util.InteractionUtil;

public class Triggerbot extends Module {
    BooleanSetting rightClick = new BooleanSetting("Require Right Click", false);

    public Triggerbot() {
        super("Triggerbot", "Automatically attacks entities in crosshair.", Category.COMBAT);
        settings.add(rightClick);
    }

    @Override
    public void tick() {
        assert mc.player != null;

        if (InteractionUtil.isLookingAtPlayer(9) && mc.player.getAttackCooldownProgress(0.5f) >= 1) {
            if (this.rightClick.value) {
                if (InteractionUtil.inputActive("right")) {InteractionUtil.leftClick();}
            } else {
                InteractionUtil.leftClick();
            }
                
        }
    }
}
