package me.vz11.zoomies.ui.hud.editor;

import java.util.ArrayList;

import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.ui.hud.HUDModuleManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class HUDModuleList 
{
    public int x, y, height, width = 128;
    int startX, startY;
    boolean dragging = false;
    public boolean collapsed = false;
    ArrayList<HUDModuleButton> buttons;

    public HUDModuleList(int initialX, int initialY, boolean collapsed)
    {
        this.x = initialX;
        this.y = initialY;
        this.collapsed = collapsed;
        buttons = new ArrayList<HUDModuleButton>();
        for (HUDModule m : HUDModuleManager.INSTANCE.modules)
        {
        	buttons.add(new HUDModuleButton(m));
        }
        if (buttons.size() == 0) collapsed = true;
        height = (buttons.size()*12) + 18;
    }

    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta, TextRenderer textRenderer) 
    {
        if (dragging)
        {
            x = mouseX - startX;
            y = mouseY - startY;
        }
        drawContext.fill(x, y, x+width, collapsed ? y+16 : y+height, 0xFF55FFFF);
        drawContext.fill(x+2, y+2, x+(width-2), y+14, hovered(mouseX, mouseY) ? 0xFF333333 : 0xFF222222);
        drawContext.drawGuiTexture(Identifier.of("zoomies", "hud"), x+2, y+2, 12, 12);
        drawContext.drawText(textRenderer, "HUD Modules", x+16, y+4, 0xFFFFFFFF, false);
        if (!collapsed)
        {
            int buttonYOffset = y+16;
            for (HUDModuleButton m : buttons)
            {
                m.render(drawContext, mouseX, mouseY, x+2, buttonYOffset, textRenderer);
                buttonYOffset += 12;
            }
        }
    }

    public boolean hovered(int mouseX, int mouseY) 
    {
		return mouseX >= x+2 && mouseX <= x+(width-2) && mouseY >= y+2 && mouseY <= y+14;
	}

    public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		for (HUDModuleButton moduleButton : buttons)
		{
			if (moduleButton.mouseClicked(mouseX, mouseY, button)) return;
		}
        if (hovered(mouseX, mouseY) && button == 1) collapsed = !collapsed;
        else if (hovered(mouseX, mouseY) && button == 0)
        {
            startX = (int)mouseX-x;
            startY = (int)mouseY-y;
            dragging = true;
        }
	}

    public void mouseReleased(int mouseX, int mouseY, int button) 
	{
		dragging = false;
	}
}
