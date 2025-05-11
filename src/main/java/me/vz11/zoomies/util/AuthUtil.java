package me.vz11.zoomies.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.Arrays;
import java.util.List;

public class AuthUtil {
    private static final List<String> AUTHORIZED_PLAYERS = Arrays.asList(
        "11VZ",
        "anchormacroer"
    );

    public static boolean isAuthorized() {
        if (MinecraftClient.getInstance().player == null) return false;
        String username = MinecraftClient.getInstance().player.getName().getString().toLowerCase();
        return AUTHORIZED_PLAYERS.stream().anyMatch(auth -> auth.toLowerCase().equals(username));
    }

    public static class UnauthorizedScreen extends Screen {
        public UnauthorizedScreen() {
            super(Text.literal("Unauthorized Access"));
        }

        @Override
        protected void init() {
            super.init();
            int centerX = this.width / 2;
            int centerY = this.height / 2;

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Unauthorized Access"), button -> {})
                .dimensions(centerX - 100, centerY - 60, 200, 20)
                .build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("You are not authorized to use Zoomies Client"), button -> {})
                .dimensions(centerX - 150, centerY - 20, 300, 20)
                .build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Contact 11VZ on Discord"), button -> {
                Util.getOperatingSystem().open("https://discord.com/users/1306686420190236753");
            }).dimensions(centerX - 100, centerY + 20, 200, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Exit"), button -> {
                MinecraftClient.getInstance().scheduleStop();
            }).dimensions(centerX - 100, centerY + 60, 200, 20).build());
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            this.renderBackground(context, mouseX, mouseY, delta);
            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public boolean shouldPause() {
            return true;
        }
    }
} 