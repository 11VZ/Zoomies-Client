package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.module.settings.DoubleSetting;

public class PearlMacro extends Module {

    private int step = 0;
    private int delayCounter = 0;

    // User-defined delay setting (converted to ticks in `onTick`)
    DoubleSetting delayAmount = new DoubleSetting("Delay", 1, 0.1, 10, 0);
    BooleanSetting switchback = new BooleanSetting("Switch Back", true);

    public PearlMacro() {
        super("Pearl Macro", "When you press a keybind it pearls", Category.COMBAT);
        settings.add(delayAmount);
        settings.add(switchback);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        step = 1;  // Start with the first step
    }

    @Override
    public void tick() {
        if (step == 0) return;

        if (delayCounter++ < (int) delayAmount.value) return;
        delayCounter = 0;

        // Execute actions with delay
        switch (step) {
            case 1:
                InteractionUtil.switchToBlock("ender_pearl");
                step++;
                break;
            case 2:
                InteractionUtil.rightClickItem();
                step++;
                break;
            case 3:
                if (switchback.value) {
                    InteractionUtil.switchBack();
                }
                step = 0;  // Reset after all actions complete
                toggle();  // Toggle off the module
                break;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        step = 0;  // Reset steps on disable
        delayCounter = 0;
    }
}
