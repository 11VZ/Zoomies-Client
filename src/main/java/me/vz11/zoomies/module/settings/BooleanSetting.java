package me.vz11.zoomies.module.settings;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class BooleanSetting extends Setting {
    public boolean value;

    public BooleanSetting(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void render(DrawContext drawContext, int x, int y, int mouseX, int mouseY, TextRenderer textRenderer) {
        super.render(drawContext, x, y, mouseX, mouseY, textRenderer);
        drawContext.drawTextWithShadow(textRenderer, Text.literal(name), x + 8, y + 10, 0xFFFFFF);

        int toggleX = x + 170;
        int toggleY = y + 8;

        drawRectangleWithRoundedCorners(drawContext, toggleX, toggleY, toggleX + 28, toggleY + 12, 6, 0xFF555555);

        if (value) {
            drawRectangleWithRoundedCorners(drawContext, toggleX + 16, toggleY, toggleX + 28, toggleY + 12, 6, 0xFF55FFFF);
        } else {
            drawRectangleWithRoundedCorners(drawContext, toggleX, toggleY, toggleX + 12, toggleY + 12, 6, 0xFF777777);
        }
    }

    private void drawRectangleWithRoundedCorners(DrawContext drawContext, int x1, int y1, int x2, int y2, int radius, int color) {
        drawContext.fill(x1 + radius, y1, x2 - radius, y2, color);
        drawContext.fill(x1, y1 + radius, x2, y2 - radius, color);

        drawCircle(drawContext, x1 + radius, y1 + radius, radius, color); // Top-left
        drawCircle(drawContext, x2 - radius, y1 + radius, radius, color); // Top-right
        drawCircle(drawContext, x1 + radius, y2 - radius, radius, color); // Bottom-left
        drawCircle(drawContext, x2 - radius, y2 - radius, radius, color); // Bottom-right
    }

    private void drawCircle(DrawContext drawContext, int centerX, int centerY, int radius, int color) {
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    drawContext.fill(centerX + x, centerY + y, centerX + x + 1, centerY + y + 1, color);
                }
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (hovered((int) mouseX, (int) mouseY) && button == 0) {
            this.value = !value;
        }
    }
}
