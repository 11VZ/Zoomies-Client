package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.ISimpleOption;

public class Fullbright extends Module 
{
    public Fullbright()
    {
        super("Fullbright", "Allows you to see in the dark.", Category.RENDER);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void onEnable()
    {
        super.onEnable();
        ((ISimpleOption<Double>)(Object)mc.options.getGamma()).setValueUnrestricted(100.0);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        mc.options.getGamma().setValue(1.0);
    }
}
