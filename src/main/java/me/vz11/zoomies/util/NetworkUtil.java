package me.vz11.zoomies.util;

import java.net.InetSocketAddress;

public class NetworkUtil {
    public static String parseAddress(String full) {
        if (full == null || full.isBlank() || !full.contains(":")) return full;
        return full.substring(0, full.lastIndexOf(':'));
    }

    public static boolean resolveAddress(String address) {
        if (address == null || address.isBlank()) return false;

        int port = parsePort(address);
        if (port == -1) port = 25565;
        else address = parseAddress(address);

        return resolveAddress(address, port);
    }

    public static boolean resolveAddress(String address, int port) {
        if (port <= 0 || port > 65535 || address == null || address.isBlank()) return false;
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        return !socketAddress.isUnresolved();
    }
    
    public static int parsePort(String full) {
        if (full == null || full.isBlank() || !full.contains(":")) return -1;

        int port;

        try {
            port = Integer.parseInt(full.substring(full.lastIndexOf(':') + 1, full.length() - 1));
        } catch (NumberFormatException ignored) {
            port = -1;
        }

        return port;
    }
    
    public static boolean ipFilter(String text, char character) {
        if (text.contains(":") && character == ':') return false;
        return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || (character >= '0' && character <= '9') || character == '.' || character == '-';
    }
}
