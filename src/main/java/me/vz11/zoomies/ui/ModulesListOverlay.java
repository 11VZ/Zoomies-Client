package me.vz11.zoomies.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

public class ModulesListOverlay
{	
    public static ModulesListOverlay INSTANCE = new ModulesListOverlay();
    private MinecraftClient mc = MinecraftClient.getInstance();
    private ArrayList<Module> enabledModules = ModuleManager.INSTANCE.getEnabledModules();
    private Map<Module, Float> moduleAnimations = new HashMap<>();
    private static final float ANIMATION_SPEED = 0.2f;
    private static final int PADDING = 1;
    private static final int MODULE_HEIGHT = 14;
    private static final int ACCENT_WIDTH = 2;
    private static final int TOOLTIP_PADDING = 4;
    private static final int TOOLTIP_MAX_WIDTH = 400;

    public void render(DrawContext drawContext, int scaledWidth, int scaledHeight)
    {
        if (mc.getDebugHud().shouldShowDebugHud()) return;
        
        int yOffset = PADDING;
        Module hoveredModule = null;
        
        for (Module m : enabledModules)
        {
            if (!m.showInModulesList.value) continue;
            
            // Update animation
            float targetAlpha = m.enabled ? 1.0f : 0.0f;
            float currentAlpha = moduleAnimations.getOrDefault(m, 0.0f);
            float newAlpha = MathHelper.lerp(ANIMATION_SPEED, currentAlpha, targetAlpha);
            moduleAnimations.put(m, newAlpha);
            
            if (newAlpha < 0.01f) continue;
            
            // Draw module
            int nameWidth = mc.textRenderer.getWidth(m.name);
            int totalWidth = nameWidth + PADDING * 2;
            
            // Draw background
            drawContext.fill(
                scaledWidth - totalWidth - PADDING * 2, 
                yOffset, 
                scaledWidth, 
                yOffset + MODULE_HEIGHT, 
                0x77222222);
            
            // Draw accent line on the right
            drawContext.fill(
                scaledWidth - ACCENT_WIDTH, 
                yOffset, 
                scaledWidth, 
                yOffset + MODULE_HEIGHT, 
                getCategoryColor(m.category));
            
            // Draw module name
            drawContext.drawText(
                mc.textRenderer, 
                m.name, 
                scaledWidth - totalWidth, 
                yOffset + 3, 
                0xFFFFFFFF, 
                false);
            
            // Check if mouse is hovering over this module
            double mouseX = mc.mouse.getX() * scaledWidth / mc.getWindow().getWidth();
            double mouseY = mc.mouse.getY() * scaledHeight / mc.getWindow().getHeight();
            if (mouseX >= scaledWidth - totalWidth - PADDING * 2 && 
                mouseX <= scaledWidth && 
                mouseY >= yOffset && 
                mouseY <= yOffset + MODULE_HEIGHT) {
                hoveredModule = m;
            }
            
            yOffset += MODULE_HEIGHT;
        }
        
        // Draw tooltip if hovering over a module
        if (hoveredModule != null) {
            String description = hoveredModule.description;
            int tooltipWidth = Math.min(mc.textRenderer.getWidth(description), TOOLTIP_MAX_WIDTH);
            int tooltipHeight = mc.textRenderer.fontHeight + TOOLTIP_PADDING * 2;
            
            // Draw tooltip background
            drawContext.fill(
                scaledWidth - tooltipWidth - TOOLTIP_PADDING * 2,
                yOffset,
                scaledWidth,
                yOffset + tooltipHeight,
                0xFF000000);
            
            // Draw tooltip border
            drawContext.fill(
                scaledWidth - tooltipWidth - TOOLTIP_PADDING * 2,
                yOffset,
                scaledWidth - tooltipWidth - TOOLTIP_PADDING * 2 + 1,
                yOffset + tooltipHeight,
                getCategoryColor(hoveredModule.category));
            
            // Draw tooltip text
            drawContext.drawText(
                mc.textRenderer,
                description,
                scaledWidth - tooltipWidth - TOOLTIP_PADDING,
                yOffset + TOOLTIP_PADDING,
                0xFFFFFFFF,
                false);
        }
    }

    private int getCategoryColor(Category category) {
        return switch (category) {
            case COMBAT -> 0xFFFF5555;
            case MOVEMENT -> 0xFF55FF55;
            case RENDER -> 0xFF5555FF;
            case PLAYER -> 0xFFFFFF55;
            case WORLD -> 0xFFFF55FF;
            default -> 0xFF55FFFF;
        };
    }

    public void update()
    {
        enabledModules = ModuleManager.INSTANCE.getEnabledModules();
    }
}
