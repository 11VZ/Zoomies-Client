package me.vz11.zoomies.ui.clickgui;

import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.Setting;
import me.vz11.zoomies.ui.SetScreenButton;
import me.vz11.zoomies.ui.hud.editor.HUDEditorScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class SettingsScreen extends Screen {
    private Module module;
    private SetScreenButton backButton;
    private SetScreenButton editButton;

    private boolean dragging = false;
    private int startX, startY;
    private int x = (ClickGUIScreen.INSTANCE.width / 2) - 112;
    private int y = (ClickGUIScreen.INSTANCE.height / 2) - 96;
    private int windowWidth = 224;
    private int windowHeight = 192;

    public SettingsScreen(Module module) {
        super(Text.literal("Settings"));
        backButton = new SetScreenButton("< Back", x + 8, y + 8, 0xFFFFFF, ClickGUIScreen.INSTANCE);
        editButton = new SetScreenButton("Edit", x + windowWidth - 28, y + 8, 0xFFFFFF, HUDEditorScreen.INSTANCE);
        this.module = module;
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta)
    {
        this.renderBackground(drawContext, mouseX, mouseY, delta);

        if (dragging) {
            x = mouseX - startX;
            y = mouseY - startY;
        }

        int radius = 8;

        drawContext.fill(x + radius, y, x + windowWidth - radius, y + windowHeight, 0xFF222222);
        drawContext.fill(x, y + radius, x + windowWidth, y + windowHeight - radius, 0xFF222222);

        drawCircle(drawContext, x + radius, y + radius, radius, 0xFF222222); // Top-left
        drawCircle(drawContext, x + windowWidth - radius, y + radius, radius, 0xFF222222); // Top-right
        drawCircle(drawContext, x + radius, y + windowHeight - radius, radius, 0xFF222222); // Bottom-left
        drawCircle(drawContext, x + windowWidth - radius, y + windowHeight - radius, radius, 0xFF222222); // Bottom-right

        drawContext.fill(x + radius, y, x + windowWidth - radius, y + 16, 0xFF222222);
        drawContext.drawCenteredTextWithShadow(textRenderer, module.name, x + (windowWidth / 2), y + 4, 0xFFFFFF);

        drawContext.drawText(textRenderer, module.description, x + 8, y + 24, 0xFFFFFF, true);

        backButton.render(drawContext, textRenderer, mouseX, mouseY, x + 4, y + 4);
        if (module.showEditButton) editButton.render(drawContext, textRenderer, mouseX, mouseY, x + windowWidth - 22, y + 4);

        int yOffset = y + 40;
        for (Setting setting : module.settings) {
            setting.render(drawContext, x + 16, yOffset, mouseX, mouseY, textRenderer);
            yOffset += 25;
        }
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


    public boolean barHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + windowWidth && mouseY >= y && mouseY <= y + 16;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (barHovered((int) mouseX, (int) mouseY)) {
            startX = (int) mouseX - x;
            startY = (int) mouseY - y;
            dragging = true;
        }
        backButton.mouseClicked((int) mouseX, (int) mouseY);
        editButton.mouseClicked((int) mouseX, (int) mouseY);
        for (Setting setting : module.settings) {
            setting.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        for (Setting setting : module.settings) {
            setting.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Setting setting : module.settings) {
            setting.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(ClickGUIScreen.INSTANCE);
    }
}
