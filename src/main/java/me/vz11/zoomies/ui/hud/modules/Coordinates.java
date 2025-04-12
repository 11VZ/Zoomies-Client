package me.vz11.zoomies.ui.hud.modules;

import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class Coordinates extends HUDModule
{
	public Coordinates(int x, int y) 
	{
		super("Coordinates", x, y);
		this.width = 128;
		this.height = 8;
		this.enabled = true;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer, boolean editMode, boolean enabled) 
	{
		super.render(drawContext, mouseX, mouseY, textRenderer, editMode, enabled);
		drawContext.drawTextWithShadow(mc.textRenderer, 
	            "X: " + ColorUtils.white + String.format("%.1f", mc.player.getX()) + ColorUtils.reset + 
	            " Y: " + ColorUtils.white + String.format("%.1f", mc.player.getY()) + ColorUtils.reset + 
	            " Z: " + ColorUtils.white + String.format("%.1f", mc.player.getZ()), x, y, 0xFF55FFFF
	        );
	}
}
