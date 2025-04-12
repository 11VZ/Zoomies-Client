package me.vz11.zoomies.ui.hud.modules;

import me.vz11.zoomies.Zoomies;
import me.vz11.zoomies.ui.hud.HUDModule;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class ClientTag extends HUDModule
{
	public ClientTag(int x, int y) 
	{
		super("Client Tag", x, y);
		this.width = 128;
		this.height = 8;
		this.enabled = true;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer, boolean editMode, boolean enabled) 
	{
		super.render(drawContext, mouseX, mouseY, textRenderer, editMode, enabled);
		drawContext.drawTextWithShadow(mc.textRenderer, Zoomies.clientTag + " " + Zoomies.versionTag, x, y, 16777215);
	}
}
