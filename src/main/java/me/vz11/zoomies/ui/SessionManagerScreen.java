package me.vz11.zoomies.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class SessionManagerScreen extends Screen {
    private ButtonWidget loginbutton;
    private TextFieldWidget usernameField;
    private String username = MinecraftClient.getInstance().getSession().getUsername();

    public SessionManagerScreen() {
        super(Text.of("Session Input"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;

        usernameField = new TextFieldWidget(this.textRenderer, centerX - 100, 40, 200, 20, Text.of("Username"));
        usernameField.setText(username);
        this.addSelectableChild(usernameField);


        ButtonWidget.Builder login = ButtonWidget.builder(Text.literal("Login"), button -> {
            username = usernameField.getText();

            MinecraftClient.getInstance().setScreen(null);
        });
        login.dimensions(centerX - 100, 160, 200, 20);
        login.tooltip(Tooltip.of(Text.literal("ez!")));
        loginbutton = login
                .build();
        this.addDrawableChild(loginbutton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        usernameField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.usernameField.keyPressed(keyCode, scanCode, modifiers) || this.usernameField.isActive()) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}