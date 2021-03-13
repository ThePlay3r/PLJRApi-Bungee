package me.pljr.pljrapibungee.channels;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.pljr.pljrapibungee.utils.ChatUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatChannel implements Listener {
    private final ProxyServer proxy;

    public ChatChannel(ProxyServer proxy){
        this.proxy = proxy;
        proxy.registerChannel("plrjapi:chat");
    }

    @EventHandler
    public void onReceive(PluginMessageEvent event) {
        if (event.getTag().equals("pljrapi:chat")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
            String subChannel = in.readUTF();
            switch (subChannel.toUpperCase()){
                case "MESSAGE": {
                    ProxiedPlayer player = proxy.getPlayer(in.readUTF());
                    if (player != null) {
                        ChatUtil.sendMessage(player, in.readUTF());
                    }
                    break;
                }
                case "BROADCAST": {
                    ChatUtil.broadcast(in.readUTF(), in.readUTF());
                    break;
                }
            }
        }
    }
}
