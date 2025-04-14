package me.vz11.zoomies.ui.clickgui;

import java.util.ArrayList;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class CategoryPane {
    public Category category;
    public int x, y, height, width = 96;
    int startX, startY;
    boolean dragging = false;
    public boolean collapsed = false;
    ArrayList<ModuleButton> moduleButtons;
    Identifier icon;

    public CategoryPane(Category category, int initialX, int initialY, boolean collapsed) {
        this.category = category;
        this.x = initialX;
        this.y = initialY;
        this.collapsed = collapsed;
        moduleButtons = new ArrayList<>();
        icon = Identifier.of("zoomies", category.name.toLowerCase());
        for (Module m : ModuleManager.INSTANCE.getModulesByCategory(category)) {
            moduleButtons.add(new ModuleButton(m));
        }
        if (moduleButtons.size() == 0) collapsed = true;
        height = (moduleButtons.size() * 16) + 24;
    }


    public void renderRoundedRect(DrawContext drawContext, int x, int y, int width, int height, int color, int radius) {
        drawContext.fill(x + radius, y, x + width - radius, y + height, color); // Center rectangle
        drawContext.fill(x, y + radius, x + radius, y + height - radius, color); // Left rectangle
        drawContext.fill(x + width - radius, y + radius, x + width, y + height - radius, color); // Right rectangle

        drawFilledCircle(drawContext, x + radius, y + radius, radius, color); // Top-left
        drawFilledCircle(drawContext, x + width - radius - 1, y + radius, radius, color); // Top-right
        drawFilledCircle(drawContext, x + radius, y + height - radius - 1, radius, color); // Bottom-left
        drawFilledCircle(drawContext, x + width - radius - 1, y + height - radius - 1, radius, color); // Bottom-right
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

    private boolean isMousePressed() {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
    }

    private boolean isDragging = false;
    private int dragOffsetX, dragOffsetY;

    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta, TextRenderer textRenderer) {
        int backgroundColor = 0xFF222222;
        int highlightColor = hovered(mouseX, mouseY) ? 0xFF444444 : 0xFF333333;

        if (isMousePressed()) {
            onMousePressed(mouseX, mouseY);
        } else {
            onMouseReleased();
        }

        if (isDragging) {
            onMouseDragged(mouseX, mouseY);
        }

        renderRoundedRect(drawContext, x, y, width, collapsed ? 20 : height, backgroundColor, 5);
        renderRoundedRect(drawContext, x + 2, y + 2, width - 4, 16, highlightColor, 5);

        drawContext.drawGuiTexture(icon, x + 4, y + 4, 12, 12);
        drawContext.drawText(textRenderer, category.name, x + 20, y + 4, 0xFFFFFFFF, false);

        if (!collapsed) {
            int buttonYOffset = y + 20;
            for (ModuleButton m : moduleButtons) {
                if (!ModuleManager.isBlatantMode() && m.module.blatant) continue;
                m.render(drawContext, mouseX, mouseY, x + 4, buttonYOffset, textRenderer);
                buttonYOffset += 16;
            }
        }
    }

    public void onMousePressed(int mouseX, int mouseY) {
        if (hovered(mouseX, mouseY)) {
            isDragging = true;
            dragOffsetX = mouseX - x;
            dragOffsetY = mouseY - y;
        }
    }

    public void onMouseReleased() {
        isDragging = false;
    }

    public void onMouseDragged(int mouseX, int mouseY) {
        if (isDragging) {
            this.x = mouseX - dragOffsetX;
            this.y = mouseY - dragOffsetY;
        }
    }


    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= x + 2 && mouseX <= x + (width - 2) && mouseY >= y + 2 && mouseY <= y + 18;
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (ModuleButton moduleButton : moduleButtons) {
            if (moduleButton.mouseClicked(mouseX, mouseY, button)) return;
        }
        if (hovered(mouseX, mouseY) && button == 1) collapsed = !collapsed;
        else if (hovered(mouseX, mouseY) && button == 0) {
            startX = mouseX - x;
            startY = mouseY - y;
            dragging = true;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
        dragging = false;
    }
}
