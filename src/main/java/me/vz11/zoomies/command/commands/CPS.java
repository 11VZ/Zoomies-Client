package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.command.CommandSource;

public class CPS extends Command {
    public static boolean disableLeft = false;
    public static boolean disableRight = false;

    public CPS() {
        super("cps", "Disable left or right click on CPS display.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("left").executes(context -> {
            disableLeft = !disableLeft;
            ChatUtils.sendMsg(ColorUtils.aqua + "Left click " + 
                (disableLeft ? ColorUtils.red + "disabled" : ColorUtils.green + "enabled"));
            return 1;
        }))
        .then(literal("right").executes(context -> {
            disableRight = !disableRight;
            ChatUtils.sendMsg(ColorUtils.aqua + "Right click " + 
                (disableRight ? ColorUtils.red + "disabled" : ColorUtils.green + "enabled"));
            return 1;
        }))
        .then(literal("both").executes(context -> {
            disableLeft = !disableLeft;
            disableRight = !disableRight;
            String status = (disableLeft ? ColorUtils.red + "disabled" : ColorUtils.green + "enabled");
            ChatUtils.sendMsg(ColorUtils.aqua + "Both clicks " + status);
            return 1;
        }));
    }
}
