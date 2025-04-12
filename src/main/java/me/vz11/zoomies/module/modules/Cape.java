package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;

public class Cape extends Module 
{
    public Cape()
    {
        super("Cape", "Enables the custom cape", Category.RENDER);
        this.showInModulesList.value = false;
    }
}
