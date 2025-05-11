package me.vz11.zoomies.mixin;

import me.vz11.zoomies.module.modules.Render;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleTypes;
import me.vz11.zoomies.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect effect, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Boolean> cir) {
        if (effect == null) return;
        
        Render render = (Render) ModuleManager.INSTANCE.getModuleByName("NoRender");
        if (render != null && render.enabled && render.explosions.value) {
            if (effect.getType() == ParticleTypes.EXPLOSION ||
                effect.getType() == ParticleTypes.EXPLOSION_EMITTER ||
                effect.getType() == ParticleTypes.LARGE_SMOKE) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
} 