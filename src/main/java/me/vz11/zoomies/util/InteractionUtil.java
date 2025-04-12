package me.vz11.zoomies.util;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class InteractionUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean cancelRightClickFlag = false;
    private static int lastSlot = -1;

    public static void centerPlayer() {
        double x = MathHelper.floor(mc.player.getX()) + 0.5;
        double z = MathHelper.floor(mc.player.getZ()) + 0.5;
        mc.player.setPosition(x, mc.player.getY(), z);
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.isOnGround()));
    }

    public static float getPlayerHealth() {
        ClientPlayerEntity player = mc.player;
        return player != null ? player.getHealth() : 0;
    }

    public static boolean isPlayerMoving() {
        ClientPlayerEntity player = mc.player;
        return player != null && (player.sidewaysSpeed != 0 || player.forwardSpeed != 0);
    }

    public static void registerRightClickCancel() {
        UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
            if (cancelRightClickFlag) {
                cancelRightClickFlag = false;
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }

    public static void cancelCurrentRightClick() {
        cancelRightClickFlag = true;
    }

    public static void goToSlot(int slot) {
        ClientPlayerEntity player = mc.player;
        if (player != null && slot >= 0 && slot < 9) {
            player.getInventory().selectedSlot = slot;
        }
    }

    public static void leftClick() {
        ClientPlayerEntity player = mc.player;

        if (player != null && mc.interactionManager != null) {
            HitResult hitResult = mc.crosshairTarget;

            if (hitResult != null) {
                switch (hitResult.getType()) {
                    case ENTITY -> {
                        EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                        Entity targetEntity = entityHitResult.getEntity();
                        mc.interactionManager.attackEntity(player, targetEntity);
                        player.swingHand(Hand.MAIN_HAND);
                    }
                    case BLOCK -> {
                        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                        mc.interactionManager.attackBlock(blockHitResult.getBlockPos(), player.getHorizontalFacing());
                        player.swingHand(Hand.MAIN_HAND);
                    }
                    default -> {
                        player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        }
    }

    public static void rightClick() {
        if (mc.player == null || mc.world == null) {
            return;
        }
        ClientPlayerEntity player = mc.player;

        BlockHitResult hitResult = RayTraceUtil.getLookedAtBlockState(5.0);

        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            player.swingHand(Hand.MAIN_HAND);

            mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(
                    Hand.MAIN_HAND,
                    hitResult,
                    0
            ));
        }
    }

    public static void rightClickItem() {
        if (mc.player == null || mc.world == null) {
            return;
        }

        float yaw = mc.player.getYaw(1.0F);
        float pitch = mc.player.getPitch(1.0F);


        mc.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(
                Hand.MAIN_HAND,
                0,
                yaw,
                pitch
        ));
    }

    public static boolean holding(String itemName) {
        if (mc.player == null) {
            return false;
        }

        ItemStack mainHandItem = mc.player.getMainHandStack();
        return mainHandItem.getItem().toString().contains(itemName);
    }

    public static void switchToBlock(String blockId) {
        if (mc.player == null || mc.world == null) {
            return;
        }

        lastSlot = mc.player.getInventory().selectedSlot;

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            if (mc.player.getInventory().getStack(i).getItem().toString().contains(blockId)) {
                mc.player.getInventory().selectedSlot = i;
                return;
            }
        }

        System.out.println("Block not found in inventory: " + blockId);
    }

    public static void switchBack() {
        if (mc.player == null || mc.world == null) {
            return;
        }

        if (lastSlot != -1) {
            mc.player.getInventory().selectedSlot = lastSlot;
            lastSlot = -1;
        } else {
            System.out.println("No previous slot to switch back to.");
        }
    }

    public static boolean inputActive(String key) {
        KeyBinding keyBinding = getKeyBindingForKey(key);
        return keyBinding != null && keyBinding.isPressed();
    }

    private static KeyBinding getKeyBindingForKey(String key) {
        if (mc.options == null) return null;

        switch (key.toLowerCase()) {
            case "left":
                return mc.options.attackKey;
            case "right":
                return mc.options.useKey;
            case "jump":
                return mc.options.jumpKey;
            case "sneak":
                return mc.options.sneakKey;
            case "sprint":
                return mc.options.sprintKey;
            default:
                return getCustomKeyBinding(key);
        }
    }

    private static KeyBinding getCustomKeyBinding(String key) {
        InputUtil.Key keyCode = InputUtil.fromTranslationKey("key.keyboard." + key.toLowerCase());
        for (KeyBinding binding : mc.options.allKeys) {
            if (binding.getDefaultKey().equals(keyCode)) {
                return binding;
            }
        }
        return null;
    }

    public static boolean isLookingAtPlayer(double distance) {
        if (mc.player == null || mc.world == null) {
            return false;
        }

        Entity target = RayTraceUtil.getLookedAtEntity(distance);
        return target instanceof PlayerEntity && target != mc.player;
    }

    // TODO: Add detection for all GUIs, preferably in a single functions with a string parameter.

    public static boolean isChatOpen() {
        return mc.currentScreen instanceof ChatScreen;
    }
}
