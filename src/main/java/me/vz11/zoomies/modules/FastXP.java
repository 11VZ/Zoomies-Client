package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.module.settings.DoubleSetting;

public class FastXP extends Module {

    private final DoubleSetting delay = new DoubleSetting("Delay", 10, 0, 1000, 1);
    private long lastClickTime = 0;

    public FastXP() {
        super("FastXP", "Spams XP while holding right-click", Category.COMBAT, true);
        settings.add(delay);
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

    public void tick() {
        if (InteractionUtil.inputActive("right") && InteractionUtil.holding("experience_bottle")) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime >= (long) delay.value) {
                InteractionUtil.rightClickItem();
                lastClickTime = currentTime;
            }
        }
    }
}
