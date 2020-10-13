package me.pljr.pljrapibungee.channels;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.pljr.pljrapibungee.PLJRApiBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.Map;

public class ChatChannel {

    public ChatChannel(){
        PLJRApiBungee.getInstance().getProxy().registerChannel("pljrapi:chat");
    }

    public void sendMessage(String player, String message){
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if (networkPlayers == null || networkPlayers.isEmpty()){
            return;
        }
        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(player);
        if (proxiedPlayer == null) return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("message");
        out.writeUTF(player);
        out.writeUTF(message);

        proxiedPlayer.getServer().getInfo().sendData("pljrapi:chat", out.toByteArray());
    }

    public void broadcastMessage(String perm, String message){
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if (networkPlayers == null || networkPlayers.isEmpty()){
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("broadcast");
        out.writeUTF(perm);
        out.writeUTF(message);

        for (Map.Entry<String, ServerInfo> entry : ProxyServer.getInstance().getServers().entrySet()){
            ProxyServer.getInstance().getServerInfo(entry.getKey()).sendData("pljrapi:chat", out.toByteArray());
        }
    }
}
