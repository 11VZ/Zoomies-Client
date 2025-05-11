package me.vz11.zoomies;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.command.CommandManager;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.BooleanSetting;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.IndexSetting;
import me.vz11.zoomies.module.settings.KeycodeSetting;
import me.vz11.zoomies.module.settings.Setting;
import me.vz11.zoomies.module.settings.StringSetting;
import me.vz11.zoomies.ui.clickgui.CategoryPane;
import me.vz11.zoomies.ui.clickgui.ClickGUIScreen;
import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.ui.hud.HUDModuleManager;
import me.vz11.zoomies.util.ColorUtils;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.util.KeycodeUtils;
import me.vz11.zoomies.util.AuthUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ChatScreen;

public class Zoomies implements ModInitializer {
	public static final Zoomies INSTANCE = new Zoomies();
	public static final Logger LOGGER = LoggerFactory.getLogger("Zoomies");
	public static final String clientTag = ColorUtils.aqua + "Zoomies";
	public static final String versionTag = ColorUtils.magenta + "v1.3";
	public static Config CONFIG = new Config();
	public static boolean loaded = false;
	private String prefix = new CommandManager().getPrefix();

	@Override
	public void onInitialize() {
		loadConfig();
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;
			
			if (!AuthUtil.isAuthorized()) {
				//client.setScreen(new AuthUtil.UnauthorizedScreen());
				return;
			}

			if (InteractionUtil.isChatOpen()) return;
			prefix = new CommandManager().getPrefix();

			if (KeycodeUtils.isKeyPressed(prefix)) {
				client.setScreen(new ChatScreen(prefix));
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void loadConfig()
	{
		try
		{
			CONFIG.load();
			for (Module m : ModuleManager.INSTANCE.modules)
			{
				m.enabled = (boolean)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("modules")).get(m.name)).get("enabled");
				for (Setting s : m.settings)
				{
                	if (s instanceof BooleanSetting)
                	{
                    	((BooleanSetting)s).value = (boolean)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("modules")).get(m.name)).get("settings")).get(s.name);
                	}
                	else if (s instanceof DoubleSetting)
                	{
                    	((DoubleSetting)s).value = (Double)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("modules")).get(m.name)).get("settings")).get(s.name);
                	}
					else if (s instanceof StringSetting)
                	{
                    	((StringSetting)s).value = (String)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("modules")).get(m.name)).get("settings")).get(s.name);
                	}
					else if (s instanceof KeycodeSetting)
                	{
                	    ((KeycodeSetting)s).value = ((Double)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("modules")).get(m.name)).get("settings")).get(s.name)).intValue();
                	}
					else if (s instanceof IndexSetting)
                	{
                	    ((IndexSetting)s).index = ((Double)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("modules")).get(m.name)).get("settings")).get(s.name)).intValue();
                	}
				}
			}
			for (HUDModule m : HUDModuleManager.INSTANCE.modules)
			{
				m.enabled = (boolean)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("enabled");
				m.x = ((Double)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("x")).intValue();
				m.y = ((Double)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("y")).intValue();
				for (Setting s : m.settings)
				{
                	if (s instanceof BooleanSetting)
                	{
                    	((BooleanSetting)s).value = (boolean)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("settings")).get(s.name);
                	}
                	else if (s instanceof DoubleSetting)
                	{
                    	((DoubleSetting)s).value = (Double)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("settings")).get(s.name);
                	}
					else if (s instanceof StringSetting)
                	{
                    	((StringSetting)s).value = (String)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("settings")).get(s.name);
                	}
					else if (s instanceof KeycodeSetting)
                	{
                	    ((KeycodeSetting)s).value = ((Double)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("settings")).get(s.name)).intValue();
                	}
					else if (s instanceof IndexSetting)
                	{
                	    ((IndexSetting)s).index = ((Double)((Map<String, Object>)((Map<String, Object>)((Map<String, Object>)CONFIG.config.get("hud")).get(m.name)).get("settings")).get(s.name)).intValue();
                	}
				}
			}
			Map<String, Object> general = (Map<String, Object>) CONFIG.config.get("general");
			if (general != null) {
				String savedPrefix = (String) general.get("prefix");
				if (savedPrefix != null) {
					new CommandManager().setPrefix(savedPrefix);
				}
			}
		}
		catch(Exception e)
		{
			CONFIG.loadDefaultConfig(); 
			CONFIG.save();
		}
	}

	public void saveConfig()
	{
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
        CONFIG.config.put("modules", mi);
        
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
        CONFIG.config.put("hud", hi);
        
        Map<String, Object> pi = new HashMap<>();
        for (CategoryPane c : ClickGUIScreen.INSTANCE.categoryPanes)
		{ 
            Map<String, Object> po = new HashMap<>();
			po.put("x", c.x);
            po.put("y", c.y);
			po.put("collapsed", c.collapsed);
            pi.put(c.category.name, po);
		}
        CONFIG.config.put("panes", pi);
		Map<String, Object> general = new HashMap<>();
		general.put("prefix", new CommandManager().getPrefix());
		CONFIG.config.put("general", general);
		
		CONFIG.save();
	}
}
