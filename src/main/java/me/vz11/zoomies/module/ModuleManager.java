package me.vz11.zoomies.module;

import java.util.ArrayList;

import me.vz11.zoomies.module.modules.*;

public class ModuleManager 
{
    public static ModuleManager INSTANCE = new ModuleManager();
    public ArrayList<Module> modules = new ArrayList<>();
	public static boolean blatantMode = true;

    public ModuleManager()
    {
        registerModules(
			new Fly(),
			new NoFall(),
			new HUDEnabler(),
			new Step(),
			new Fullbright(),
			new Speed(),
			new ModulesList(),
			new ChatSpammer(),
			new Reach(),
			new AntiPowderSnow(),
			new AutoTotem(),
			new NoJumpDelay(),
			new Sprint(),
			new PearlMacro(),
			new FastXP(),
			new Airplace(),
			new FastPlace(),
			new QuickFall(),
			new MaceBoost(),
			new FastMine(),
			new Cape(),
			new Airjump(),
			new Triggerbot(),
			new FPSSpoofer(),
			new Airwalk(),
			new AutoAFK(),
			new MouseTaper(),
			new AutoRespawn(),
			new AutoRekit(),
			new CW()
		);
    }
    
    public static boolean isBlatantMode() {
        return blatantMode;
    }
    
    public static void toggleBlatantMode() {
        blatantMode = !blatantMode;
    }
	
	private void registerModules(Module... modules) 
	{
		for (Module module : modules) {
			this.modules.add(module);
		}
	}

    public Module getModuleByName(String moduleName) 
	{
		for(Module module : modules) 
		{
			if ((module.name.trim().equalsIgnoreCase(moduleName)))
			{
				return module;
			}
		}
		return null;
	}

	public ArrayList<Module> getModulesByCategory(Category category)
	{
		ArrayList<Module> returnedModules = new ArrayList<>();
		for(Module module : modules) 
		{
			if (module.category == category) 
			{
				returnedModules.add(module);
			}
		}
		return returnedModules;
	}

	public ArrayList<Module> getEnabledModules() 
	{
		ArrayList<Module> enabledModules = new ArrayList<>();
		for (Module module : modules) 
		{
			if (!module.enabled)
				continue;
			enabledModules.add(module);
		}
		return enabledModules;
	}
}
