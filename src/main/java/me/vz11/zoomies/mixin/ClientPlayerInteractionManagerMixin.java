package me.vz11.zoomies.mixin;

import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.modules.FastMine;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    
    @ModifyConstant(method = "updateBlockBreakingProgress", constant = @Constant(intValue = 5))
    private int modifyBlockBreakingProgress(int original) {
        FastMine fastMine = (FastMine) ModuleManager.INSTANCE.getModuleByName("Fast Mine");
        if (fastMine != null && fastMine.enabled && fastMine.shouldFastMine()) {
            switch (fastMine.mode.value) {
                case "Instant":
                    return 0;
                case "Fast":
                    return 1;
                case "Custom":
                    return (int) (5 / fastMine.speed.value);
                default:
                    return original;
            }
        }
        return original;
    }
}