package me.vz11.zoomies.module.modules;

import me.vz11.zoomies.module.Category;
import me.vz11.zoomies.module.Module;
import me.vz11.zoomies.module.settings.DoubleSetting;
import me.vz11.zoomies.module.settings.StringSetting;
import me.vz11.zoomies.util.Timer;

public class ChatSpammer extends Module 
{
    public StringSetting message = new StringSetting("Message", "MARLOW LOST TO 11VZ LOLOLOL");
    public DoubleSetting delay = new DoubleSetting("Delay (Seconds)", 1, 0.1, 10, 1);
    private Timer timer = new Timer();

    public ChatSpammer()
    {
        super("Chat Spammer", "Spams a selected message in chat.", Category.CHAT, false);
        settings.add(message);
        settings.add(delay);
    }

    @Override
    public void tick()
    {
        if (timer.hasTimeElapsed((long)delay.value * 1000, true)) 
        {
        	mc.player.networkHandler.sendChatMessage(message.value);
        }
    }
}
