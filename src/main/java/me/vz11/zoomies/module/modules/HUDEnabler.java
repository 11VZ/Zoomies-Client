package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.IndexSetting;

public class HUDEnabler extends Module 
{
	public IndexSetting mode = new IndexSetting("Mode", 0, "Modular", "Legacy");
    public BooleanSetting mainmenuhud = new BooleanSetting("Main Menu Client Tag", true);
    public static boolean isSettingEnabled;
	
    public HUDEnabler()
    {
        super("HUD", "The Zoomies HUD.", Category.RENDER, false);
        this.enabled = true;
        this.showInModulesList.value = false;
        this.showEditButton = true;
        settings.add(mode);
        settings.add(mainmenuhud);
        if (this.mainmenuhud.value) {
            isSettingEnabled = true;
        } else {
            isSettingEnabled = false;
        }
        
    }    
}
