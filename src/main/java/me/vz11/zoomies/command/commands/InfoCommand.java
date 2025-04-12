package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import me.vz11.zoomies.util.TickRateUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.command.CommandSource;
import net.minecraft.world.Difficulty;

public class InfoCommand extends Command {
    private static final int ENTRIES_PER_PAGE = 5;

    public InfoCommand() {
        super("info", "Displays server information");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(RequiredArgumentBuilder.<CommandSource, Integer>argument("page", IntegerArgumentType.integer(1))
            .executes(context -> {
                int page = IntegerArgumentType.getInteger(context, "page");
                showInfoPage(page);
                return SINGLE_SUCCESS;
            }))
        .executes(context -> {
            showInfoPage(1);
            return SINGLE_SUCCESS;
        });
    }

    private void showInfoPage(int page) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        ServerInfo serverData = MinecraftClient.getInstance().getCurrentServerEntry();
        int totalEntries = 7;
        int totalPages = (int) Math.ceil(totalEntries / (double) ENTRIES_PER_PAGE);
        
        page = Math.max(1, Math.min(page, totalPages));
        
        ChatUtils.clearChat();
        ChatUtils.sendMsg(ColorUtils.aqua + "---- Server Info [" + page + "/" + totalPages + "] ----");

        switch (page) {
            case 1:
                String serverIP = serverData != null ? serverData.address : "Singleplayer";
                ChatUtils.sendMsg(ColorUtils.aqua + "Server IP: " + ColorUtils.white + serverIP);
                // ChatUtils.sendMsg(ColorUtils.aqua + "Public IP: " + ColorUtils.white + NetworkUtil.resolveAddress(serverIP));
                ChatUtils.sendMsg(ColorUtils.aqua + "Version: " + ColorUtils.white + MinecraftClient.getInstance().getGameVersion());
                
                if (serverData != null) {
                    ChatUtils.sendMsg(ColorUtils.aqua + "Players: " + ColorUtils.white + 
                        serverData.players.online() + " / " + serverData.players.max());
                    ChatUtils.sendMsg(ColorUtils.aqua + "Ping: " + ColorUtils.white + serverData.ping + "ms");
                }
                break;
                
            case 2:
                if (player.clientWorld != null) {
                    Difficulty difficulty = player.clientWorld.getDifficulty();
                    ChatUtils.sendMsg(ColorUtils.aqua + "Difficulty: " + ColorUtils.white + difficulty.getName());
                }
                float tps = TickRateUtil.INSTANCE.getTickRate();
                ChatUtils.sendMsg(ColorUtils.aqua + "TPS: " + ColorUtils.white + String.format("%.1f", tps));
                break;
        }
        
        ChatUtils.sendMsg(ColorUtils.green + "Use .info <page> to navigate");
    }
}