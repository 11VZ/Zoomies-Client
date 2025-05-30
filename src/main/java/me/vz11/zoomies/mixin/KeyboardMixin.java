package me.vz11.zoomies.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.ui.clickgui.ClickGUIScreen;
import me.vz11.zoomies.util.InteractionUtil;
import me.vz11.zoomies.util.KeycodeUtils;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Shadow @Final private MinecraftClient client;

	@Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
        if (KeycodeUtils.isKeyPressed(ClickGUIScreen.keybind) && !InteractionUtil.isChatOpen()) {
            MinecraftClient.getInstance().setScreen(ClickGUIScreen.INSTANCE);
        }
        for (Module m : ModuleManager.INSTANCE.modules)
        {
            if (key == m.keybind.value && action == GLFW.GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null)
            {
                m.toggle();
            }
        }
    }
}
