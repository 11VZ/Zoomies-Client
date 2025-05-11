package me.vz11.zoomies.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import java.util.Optional;
import java.util.UUID;

public class SessionUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void setOfflineUsername(String username) {
        if (username == null || username.isEmpty()) return;
        try {
            UUID uuid = UUID.randomUUID();
            Session newSession = new Session(
                username,
                uuid,
                "",
                Optional.empty(),
                Optional.empty(),
                Session.AccountType.MOJANG
            );
            ReflectionUtils.setPrivateField(MinecraftClient.class, mc, "session", newSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentUsername() {
        try {
            Session session = (Session) ReflectionUtils.getPrivateField(MinecraftClient.class, mc, "session");
            return session.getUsername();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isOffline() {
        try {
            Session session = (Session) ReflectionUtils.getPrivateField(MinecraftClient.class, mc, "session");
            return session.getUuidOrNull() == null;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
} 