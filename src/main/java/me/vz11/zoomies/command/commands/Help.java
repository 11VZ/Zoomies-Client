package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.command.CommandManager;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.command.CommandSource;

public class Help extends Command {
    private static final int COMMANDS_PER_PAGE = 5;

    public Help() {
        super("help", "Lists all commands with pagination");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(RequiredArgumentBuilder.<CommandSource, Integer>argument("page", IntegerArgumentType.integer(1))
            .executes(context -> {
                int page = IntegerArgumentType.getInteger(context, "page");
                showHelpPage(page);
                return SINGLE_SUCCESS;
            }))
        .executes(context -> {
            showHelpPage(1);
            return SINGLE_SUCCESS;
        });
    }

    private void showHelpPage(int page) {
        var commands = CommandManager.get().getAll();
        int totalPages = (int) Math.ceil(commands.size() / (double) COMMANDS_PER_PAGE);
        
        page = Math.max(1, Math.min(page, totalPages));
        
        ChatUtils.clearChat();
        
        int start = (page - 1) * COMMANDS_PER_PAGE;
        int end = Math.min(start + COMMANDS_PER_PAGE, commands.size());

        ChatUtils.sendMsg(ColorUtils.aqua + "---- Zoomies Help [" + page + "/" + totalPages + "] ----");
        for (Command cmd : commands.subList(start, end)) {
            ChatUtils.sendMsg(ColorUtils.aqua + cmd.getName() + ColorUtils.white + " - " + cmd.getDescription());
        }
        ChatUtils.sendMsg(ColorUtils.green + "Use .help <page> to navigate");
    }
}
