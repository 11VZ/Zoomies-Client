package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.ChatUtils;
import me.vz11.zoomies.util.InteractionUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.EntityHitResult;

public class MaceBoost extends Module {
    private boolean shouldBoost = false;
    private int boostStage = 0;
    
    public MaceBoost() {
        super("MaceBoost", "Instantly kills people with a mace", Category.COMBAT, true);
    }

    @Override
    public void tick()
    {	
        if (mc.player == null || mc.world == null) return;
        
        if (mc.options.attackKey.isPressed() && mc.player.getAttackCooldownProgress(0.1f) <= 1 && InteractionUtil.holding("mace")) {
            if (mc.crosshairTarget instanceof EntityHitResult) {
                shouldBoost = true;
                boostStage = 0;
            }
        }

        if (shouldBoost) {
            double x = mc.player.getX();
            double y = mc.player.getY();
            double z = mc.player.getZ();

            switch (boostStage) {
                case 0 -> {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
                    boostStage++;
                }
                case 1 -> {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 20, z, false));
                    boostStage++;
                }
                case 2 -> {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
                    if (mc.crosshairTarget instanceof EntityHitResult entityHit) {
                        mc.interactionManager.attackEntity(mc.player, entityHit.getEntity());
                    }
                    shouldBoost = false;
                    boostStage = 0;
                }
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        shouldBoost = false;
        boostStage = 0;
    }
}