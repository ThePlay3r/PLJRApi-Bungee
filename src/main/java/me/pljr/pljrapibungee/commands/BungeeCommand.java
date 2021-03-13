package me.pljr.pljrapibungee.commands;

import me.pljr.pljrapibungee.config.Lang;
import me.pljr.pljrapibungee.utils.ChatUtil;
import me.pljr.pljrapibungee.utils.NumberUtil;
import me.pljr.pljrapibungee.utils.PlayerUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.List;

public abstract class BungeeCommand extends Command {
    private final String command;
    private final String permission;

    public BungeeCommand(String command){
        super(command);
        this.command = command;
        this.permission = "";
    }

    public BungeeCommand(String command, String permission){
        super(command, permission);
        this.command = command;
        this.permission = permission;
    }

    public void registerCommand(Plugin plugin){
        PluginManager pluginManager = plugin.getProxy().getPluginManager();
        pluginManager.registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (!permission.equals("")){
                if (!checkPerm(player, permission)) return;
            }
            onPlayerCommand(player, args);
        }else{
            onConsoleCommand(sender, args);
        }
    }

    public void onPlayerCommand(ProxiedPlayer player, String[] args){
        sendMessageClean(player, Lang.COMMAND_RESPONSE_PLAYER.get());
    }

    public void onConsoleCommand(CommandSender sender, String[] args){
        sendMessageClean(sender, Lang.COMMAND_RESPONSE_CONSOLE.get());
    }

    public boolean checkPerm(CommandSender sender, String perm){
        if (!(sender instanceof ProxiedPlayer)) return true;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.hasPermission(perm)) return true;
        sendMessage(player, Lang.NO_PERM.get());
        return false;
    }

    public boolean checkPlayer(CommandSender sender, String requestName){
        if (!PlayerUtil.isPlayer(requestName)){
            sendMessage(sender, Lang.OFFLINE.get().replace("{name}", requestName));
            return false;
        }
        return true;
    }

    public boolean checkInt(CommandSender sender, String target){
        if (NumberUtil.isInt(target)){
            return true;
        }
        sendMessage(sender, Lang.NO_NUMBER.get().replace("{arg}", target));
        return false;
    }

    public void sendMessage(CommandSender sender, List<String> messages){
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            for (String line : messages){
                sendMessage(player, line);
            }
            return;
        }
        for (String line : messages){
            sendMessage(sender, line);
        }
    }

    public void sendMessageClean(CommandSender sender, List<String> messages){
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            for (String line : messages){
                sendMessageClean(player, line);
            }
            return;
        }
        for (String line : messages){
            sendMessageClean(sender, line);
        }
    }

    public void sendMessage(ProxiedPlayer player, String message){
        ChatUtil.sendMessage(player, message);
    }

    public void sendMessageClean(ProxiedPlayer player, String message){
        ChatUtil.sendMessageClean(player, message);
    }

    public void sendMessage(CommandSender sender, String message){
        ChatUtil.sendMessage(sender, message);
    }

    public void sendMessageClean(CommandSender sender, String message){
        ChatUtil.sendMessageClean(sender, message);
    }
}
