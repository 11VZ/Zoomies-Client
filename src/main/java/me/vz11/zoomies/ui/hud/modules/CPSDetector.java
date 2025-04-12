package me.vz11.zoomies.ui.hud.modules;

import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.util.ClickCounter;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class CPSDetector extends HUDModule {
    public CPSDetector(int x, int y) {
        super("CPS", x, y);
        this.width = 48;
        this.height = 8;
        this.enabled = true;
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer, 
                      boolean editMode, boolean enabled) {
        super.render(drawContext, mouseX, mouseY, textRenderer, editMode, enabled);
        
        int leftCps = ClickCounter.INSTANCE.getLeftCPS();
        int rightCps = ClickCounter.INSTANCE.getRightCPS();
        String cpsText = ColorUtils.aqua + leftCps + ColorUtils.white + " / " + 
                        ColorUtils.aqua + rightCps;
        
        this.width = textRenderer.getWidth(cpsText);
        
        drawContext.drawTextWithShadow(textRenderer, cpsText, x, y, 0xFFFFFF);
    }
}
