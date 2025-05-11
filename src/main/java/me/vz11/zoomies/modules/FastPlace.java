package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.util.InteractionUtil;

public class FastPlace extends Module {

    public BooleanSetting crystalonly = new BooleanSetting("Crystal Only", false);
    public FastPlace() {
        super("Fastplace", "Places blocks really fast", Category.COMBAT, true);
        settings.add(crystalonly);
    }

    @Override
    public void tick() {
        if (InteractionUtil.inputActive("right")) {
            if (crystalonly.value) {
                if (InteractionUtil.holding("end_crystal")) {
                    InteractionUtil.rightClick();
                }
            } else {
                InteractionUtil.rightClick();
            }
        }
    }
}
