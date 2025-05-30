package me.vz11.zoomies.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import me.vz11.zoomies.Zoomies;
import me.vz11.zoomies.command.commands.*;

public class CommandManager 
{
    private static MinecraftClient mc = MinecraftClient.getInstance();
	private final CommandDispatcher<CommandSource> DISPATCHER = new CommandDispatcher<>();
    private final CommandSource COMMAND_SOURCE = new ChatCommandSource(mc);
    private final List<Command> commands = new ArrayList<>();
    private final Map<Class<? extends Command>, Command> commandInstances = new HashMap<>();
    public static String prefix = ".";

    public CommandManager() {
    	add(new VClip());
        add(new Help());
        add(new Toggle());
        add(new Teleport());
        add(new SettingCommand());
        add(new Reset());
        add(new DeathPos());
        add(new InfoCommand());
        add(new CenterPos());
        add(new Ping());
        add(new TPS());
        add(new CPS());
        add(new Prefix());
        add(new ClearChat());
        add(new Mode());
        add(new Bind());
        commands.sort(Comparator.comparing(Command::getName));
    }

    public static CommandManager get() {
        return new CommandManager();
    }

    public void dispatch(String message) throws CommandSyntaxException {
        dispatch(message, new ChatCommandSource(mc));
    }

    public void dispatch(String message, CommandSource source) throws CommandSyntaxException {
        ParseResults<CommandSource> results = DISPATCHER.parse(message, source);
        DISPATCHER.execute(results);
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return DISPATCHER;
    }

    public CommandSource getCommandSource() {
        return COMMAND_SOURCE;
    }

    private final static class ChatCommandSource extends ClientCommandSource {
        public ChatCommandSource(MinecraftClient client) {
            super(null, client);
        }
    }

    public void add(Command command) {
        commands.removeIf(command1 -> command1.getName().equals(command.getName()));
        commandInstances.values().removeIf(command1 -> command1.getName().equals(command.getName()));

        command.registerTo(DISPATCHER);
        commands.add(command);
        commandInstances.put(command.getClass(), command);
    }

    public int getCount() {
        return commands.size();
    }

    public List<Command> getAll() {
        return commands;
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> T get(Class<T> klass) {
        return (T) commandInstances.get(klass);
    }

    public void setPrefix(String selectedPrefix){
        CommandManager.prefix = selectedPrefix;
        Zoomies.INSTANCE.saveConfig();
    }

    public String getPrefix() {
        return prefix;
    }
}
