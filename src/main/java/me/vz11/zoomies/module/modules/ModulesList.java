package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class ModulesList extends Module 
{
    public ModulesList()
    {
        super("ModulesList", "Shows enabled modules on side of screen", Category.RENDER);
        this.enabled = true;
        this.showInModulesList.value = false;
    }
}
