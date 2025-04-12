package me.vz11.zoomies.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtils 
{
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void sendMsg(String message) 
    {
        if (mc.world == null) return;
        mc.inGameHud.getChatHud().addMessage(Text.literal(message));
    }

    public static void clearChat() {
        for(int i = 0; i < 20; i++) {
            sendMsg("");
        }
    }
}
