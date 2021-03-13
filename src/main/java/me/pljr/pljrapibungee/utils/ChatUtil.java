package me.pljr.pljrapibungee.utils;

import me.pljr.pljrapibungee.PLJRApiBungee;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

public final class ChatUtil {
    private static final BungeeAudiences BUNGEE_AUDIENCES = PLJRApiBungee.get().getBungeeAudiences();
    private static final ProxyServer PROXY_SERVER = ProxyServer.getInstance();

    /**
     * Sends a message with parsed {@link MiniMessage} tags to {@link ProxiedPlayer}.
     *
     * @param player {@link ProxiedPlayer} that will receive the message
     * @param message String that will be send to the player
     */
    public static void sendMessage(ProxiedPlayer player, String message){
        BUNGEE_AUDIENCES.player(player).sendMessage(MiniMessage.get().parse(message));
    }

    /**
     * Sends a message to {@link ProxiedPlayer}.
     *
     * @param player {@link ProxiedPlayer} that will receive the message
     * @param message String that will be send to the player
     */
    public static void sendMessageClean(ProxiedPlayer player, String message){
        player.sendMessage(new TextComponent(message));
    }

    /**
     * Sends a message with parsed {@link MiniMessage} tags to {@link CommandSender}.
     *
     * @param target {@link CommandSender} that will receive the message
     * @param message String that will be send to the target
     *
     * @see #sendMessage(ProxiedPlayer, String)
     */
    public static void sendMessage(CommandSender target, String message){
        if (target instanceof ProxiedPlayer){
            sendMessage((ProxiedPlayer) target, message);
            return;
        }
        BUNGEE_AUDIENCES.sender(target).sendMessage(MiniMessage.get().parse(message));
    }

    /**
     * Sends a message to {@link CommandSender}.
     *
     * @param target {@link CommandSender} that will receive the message
     * @param message String that will be send to the target
     *
     * @see #sendMessageClean(ProxiedPlayer, String)
     */
    public static void sendMessageClean(CommandSender target, String message){
        if (target instanceof ProxiedPlayer){
            sendMessageClean((ProxiedPlayer) target, message);
            return;
        }
        target.sendMessage(new TextComponent(message));
    }

    /**
     * Broadcasts a message with parsed {@link MiniMessage} tags.
     *
     * @param message String that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the message
     *
     * @see #sendMessage(ProxiedPlayer, String)
     * @see #sendMessage(CommandSender, String)
     */
    public static void broadcast(String message, String perm){
        PROXY_SERVER.getPlayers().forEach(player -> {
            if (player.hasPermission(perm) || perm.equals("")){
                sendMessage(player, message);
            }
        });
        sendMessage(PROXY_SERVER.getConsole(), message);
    }

    /**
     * Broadcasts a list of messages with parsed {@link MiniMessage} tags.
     *
     * @param messages ArrayList that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the messages
     *
     * @see #broadcast(String, String)
     */
    public static void broadcast(List<String> messages, String perm){
        messages.forEach(line -> broadcast(line, perm));
    }

    /**
     * Broadcasts a message.
     *
     * @param message String that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the message
     *
     * @see #sendMessage(ProxiedPlayer, String)
     * @see #sendMessageClean(CommandSender, String)
     */
    public static void broadcastClean(String message, String perm){
        PROXY_SERVER.getPlayers().forEach(player -> {
            if (player.hasPermission(perm) || perm.equals("")){
                sendMessageClean(player, message);
            }
        });
        sendMessageClean(PROXY_SERVER.getConsole(), message);
    }

    /**
     * Broadcasts a list of messages.
     *
     * @param messages ArrayList that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the messages
     *
     * @see #broadcastClean(List, String)
     */
    public static void broadcastClean(List<String> messages, String perm){
        messages.forEach(line -> broadcastClean(line, perm));
    }
}