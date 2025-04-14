package me.vz11.zoomies.ui.hud;

import java.util.ArrayList;

import me.vz11.zoomies.ui.hud.modules.*;

public class HUDModuleManager 
{
	public static HUDModuleManager INSTANCE = new HUDModuleManager();
    public ArrayList<HUDModule> modules = new ArrayList<>();

	public HUDModuleManager() 
	{
		registerModules(
			new ClientTag(2, 2),
			new FPS(2, 12),
			new CPSDetector(2, 22),
			new TPS(2, 32),
			new Ping(2, 42),
			new ServerInfoHUD(2, 72),
			new Coordinates(2, 62),
			new MovementSpeed(2, 52),
			new ArmorDisplay(2, 82),
			new TotemCounter(20, 82)
		);
	}
	
	private void registerModules(HUDModule... modules) 
	{
		for (HUDModule module : modules) {
			this.modules.add(module);
		}
	}

    public HUDModule getModuleByName(String moduleName) 
	{
		for(HUDModule module : modules) 
		{
			if ((module.name.trim().equalsIgnoreCase(moduleName)))
			{
				return module;
			}
		}
		return null;
	}
    
    public ArrayList<HUDModule> getEnabledModules() 
	{
		ArrayList<HUDModule> enabledModules = new ArrayList<>();
		for (HUDModule module : modules) 
		{
			if (!module.enabled)
				continue;
			enabledModules.add(module);
		}
		return enabledModules;
	}
}
