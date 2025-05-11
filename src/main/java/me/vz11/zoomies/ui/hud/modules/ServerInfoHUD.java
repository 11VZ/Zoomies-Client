package me.vz11.zoomies.ui.hud.modules;

import me.vz11.zoomies.ui.hud.HUDModule;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;

public class ServerInfoHUD extends HUDModule {
    public ServerInfoHUD(int x, int y) {
        super("Server Info", x, y);
        this.width = 128;
        this.height = 32;
        this.enabled = true;
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, TextRenderer textRenderer, boolean editMode, boolean enabled) {
        super.render(drawContext, mouseX, mouseY, textRenderer, editMode, enabled);
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        ServerInfo serverData = MinecraftClient.getInstance().getCurrentServerEntry();
    
        String serverIP = serverData != null ? serverData.address : "Singleplayer";
        
        int yOffset = 0;
        int lineHeight = textRenderer.fontHeight + 1;
        
        drawContext.drawTextWithShadow(textRenderer, 
            ColorUtils.aqua + "Server: " + ColorUtils.white + serverIP, 
            x, y + yOffset, 0xFFFFFF
        );
        yOffset += lineHeight;

        if (serverData != null) {
            ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
            if (networkHandler != null) {
                int online = networkHandler.getPlayerList().size();
                int max = MinecraftClient.getInstance().getCurrentServerEntry().players.max();
                drawContext.drawTextWithShadow(textRenderer,
                    ColorUtils.aqua + "Players: " + ColorUtils.white + online + " / " + max,
                    x, y + yOffset, 0xFFFFFF);
                yOffset += lineHeight;
            }
        }        

        if (serverData!= null) {
            String versionString = serverData.version.toString();
            String cleaned = versionString != null ? versionString : "";
            int start = cleaned.indexOf('{');
            int end = cleaned.indexOf('}', start + 1);
            if (start != -1 && end != -1) {
                cleaned = cleaned.substring(start + 1, end);
            }
            String displayVersion = cleaned.split("\\s+\\(")[0];
            drawContext.drawTextWithShadow(textRenderer,
                ColorUtils.aqua + "Software: " + ColorUtils.white + displayVersion,
                x, y + yOffset, 0xFFFFFF);
            yOffset += lineHeight;
        }
    }
}