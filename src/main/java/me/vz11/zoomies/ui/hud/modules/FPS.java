package me.vz11.zoomies.ui.hud.modules;

import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class FPS extends HUDModule
{
	public FPS(int x, int y) 
	{
		super("FPS", x, y);
		this.width = 48;
		this.height = 8;
		this.enabled = true;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer, boolean editMode, boolean enabled) 
	{
		super.render(drawContext, mouseX, mouseY, textRenderer, editMode, enabled);
		drawContext.drawTextWithShadow(mc.textRenderer, ColorUtils.aqua + "FPS: " + ColorUtils.white + mc.fpsDebugString.split(" ")[0], x, y, 0xFF55FFFF);
	}
}
