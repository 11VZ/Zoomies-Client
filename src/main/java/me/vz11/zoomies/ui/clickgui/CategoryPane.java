package me.vz11.zoomies.ui.clickgui;

import java.util.ArrayList;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.MathUtils;
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
    private float animationProgress = 0f;
    private static final float ANIMATION_SPEED = 0.5f;
    private float dragX = 0f;
    private float dragY = 0f;
    private static final float DRAG_SMOOTHING = 0.3f;
    private int targetX, targetY;

    public CategoryPane(Category category, int initialX, int initialY, boolean collapsed) {
        this.category = category;
        this.x = initialX;
        this.y = initialY;
        this.targetX = initialX;  
        this.targetY = initialY;
        this.collapsed = collapsed;
        this.animationProgress = collapsed ? 0f : 1f;
        moduleButtons = new ArrayList<>();
        icon = Identifier.of("zoomies", category.name.toLowerCase());
        for (Module m : ModuleManager.INSTANCE.getModulesByCategory(category)) {
            moduleButtons.add(new ModuleButton(m));
        }
        if (moduleButtons.size() == 0) collapsed = true;
        height = (moduleButtons.size() * 16) + 24;
    }

    public void renderRoundedRect(DrawContext drawContext, int x, int y, int width, int height, int color, int radius) {
        int shadowColor = 0x40000000;
        drawContext.fill(x + 2, y + 2, x + width - 2, y + height, shadowColor);
        
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

    private boolean isMousePressed() {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
    }

    private boolean isDragging = false;
    private int dragOffsetX, dragOffsetY;

    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta, TextRenderer textRenderer) {
        float targetProgress = collapsed ? 0f : 1f;
        if (animationProgress != targetProgress) {
            animationProgress = MathUtils.lerp(animationProgress, targetProgress, ANIMATION_SPEED);
        }

        if (isDragging) {
            targetX = mouseX - dragOffsetX;
            targetY = mouseY - dragOffsetY;
        }
        
        if (x != targetX || y != targetY) {
            x = (int) MathUtils.lerp(x, targetX, DRAG_SMOOTHING);
            y = (int) MathUtils.lerp(y, targetY, DRAG_SMOOTHING);
        }

        int backgroundColor = 0xFF1A1A1A;
        int highlightColor = hovered(mouseX, mouseY) ? 0xFF2D2D2D : 0xFF252525;
        int borderColor = 0xFF3D3D3D;

        if (isMousePressed()) {
            onMousePressed(mouseX, mouseY);
        } else {
            onMouseReleased();
        }

        int currentHeight = (int) (20 + (height - 20) * animationProgress);

        renderRoundedRect(drawContext, x, y, width, currentHeight, backgroundColor, 5);
        
        renderRoundedRect(drawContext, x + 2, y + 2, width - 4, 16, highlightColor, 5);
        
        drawContext.fill(x + 1, y + 1, x + width - 1, y + 2, borderColor);
        drawContext.fill(x + 1, y + currentHeight - 2, x + width - 1, y + currentHeight - 1, borderColor);
        drawContext.fill(x + 1, y + 2, x + 2, y + currentHeight - 2, borderColor);
        drawContext.fill(x + width - 2, y + 2, x + width - 1, y + currentHeight - 2, borderColor);

        drawContext.drawGuiTexture(icon, x + 4, y + 4, 12, 12);
        drawContext.drawText(textRenderer, category.name, x + 20, y + 4, 0xFFFFFFFF, false);

        String indicator = collapsed ? "▼" : "▲";
        drawContext.drawText(textRenderer, indicator, x + width - 16, y + 4, 0xFFFFFFFF, false);

        if (!collapsed && animationProgress > 0.99f) {
            int buttonYOffset = y + 20;
            for (ModuleButton m : moduleButtons) {
                if (!ModuleManager.isBlatantMode() && m.module.blatant) continue;
                m.render(drawContext, mouseX, mouseY, x + 4, buttonYOffset, textRenderer);
                buttonYOffset += 16;
            }
        }
    }

    public void renderTooltips(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer) {
        if (!collapsed && animationProgress > 0.99f) {
            for (ModuleButton m : moduleButtons) {
                if (!ModuleManager.isBlatantMode() && m.module.blatant) continue;
                m.renderTooltip(drawContext, mouseX, mouseY, textRenderer);
            }
        }
    }

    public void onMousePressed(int mouseX, int mouseY) {
        if (hovered(mouseX, mouseY)) {
            isDragging = true;
            dragOffsetX = mouseX - x;
            dragOffsetY = mouseY - y;
            targetX = x;
            targetY = y;
        }
    }

    public void onMouseReleased() {
        isDragging = false;
    }

    public void onMouseDragged(int mouseX, int mouseY) {
        if (isDragging) {
            targetX = mouseX - dragOffsetX;
            targetY = mouseY - dragOffsetY;
        }
    }

    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= x + 2 && mouseX <= x + (width - 2) && mouseY >= y + 2 && mouseY <= y + 18;
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (ModuleButton moduleButton : moduleButtons) {
            if (moduleButton.mouseClicked(mouseX, mouseY, button)) return;
        }
        if (hovered(mouseX, mouseY)) {
            if (button == 1) {
                collapsed = !collapsed;
            } else if (button == 0) {
                startX = mouseX - x;
                startY = mouseY - y;
                dragging = true;
                targetX = x;
                targetY = y;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
        dragging = false;
    }
}
