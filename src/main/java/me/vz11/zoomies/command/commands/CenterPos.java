package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import me.vz11.zoomies.util.InteractionUtil;
import net.minecraft.command.CommandSource;

public class CenterPos extends Command
{
    public CenterPos() 
    {
		super("center", "Centeres your position on the block.");
	}
    
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.executes(context -> 
        {
            ChatUtils.sendMsg(ColorUtils.aqua + "Centered your position.");
            InteractionUtil.centerPlayer();
            return SINGLE_SUCCESS;
		}); 
    }
}
