package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import me.vz11.zoomies.util.TickRateUtil;
import net.minecraft.command.CommandSource;

public class TPS extends Command
{
    public TPS() 
    {
        super("tps", "Gets the TPS of the server.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.executes(context -> 
        {
            float tps = TickRateUtil.INSTANCE.getTickRate();
			ChatUtils.sendMsg(ColorUtils.aqua + "TPS: " + ColorUtils.white + String.format("%.1f", tps));
			return SINGLE_SUCCESS;
		});
    }    
}
