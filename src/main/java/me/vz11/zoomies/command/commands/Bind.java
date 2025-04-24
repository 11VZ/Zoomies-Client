package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.ui.clickgui.ClickGUIScreen;
import net.minecraft.command.CommandSource;

public class Bind extends Command {
    public Bind() {
        super("bind", "Binds clickgui");
    }
    
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("key", StringArgumentType.string())
            .executes(context -> {
                String key = StringArgumentType.getString(context, "key");
                key = key.replace("_", " ");
                ClickGUIScreen.keybind = key;
                ChatUtils.sendMsg("ClickGUI keybind set to §d" + key);
                return SINGLE_SUCCESS;
            }));
    }
}
