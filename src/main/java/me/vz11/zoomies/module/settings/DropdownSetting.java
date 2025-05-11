package me.vz11.zoomies.module.settings;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class DropdownSetting extends Setting {
    public String value;
    public final String[] options;

    public DropdownSetting(String name, String defaultValue, String[] options) {
        this.name = name;
        this.value = defaultValue;
        this.options = options;
    }

    public void setValue(String value) {
        for (String option : options) {
            if (option.equals(value)) {
                this.value = value;
                break;
            }
        }
    }

    public int getIndex() {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(value)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void render(DrawContext drawContext, int x, int y, int mouseX, int mouseY, TextRenderer textRenderer) {
        super.render(drawContext, x, y, mouseX, mouseY, textRenderer);
        drawContext.drawText(textRenderer, name + ": " + value, x + 5, y + 8, -1, false);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (hovered((int)mouseX, (int)mouseY)) {
            int currentIndex = getIndex();
            if (button == 0) {
                currentIndex = (currentIndex + 1) % options.length;
            } else if (button == 1) {
                currentIndex = (currentIndex - 1 + options.length) % options.length;
            }
            setValue(options[currentIndex]);
        }
    }
} 