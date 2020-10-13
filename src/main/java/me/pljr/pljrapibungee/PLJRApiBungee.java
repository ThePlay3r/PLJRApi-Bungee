package me.pljr.pljrapibungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.pljr.pljrapibungee.channels.ChatChannel;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public final class PLJRApiBungee extends Plugin implements Listener {
    private static PLJRApiBungee instance;
    private static ChatChannel chatChannel;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        setupChannels();
    }

    private void setupChannels(){
        chatChannel = new ChatChannel();
    }

    @EventHandler
    public void onReceive(PluginMessageEvent event){
        if (event.getTag().equals("pljrapi:chat")){
            ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
            String subChannel = in.readUTF();
            if (subChannel.equals("message")){
                chatChannel.sendMessage(in.readUTF(), in.readUTF());
                return;
            }
            if (subChannel.equals("broadcast")){
                chatChannel.broadcastMessage(in.readUTF(), in.readUTF());
            }
        }
    }

    public static PLJRApiBungee getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
