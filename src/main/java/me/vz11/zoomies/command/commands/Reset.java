package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.Zoomies;
import me.vz11.zoomies.command.Command;
import net.minecraft.command.CommandSource;

public class Reset extends Command
{
    public Reset() 
    {
        super("reset", "Resets all config options.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.executes(context -> 
        {
			Zoomies.CONFIG.loadDefaultConfig();
			return SINGLE_SUCCESS;
		});
    }    
}
