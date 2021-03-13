package me.pljr.pljrapibungee.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public final class PlayerUtil {

    public static boolean isPlayer(String input) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(input);
        return player != null;
    }
}
