package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;

public class FPSSpoofer extends Module {
    public static FPSSpoofer INSTANCE;
    
    public final BooleanSetting enabled = new BooleanSetting("Enabled", true);
    public final DoubleSetting fakeFps = new DoubleSetting("Fake FPS", 420, 0, 1000, 0);
    public final BooleanSetting fluctuate = new BooleanSetting("Fluctuate", false);
    public final DoubleSetting fluctuateAmount = new DoubleSetting("Fluct Amount", 20, 0, 100, 0);

    public FPSSpoofer() {
        super("FPS Spoofer", "Sets your FPS to a number", Category.RENDER);
        settings.add(enabled);
        settings.add(fakeFps);
        settings.add(fluctuate);
        settings.add(fluctuateAmount);
        INSTANCE = this;
    }
}
