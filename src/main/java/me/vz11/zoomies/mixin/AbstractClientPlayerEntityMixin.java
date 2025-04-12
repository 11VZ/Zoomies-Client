package me.vz11.zoomies.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.vz11.zoomies.module.ModuleManager;

@Mixin(AbstractClientPlayerEntity.class)
@Environment(EnvType.CLIENT)
public class AbstractClientPlayerEntityMixin {
    private static final Identifier CAPE_ID = Identifier.of("zoomies", "textures/cape.png");
    @Inject(at = @At("RETURN"), method = "getSkinTextures", cancellable = true)
    private void getSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        if (ModuleManager.INSTANCE.getModuleByName("Cape").enabled) {
            SkinTextures textures = cir.getReturnValue();
            cir.setReturnValue(new SkinTextures(
                textures.texture(),
                textures.textureUrl(),
                CAPE_ID,
                textures.elytraTexture(),
                textures.model(),
                textures.secure()
            ));
        } else {
            SkinTextures textures = cir.getReturnValue();
            cir.setReturnValue(new SkinTextures(
                textures.texture(),
                textures.textureUrl(),
                textures.capeTexture(),
                textures.elytraTexture(),
                textures.model(),
                textures.secure()
            ));
        }
    }
}
