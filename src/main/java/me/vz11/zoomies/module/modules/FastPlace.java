package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;

public class FastPlace extends Module {

    public FastPlace() {
        super("Fastplace", "Places blocks really fast", Category.COMBAT);
    }

    @Override
    public void tick() {
        if (InteractionUtil.inputActive("right")) {
            InteractionUtil.rightClick();
        }
    }
}
