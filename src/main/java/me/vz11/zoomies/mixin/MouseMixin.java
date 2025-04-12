package me.vz11.zoomies.mixin;

import me.vz11.zoomies.util.ClickCounter;
import me.vz11.zoomies.command.commands.CPS;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (action == 1) {
            if (button == 0 && !CPS.disableLeft) {
                ClickCounter.INSTANCE.registerLeftClick();
            } else if (button == 1 && !CPS.disableRight) {
                ClickCounter.INSTANCE.registerRightClick();
            }
        }
    }
}