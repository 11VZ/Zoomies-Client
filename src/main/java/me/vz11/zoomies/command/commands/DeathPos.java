package me.vz11.zoomies.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.vz11.zoomies.command.Command;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.ColorUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.GlobalPos;

public class DeathPos extends Command
{
    public DeathPos() 
    {
		super("deathpos", "Shows your last death position.");
	}
    
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) 
    {
        builder.executes(context -> 
        {
            GlobalPos pos = null;
            try 
            {
                pos = mc.player.getLastDeathPos().get();
                if (pos == null) throw new Exception();
            } 
            catch (Exception e) 
            {
                ChatUtils.sendMsg(ColorUtils.reset + "You have not died in this world.");
                return SINGLE_SUCCESS;
            }
            
			ChatUtils.sendMsg(ColorUtils.reset + "You last died at: " + 
            ColorUtils.aqua + " X: " + ColorUtils.gray + pos.pos().getX() + 
            ColorUtils.aqua + " Y: " + ColorUtils.gray + pos.pos().getY() +
            ColorUtils.aqua + " Z: " + ColorUtils.gray + pos.pos().getZ()
            );
			return SINGLE_SUCCESS;
		}); 
    }
}
