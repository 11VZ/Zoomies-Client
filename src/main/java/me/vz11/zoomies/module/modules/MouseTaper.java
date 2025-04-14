package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class MouseTaper extends Module
{
    public MouseTaper()
    {
        super("Mouse Taper", "Holds left click forever.", Category.PLAYER, false);
    }

    @Override
    public void tick()
    {
        mc.options.attackKey.setPressed(true);
    }
}
