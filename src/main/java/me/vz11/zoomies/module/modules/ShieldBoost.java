package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class ShieldBoost extends Module {

    DoubleSetting multiply = new DoubleSetting("Multiply by", 2, 1, 10, 0);

    public ShieldBoost() {
        super("Shield Boost", "Makes it easy to escape holes", Category.COMBAT, true);
        settings.add(multiply);
    }

    @Override
    public void tick() {
        if (mc.player.isBlocking()) {
            Entity targetEntity = mc.player.getAttacker();
            boolean isCrystal = targetEntity instanceof EndCrystalEntity;
            
            boolean isAnchor = false;
            if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
                BlockState state = mc.world.getBlockState(pos);
                isAnchor = state.getBlock() == Blocks.RESPAWN_ANCHOR;
            }

            if (isCrystal || isAnchor) {
                mc.player.setVelocity(mc.player.getVelocity().x * multiply.value, mc.player.getVelocity().y * multiply.value, mc.player.getVelocity().z * multiply.value);
            }
        }
    }
}
