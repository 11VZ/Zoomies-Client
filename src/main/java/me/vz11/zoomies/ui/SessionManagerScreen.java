package me.vz11.zoomies.ui;

import me.vz11.zoomies.util.SessionUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;

public class SessionManagerScreen extends Screen {
    private TextFieldWidget usernameField;
    private String status = "";

    public SessionManagerScreen() {
        super(Text.literal("Session Manager"));
    }

    @Override
    protected void init() {
        super.init();
        int centerX = width / 2;
        int centerY = height / 2;

        // Username field
        usernameField = new TextFieldWidget(textRenderer, centerX - 100, centerY - 20, 200, 20, Text.literal("Username"));
        usernameField.setMaxLength(16);
        usernameField.setText(SessionUtil.getCurrentUsername());
        addDrawableChild(usernameField);

        // Set username button
        ButtonWidget setButton = ButtonWidget.builder(Text.literal("Set Username"), button -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                SessionUtil.setOfflineUsername(username);
                status = "Username set to: " + username;
                close();
            } else {
                status = "Please enter a username!";
            }
        }).dimensions(centerX - 50, centerY + 20, 100, 20).build();
        addDrawableChild(setButton);

        // Back button
        ButtonWidget backButton = ButtonWidget.builder(Text.literal("Back"), button -> close())
                .dimensions(centerX - 50, centerY + 50, 100, 20).build();
        addDrawableChild(backButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int centerX = width / 2;
        int centerY = height / 2;

        context.drawCenteredTextWithShadow(textRenderer, title, centerX, centerY - 60, 0xFFFFFF);

        context.drawTextWithShadow(textRenderer, "Username:", centerX - 100, centerY - 35, 0xFFFFFF);

        if (!status.isEmpty()) {
            context.drawCenteredTextWithShadow(textRenderer, status, centerX, centerY + 80, 0xFFFFFF);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}