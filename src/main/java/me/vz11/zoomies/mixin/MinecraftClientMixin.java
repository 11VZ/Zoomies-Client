package me.vz11.zoomies.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import me.vz11.zoomies.Zoomies;
import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.modules.FPSSpoofer;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    private int fpsCounter;

    @ModifyVariable(
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;fpsCounter:I"),
        method = "render(Z)V",
        index = 1,
        argsOnly = true
    )
    private boolean modifyFpsCounter(boolean value) {
        me.vz11.zoomies.module.Module spoofer = ModuleManager.INSTANCE.getModuleByName("FPS Spoofer");
        if (spoofer == null || !spoofer.enabled) return value;
        
        fpsCounter = (int) ((FPSSpoofer) spoofer).fakeFps.value;
        if (((FPSSpoofer) spoofer).fluctuate.value) {
            fpsCounter += (int) (Math.random() * ((FPSSpoofer) spoofer).fluctuateAmount.value * 2) 
                - ((FPSSpoofer) spoofer).fluctuateAmount.value;
        }
        return true;
    }

    @Inject(at = @At("TAIL"), method = "scheduleStop")
    public void onShutdown(CallbackInfo ci) {
        Zoomies.INSTANCE.saveConfig();
    }
}
