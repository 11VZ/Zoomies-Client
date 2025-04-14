package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.util.ChatUtils;
import net.minecraft.command.CommandSource;

public class Mode extends Command {
    public Mode() {
        super("mode", "Toggle legit mode or blatant mode.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("legit").executes(context -> {
            ModuleManager.blatantMode = false;
            ChatUtils.sendMsg("Switched to §9Legit§f mode");
            return 1;
        }))
        .then(literal("blatant").executes(context -> {
            ModuleManager.blatantMode = true;
            ChatUtils.sendMsg("Switched to §cBlatant§f mode");
            return 1;
        }));
    }
}
