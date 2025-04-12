package me.vz11.zoomies.mixin;

import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.vz11.zoomies.ui.SessionManagerScreen;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin extends Screen {
    protected MultiplayerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void onInit(CallbackInfo ci) {
        this.addDrawableChild(
            this.addDrawableChild(ButtonWidget.builder(Text.of("Session Manager"), (button) -> {
                this.client.setScreen(new SessionManagerScreen());
            }).dimensions(10, 10, 120, 20).build())
        );
    }
}
