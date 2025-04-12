package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import net.minecraft.command.CommandSource;

public class ClearChat extends Command
{
    public ClearChat() 
    {
        super("clearchat", "Clears the chat.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.executes(context -> 
        {
            ChatUtils.clearChat();
            return SINGLE_SUCCESS;
		});
    }    
}
