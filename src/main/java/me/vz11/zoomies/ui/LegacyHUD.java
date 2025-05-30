package me.vz11.zoomies.ui;

import me.vz11.zoomies.Zoomies;
import me.vz11.zoomies.util.ColorUtils;
import me.vz11.zoomies.util.MathUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec3d;

public class LegacyHUD
{
    public static LegacyHUD INSTANCE = new LegacyHUD();
	private MinecraftClient mc = MinecraftClient.getInstance();

    public void render(DrawContext drawContext, int scaledWidth, int scaledHeight)
    {
    	if (mc.getDebugHud().shouldShowDebugHud()) return;

		drawContext.drawTextWithShadow(mc.textRenderer, "FPS: " + ColorUtils.gray + mc.fpsDebugString.split(" ")[0], 2, 2, 0xFF55FFFF);
		drawContext.drawTextWithShadow(mc.textRenderer, "Ping: " + ColorUtils.gray + (mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()) == null ? 0 : mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()).getLatency()), 2, 12, 0xFF55FFFF);
		drawContext.drawTextWithShadow(mc.textRenderer, "Meters/s: " + ColorUtils.gray + MathUtils.round(moveSpeed(), 2), 2, scaledHeight - 20, 0xFF55FFFF);

        drawContext.drawTextWithShadow(mc.textRenderer, 
            "X: " + ColorUtils.gray + String.format("%.1f", mc.player.getX()) + ColorUtils.reset + 
            " Y: " + ColorUtils.gray + String.format("%.1f", mc.player.getY()) + ColorUtils.reset + 
            " Z: " + ColorUtils.gray + String.format("%.1f", mc.player.getZ()), 2, scaledHeight - 10, 0xFF55FFFF
        );

        drawContext.drawTextWithShadow(mc.textRenderer, Zoomies.clientTag + " " + Zoomies.versionTag, 
        scaledWidth - mc.textRenderer.getWidth(Zoomies.clientTag + " " + Zoomies.versionTag) - 2, scaledHeight - 10, 16777215);
    }

    private double moveSpeed() 
    {
        Vec3d move = new Vec3d(mc.player.getX() - mc.player.prevX, 0, mc.player.getZ() - mc.player.prevZ).multiply(20);

        return Math.abs(MathUtils.length2D(move)) ;
    }
}
