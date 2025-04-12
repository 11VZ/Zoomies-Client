package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

public class Ping extends Command
{
    public Ping() 
    {
        super("ping", "Gets your ping.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.executes(context -> 
        {
            if (MinecraftClient.getInstance().getCurrentServerEntry() == null){ChatUtils.sendMsg(ColorUtils.red + "Not connected to a server.");return SINGLE_SUCCESS;}
			ChatUtils.sendMsg(ColorUtils.aqua + "Ping: " + ColorUtils.white + (mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()) == null ? 0 : mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()).getLatency()));
			return SINGLE_SUCCESS;
		});
    }    
}
