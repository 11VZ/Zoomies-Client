package me.vz11.zoomies.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.math.Box;
import net.minecraft.util.hit.EntityHitResult;

public class RayTraceUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Entity getLookedAtEntity(double distance) {
        if (mc.player == null || mc.world == null) {
            return null;
        }

        Vec3d start = mc.player.getEyePos();
        Vec3d direction = mc.player.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(distance));
        Box searchBox = new Box(start, end).expand(1.0);

        EntityHitResult result = ProjectileUtil.raycast(
                mc.player,
                start,
                end,
                searchBox,
                entity -> !entity.isSpectator() && entity.isAlive() && entity != mc.player,
                distance
        );

        return result != null ? result.getEntity() : null;
    }

    public static BlockState getLookedAtBlock(double distance) {
        if (mc.player == null || mc.world == null) {
            return null;
        }

        Vec3d start = mc.player.getCameraPosVec(1.0F);
        Vec3d end = start.add(mc.player.getRotationVec(1.0F).multiply(distance));

        HitResult hitResult = mc.world.raycast(new net.minecraft.world.RaycastContext(
                start,
                end,
                net.minecraft.world.RaycastContext.ShapeType.OUTLINE,
                net.minecraft.world.RaycastContext.FluidHandling.NONE,
                mc.player
        ));

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            return mc.world.getBlockState(blockHit.getBlockPos());
        }

        return null;
    }



    public static BlockHitResult getLookedAtBlockState(double distance) {
        if (mc.player == null || mc.world == null) {
            return null;
        }

        Vec3d start = mc.player.getCameraPosVec(1.0F);
        Vec3d end = start.add(mc.player.getRotationVec(1.0F).multiply(distance));

        return mc.world.raycast(new net.minecraft.world.RaycastContext(
                start,
                end,
                net.minecraft.world.RaycastContext.ShapeType.OUTLINE,
                net.minecraft.world.RaycastContext.FluidHandling.NONE,
                mc.player
        ));
    }

    public static BlockHitResult getLookedAtBlockHitResult(double distance) {
        if (mc.player == null || mc.world == null) {
            return null;
        }

        Vec3d start = mc.player.getCameraPosVec(1.0F);
        Vec3d end = start.add(mc.player.getRotationVec(1.0F).multiply(distance));

        HitResult hitResult = mc.world.raycast(new net.minecraft.world.RaycastContext(
                start,
                end,
                net.minecraft.world.RaycastContext.ShapeType.OUTLINE,
                net.minecraft.world.RaycastContext.FluidHandling.NONE,
                mc.player
        ));

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return (BlockHitResult) hitResult;
        }

        return null;
    }

    public static String parseBlockName(String blockStateString) {
        if (blockStateString == null || !blockStateString.contains("{") || !blockStateString.contains("minecraft:")) {
            return "Unknown Block";
        }

        try {
            int startIndex = blockStateString.indexOf("minecraft:") + "minecraft:".length();
            int endIndex = blockStateString.indexOf("}", startIndex);
            if (startIndex > 0 && endIndex > startIndex) {
                return blockStateString.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Unknown Block";
    }
    public static String getLookedAtBlockName(double distance) {
        BlockState block = getLookedAtBlock(distance);
        if (block != null) {
            String blockStateString = block.toString();
            return parseBlockName(blockStateString);
        }
        return "No Block";
    }
}
