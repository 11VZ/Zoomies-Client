package me.vz11.zoomies.module.settings;

import me.vz11.zoomies.util.MathUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class DoubleSetting extends Setting
{
    public double value;
    private double min, max;
    private int roundingPlace;

    boolean sliding = false;

    public DoubleSetting(String name, double value, double min, double max, int roundingPlace)
    {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.roundingPlace = roundingPlace;
    }

    @Override
    public void render(DrawContext drawContext, int x, int y, int mouseX, int mouseY, TextRenderer textRenderer) 
    {
        super.render(drawContext, x, y, mouseX, mouseY, textRenderer);
        drawContext.drawTextWithShadow(textRenderer, Text.literal(name), x+2, y+4, 0xFFFFFF);
        double diff = Math.min(100, Math.max(0, (mouseX - x)/1.9));

        if (sliding) 
        {
			if (diff == 0) 
            {
				value = min;
			}
			else 
            {
				double newValue = MathUtils.round(((diff / 100) * (max - min) + min), roundingPlace);
				value = newValue;
			}
		}

        String valueString = ""+MathUtils.round(value, roundingPlace);
        drawContext.drawTextWithShadow(textRenderer, Text.literal(valueString), (x+190)-textRenderer.getWidth(valueString), y+4, 0xFFFFFF);
        drawContext.fill(x+2, y+16, x+190, y+18, 0xFF666666);
        int scaledValue = (int)((value/max)*190);
        drawContext.fill(x+2, y+16, (x+2)+scaledValue, y+18, 0xFF55FFFF);
        drawContext.fill(x+2+(scaledValue-1), y+14, x+2+(scaledValue+1), y+20, 0xFFFFFFFF);
    }

    @Override
	public void mouseClicked(double mouseX, double mouseY, int button) 
    {
		if (hovered((int)mouseX, (int)mouseY) && button == 0) 
        {
			this.sliding = true;
		}
	}
	
	@Override
	public void mouseReleased(double mouseX, double mouseY, int button) 
    {
		sliding = false;
	}
    
}
