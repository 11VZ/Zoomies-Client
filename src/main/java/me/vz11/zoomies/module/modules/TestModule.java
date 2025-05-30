package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.StringSetting;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public class TestModule extends Module 
{
    public BooleanSetting bs = new BooleanSetting("BooleanSetting", false);
    public DoubleSetting ds = new DoubleSetting("DoubleSetting", 1, 0, 10, 10);
    public StringSetting ss = new StringSetting("StringSetting", "string");

    public TestModule()
    {
        super("Test Module", "Used for testing module features.", Category.WORLD, false);
        addSettings(bs, ds, ss);
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        System.out.println("Test Module Enabled");
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        System.out.println("Test Module Disabled");
    }

    @Override
    public void onMotion(MovementType type, Vec3d movement)
    {
        System.out.println("Test Module motion");
    }

    @Override
    public void tick()
    {
        System.out.println("Test Module Tick");
    }
}
