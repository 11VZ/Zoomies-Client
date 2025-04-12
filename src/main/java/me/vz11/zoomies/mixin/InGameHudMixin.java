package me.vz11.zoomies.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.vz11.zoomies.module.ModuleManager;
import me.vz11.zoomies.module.modules.HUDEnabler;
import me.vz11.zoomies.ui.LegacyHUD;
import me.vz11.zoomies.ui.ModulesListOverlay;
import me.vz11.zoomies.ui.hud.HUDRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    
	@Inject(at = @At("TAIL"), method = "render") 
	public void onRender (DrawContext drawContext, RenderTickCounter tickCounter, CallbackInfo info) 
    {
		HUDEnabler hudModule = (HUDEnabler)ModuleManager.INSTANCE.getModuleByName("HUD");
		if (hudModule.enabled) 
		{
			if (hudModule.mode.index == 1) LegacyHUD.INSTANCE.render(drawContext, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight());
			else HUDRenderer.INSTANCE.render(drawContext);
		}
		if (ModuleManager.INSTANCE.getModuleByName("ModulesList").enabled) ModulesListOverlay.INSTANCE.render(drawContext, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight());
	}

}
