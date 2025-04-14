package me.vz11.zoomies.ui.clickgui;

import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class ModuleButton {
	public Module module;
	public int x, y, width, height = 16;

	public ModuleButton(Module module) {
		this.module = module;
		this.width = 88;
	}

	public void render(DrawContext drawContext, int mouseX, int mouseY, int x, int y, TextRenderer textRenderer) {
        this.x = x;
        this.y = y;
        
        if (!ModuleManager.isBlatantMode() && module.blatant) return;
        
		int backgroundColor = hovered(mouseX, mouseY) ? 0xFF444444 : 0xFF333333;

		drawContext.fill(x + 2, y, x + width - 3, y + height, backgroundColor);
		drawContext.fill(x, y + 3, x + width, y + height - 3, backgroundColor);

		drawContext.fill(x + 2, y + 1, x + width - 2, y + 3, backgroundColor);
		drawContext.fill(x + 2, y + height - 3, x + width - 2, y + height - 1, backgroundColor);
		drawContext.fill(x + 1, y + 2, x + 3, y + height - 2, backgroundColor);
		drawContext.fill(x + width - 3, y + 2, x + width - 1, y + height - 2, backgroundColor);

		drawContext.drawText(textRenderer, module.name, x + 5, y + 4, module.enabled ? 0x55FFFF : 0xFFFFFF, false);
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
