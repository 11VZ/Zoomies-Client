package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.util.InteractionUtil;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class NoJumpDelay extends Module
{
    private final MinecraftClient mc;

    public NoJumpDelay()
    {
        super("NoJumpDelay", "Eliminates jump delay.", Category.MOVEMENT, true);
        mc = MinecraftClient.getInstance();
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
    }

    @Override
    public void tick()
    {
        if (mc.player != null && mc.player.isOnGround() && GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS && !InteractionUtil.isChatOpen()) {
            mc.player.jump();
        }
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }
}
