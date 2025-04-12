package me.vz11.zoomies.ui.hud.modules;

import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class Ping extends HUDModule
{
	public Ping(int x, int y) 
	{
		super("Ping", x, y);
		this.width = 48;
		this.height = 8;
		this.enabled = true;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer, boolean editMode, boolean enabled) 
	{
		PlayerListEntry player;
		super.render(drawContext, mouseX, mouseY, textRenderer, editMode, enabled);
player = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(MinecraftClient.getInstance().player.getUuid());
if (player != null) {
    drawContext.drawTextWithShadow(mc.textRenderer, "Ping: " + ColorUtils.white + (player.getLatency()), x, y, 0xFF55FFFF);
} else {
    drawContext.drawTextWithShadow(mc.textRenderer, "Ping: " + ColorUtils.red + "Unknown", x, y, 0xFF55FFFF);
}
	}
}
