package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

public class Toggle extends Command
{

    public Toggle() 
    {
        super("toggle", "Toggle a module on or off.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.then(argument("module", StringArgumentType.string())
        .executes(context -> 
        {
            String m = context.getArgument("module", String.class);
            Module module = ModuleManager.INSTANCE.getModuleByName(m);
            MinecraftClient.getInstance().send(() -> module.toggle());

            return SINGLE_SUCCESS;
        }));
    }
    
}
