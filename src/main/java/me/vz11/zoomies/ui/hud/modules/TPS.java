package me.vz11.zoomies.ui.hud.modules;

import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.util.ColorUtils;
import me.vz11.zoomies.util.TickRateUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class TPS extends HUDModule
{
	public TPS(int x, int y) 
	{
		super("TPS", x, y);
		this.width = 48;
		this.height = 8;
		this.enabled = true;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer, boolean editMode, boolean enabled) 
	{
		super.render(drawContext, mouseX, mouseY, textRenderer, editMode, enabled);
		float tps = TickRateUtil.INSTANCE.getTickRate();
		
		drawContext.drawTextWithShadow(textRenderer,
			ColorUtils.aqua + "TPS: " + ColorUtils.white + String.format("%.1f", tps),
			x, y, 0xFFFFFF
		);
	}
}
