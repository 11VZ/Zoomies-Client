package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.command.CommandManager;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.command.CommandSource;

public class Prefix extends Command
{
    public Prefix() 
    {
        super("prefix", "Sets the prefix of the commands.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.then(argument("key", StringArgumentType.greedyString())
            .executes(context -> 
            {
                String key = StringArgumentType.getString(context, "key");
                CommandManager.get().setPrefix(key);
                ChatUtils.sendMsg(ColorUtils.green + "Prefix set to " + key);
                return SINGLE_SUCCESS;
            }));
    }    
}
