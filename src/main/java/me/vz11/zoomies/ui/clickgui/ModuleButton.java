package me.vz11.zoomies.ui.clickgui;

import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.util.MathUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import com.mojang.blaze3d.systems.RenderSystem;

public class ModuleButton {
	public Module module;
	public int x, y, width, height = 16;
	private float hoverProgress = 0f;
	private static final float HOVER_SPEED = 0.7f;
	private static final int TOOLTIP_PADDING = 4;
	private static final int TOOLTIP_MAX_WIDTH = 400;

	public ModuleButton(Module module) {
		this.module = module;
		this.width = 88;
	}

	public void render(DrawContext drawContext, int mouseX, int mouseY, int x, int y, TextRenderer textRenderer) {
		this.x = x;
		this.y = y;
		
		if (!ModuleManager.isBlatantMode() && module.blatant) return;
		
		float targetHover = hovered(mouseX, mouseY) ? 1f : 0f;
		if (hoverProgress != targetHover) {
			hoverProgress = MathUtils.lerp(hoverProgress, targetHover, HOVER_SPEED);
		}

		int baseColor = 0xFF2A2A2A;
		int hoverColor = 0xFF3A3A3A;
		int enabledColor = 0xFF4CAF50;
		int disabledColor = 0xFFE53935;
		
		int backgroundColor = interpolateColor(baseColor, hoverColor, hoverProgress);
		
		drawRoundedButton(drawContext, x, y, width, height, backgroundColor);
		
		int statusColor = module.enabled ? enabledColor : disabledColor;
		drawContext.fill(x + 2, y + 2, x + 4, y + height - 2, statusColor);
		
		int textColor = module.enabled ? 0xFFFFFFFF : 0xFFAAAAAA;
		drawContext.drawText(textRenderer, module.name, x + 8, y + 4, textColor, false);
	}

	public void renderTooltip(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer) {
		if (!ModuleManager.isBlatantMode() && module.blatant) return;
		
		if (hovered(mouseX, mouseY)) {
			String description = module.description;
			int tooltipWidth = Math.min(textRenderer.getWidth(description), TOOLTIP_MAX_WIDTH);
			int tooltipHeight = textRenderer.fontHeight + TOOLTIP_PADDING * 2;
			
			drawContext.fill(
				x + width + 5,
				y,
				x + width + 5 + tooltipWidth + TOOLTIP_PADDING * 2,
				y + tooltipHeight,
				0xFF000000);
			
			int statusColor = module.enabled ? 0xFF4CAF50 : 0xFFE53935;
			drawContext.fill(
				x + width + 5,
				y,
				x + width + 6,
				y + tooltipHeight,
				statusColor);
			
			drawContext.drawText(
				textRenderer,
				description,
				x + width + 5 + TOOLTIP_PADDING,
				y + TOOLTIP_PADDING,
				0xFFFFFFFF,
				false);
		}
	}

	private void drawRoundedButton(DrawContext drawContext, int x, int y, int width, int height, int color) {
		int radius = 3;
		
		drawContext.fill(x + radius, y, x + width - radius, y + height, color);
		drawContext.fill(x, y + radius, x + radius, y + height - radius, color);
		drawContext.fill(x + width - radius, y + radius, x + width, y + height - radius, color);
		
		drawFilledCircle(drawContext, x + radius, y + radius, radius, color);
		drawFilledCircle(drawContext, x + width - radius - 1, y + radius, radius, color);
		drawFilledCircle(drawContext, x + radius, y + height - radius - 1, radius, color);
		drawFilledCircle(drawContext, x + width - radius - 1, y + height - radius - 1, radius, color);
	}

	private void drawFilledCircle(DrawContext drawContext, int centerX, int centerY, int radius, int color) {
		int squareRadius = radius * radius;
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				if (x * x + y * y <= squareRadius) {
					drawContext.fill(centerX + x, centerY + y, centerX + x + 1, centerY + y + 1, color);
				}
			}
		}
	}

	private int interpolateColor(int color1, int color2, float factor) {
		int r1 = (color1 >> 16) & 0xFF;
		int g1 = (color1 >> 8) & 0xFF;
		int b1 = color1 & 0xFF;
		
		int r2 = (color2 >> 16) & 0xFF;
		int g2 = (color2 >> 8) & 0xFF;
		int b2 = color2 & 0xFF;
		
		int r = (int) (r1 + (r2 - r1) * factor);
		int g = (int) (g1 + (g2 - g1) * factor);
		int b = (int) (b1 + (b2 - b1) * factor);
		
		return (0xFF << 24) | (r << 16) | (g << 8) | b;
	}

	public boolean hovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

	public boolean mouseClicked(int mouseX, int mouseY, int button) {
		if (!ModuleManager.isBlatantMode() && module.blatant) return false;
		
		if (hovered(mouseX, mouseY)) {
			if (button == 0) {
				module.toggle();
			} else if (button == 1) {
				MinecraftClient.getInstance().setScreen(new SettingsScreen(module));
			}
			return true;
		}
		return false;
	}
}
