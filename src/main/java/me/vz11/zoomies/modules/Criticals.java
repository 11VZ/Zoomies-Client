package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.hit.EntityHitResult;

public class Criticals extends Module
{
    private boolean shouldCrit = false;
    private int critStage = 0;
    
    public Criticals()
    {
        super("Criticals", "Critical smacks.", Category.COMBAT, true);
    }
    
    @Override
    public void onEnable()
    {
        super.onEnable();
        shouldCrit = false;
        critStage = 0;
    }

    @Override
    public void tick()
    {	
        if (mc.player == null || mc.world == null) return;
        
        if (mc.options.attackKey.isPressed() && mc.player.getAttackCooldownProgress(0.1f) <= 1) {
            if (mc.crosshairTarget instanceof EntityHitResult) {
                shouldCrit = true;
                critStage = 0;
            }
        }

        if (shouldCrit) {
            double x = mc.player.getX();
            double y = mc.player.getY();
            double z = mc.player.getZ();

            switch (critStage) {
                case 0 -> {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
                    critStage++;
                }
                case 1 -> {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0625, z, false));
                    critStage++;
                }
                case 2 -> {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
                    if (mc.crosshairTarget instanceof EntityHitResult entityHit) {
                        mc.interactionManager.attackEntity(mc.player, entityHit.getEntity());
                    }
                    shouldCrit = false;
                    critStage = 0;
                }
            }
        }
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        shouldCrit = false;
        critStage = 0;
    }
}
