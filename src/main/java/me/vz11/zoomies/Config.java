package me.vz11.zoomies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import me.vz11.zoomies.command.CommandManager;
import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.IndexSetting;
import me.vz11.zoomies.module.settings.KeycodeSetting;
import me.vz11.zoomies.module.settings.Setting;
import me.vz11.zoomies.module.settings.StringSetting;
import me.vz11.zoomies.ui.clickgui.ClickGUIScreen;
import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.ui.hud.HUDModuleManager;
import net.minecraft.client.MinecraftClient;

public class Config 
{
    MinecraftClient mc = MinecraftClient.getInstance();
    public File configDir = new File(mc.runDirectory.getPath() + "/11VZ");
    public File configFile = new File(configDir, "config.json");
    public Map<String, Object> config = new HashMap<>();
    protected static Gson gson = new Gson();
    
    public Config()
    {
        configDir.mkdirs();
    }

    public boolean doesConfigExist()
    {
        return !Files.exists(configFile.toPath());
    }

    public void loadDefaultConfig()
    {
        ModuleManager.INSTANCE = new ModuleManager();
        Map<String, Object> mi = new HashMap<>();
        for (Module m : ModuleManager.INSTANCE.modules)
		{
			Map<String, Object> mo = new HashMap<>();
            mo.put("enabled", m.enabled);
            Map<String, Object> ms = new HashMap<>();
			for (Setting s : m.settings)
			{
                if (s instanceof BooleanSetting)
                {
                    ms.put(s.name, ((BooleanSetting)s).value);
                }
                else if (s instanceof DoubleSetting)
                {
                    ms.put(s.name, ((DoubleSetting)s).value);
                }
                else if (s instanceof StringSetting)
                {
                    ms.put(s.name, ((StringSetting)s).value);
                }
                else if (s instanceof KeycodeSetting)
                {
                    ms.put(s.name, ((KeycodeSetting)s).value);
                }
                else if (s instanceof IndexSetting)
                {
                    ms.put(s.name, ((IndexSetting)s).index);
                }
			}
            mo.put("settings", ms);
            mi.put(m.name, mo);
		}
        config.put("modules", mi);
        
        HUDModuleManager.INSTANCE = new HUDModuleManager();
        Map<String, Object> hi = new HashMap<>();
        for (HUDModule h : HUDModuleManager.INSTANCE.modules)
		{
			Map<String, Object> ho = new HashMap<>();
            ho.put("enabled", h.enabled);
            Map<String, Object> hs = new HashMap<>();
			for (Setting s : h.settings)
			{
                if (s instanceof BooleanSetting)
                {
                    hs.put(s.name, ((BooleanSetting)s).value);
                }
                else if (s instanceof DoubleSetting)
                {
                    hs.put(s.name, ((DoubleSetting)s).value);
                }
                else if (s instanceof StringSetting)
                {
                    hs.put(s.name, ((StringSetting)s).value);
                }
                else if (s instanceof KeycodeSetting)
                {
                    hs.put(s.name, ((KeycodeSetting)s).value);
                }
                else if (s instanceof IndexSetting)
                {
                    hs.put(s.name, ((IndexSetting)s).index);
                }
			}
            ho.put("settings", hs);
            ho.put("x", h.x);
            ho.put("y", h.y);
            hi.put(h.name, ho);
		}
        config.put("hud", hi);
        int xOffset = 4;
		int yOffset = 4;
        Map<String, Object> pi = new HashMap<>();
        for (Category category : Category.values())
		{ 
            Map<String, Object> po = new HashMap<>();
			if (xOffset > 400)
			{
				xOffset = 4;
				yOffset = 128;
			}
			po.put("x", xOffset);
            po.put("y", yOffset);
            po.put("collapsed", false);
            pi.put(category.name, po);
			xOffset += 100;
		}
        config.put("panes", pi);
        ClickGUIScreen.INSTANCE = new ClickGUIScreen();
        
        Map<String, Object> general = new HashMap<>();
        general.put("prefix", new CommandManager().getPrefix());
        config.put("general", general);
    }

    @SuppressWarnings("unchecked")
	public void load() throws IOException
    {
        try
        {
            String configText = new String(Files.readAllBytes(configFile.toPath()));
            config = (Map<String, Object>)gson.fromJson(configText, Map.class);
        }
        catch (Exception e)
        {
            throw new IOException("Failed to load config file.");
        }
    }

    public void save()
    {
        try
        { 
            if (!doesConfigExist()) configFile.createNewFile(); 
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
        try
        {
            String configText = gson.toJson(config);
            FileWriter writer = new FileWriter(configFile);
            writer.write(configText);
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
