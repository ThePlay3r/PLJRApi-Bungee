package me.pljr.pljrapibungee.config;

import me.pljr.pljrapibungee.utils.FormatUtil;
import me.pljr.pljrapibungee.utils.NumberUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigManager {
    private final File file;
    private final Configuration config;
    private final String prefix;
    private final String fileName;
    private final CommandSender console = ProxyServer.getInstance().getConsole();

    private void pathNotFound(String path) {
        this.console.sendMessage(this.prefix + " Path " + path + " was not found in " + this.fileName + " !");
    }

    private void isNotInt(String number, String path) {
        this.console.sendMessage(this.prefix + " " + number + " is not an int on " + path + " in " + this.fileName + " !");
    }

    private void isNotDouble(String number, String path) {
        this.console.sendMessage(this.prefix + " " + number + " is not an double on " + path + " in " + this.fileName + " !");
    }

    private void isNotLong(String number, String path) {
        this.console.sendMessage(this.prefix + " " + number + " is not an long on " + path + " in " + this.fileName + " !");
    }

    private void isNotStringlist(String path) {
        this.console.sendMessage(this.prefix + " Couldn't find a String List on " + path + " in " + this.fileName + " !");
    }

    public ConfigManager(Plugin plugin, String fileName, boolean isResource){
        String pluginName = plugin.getDescription().getName();
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdir();
        File file = new File(dataFolder, fileName);
        Configuration configuration = null;
        try {
            if (!file.exists()){
                if (isResource){
                    InputStream in = plugin.getResourceAsStream(fileName);
                    Files.copy(in, dataFolder.toPath());
                }else{
                    file.createNewFile();
                }
            }
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.file = file;
        this.config = configuration;
        this.prefix = "§c" + pluginName + ":";
        this.fileName = fileName;
    }

    public void save(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        return this.config;
    }

    public String getString(String path) {
        if (this.config.contains(path)) {
            return FormatUtil.colorString(this.config.getString(path));
        } else {
            this.pathNotFound(path);
            return ChatColor.RED + path;
        }
    }

    public int getInt(String path) {
        String number = this.config.getInt(path) + "";
        if (NumberUtil.isInt(number)) {
            return Integer.parseInt(number);
        } else {
            this.isNotInt(this.config.getInt(path) + "", path);
            return 1;
        }
    }

    public double getDouble(String path) {
        String number = this.config.getDouble(path) + "";
        if (NumberUtil.isDouble(number)) {
            return Double.parseDouble(number);
        } else {
            this.isNotDouble(this.config.getDouble(path) + "", path);
            return 1.0D;
        }
    }

    public float getFloat(String path) {
        return (float)this.getDouble(path);
    }

    public long getLong(String path) {
        String number = this.config.getLong(path) + "";
        if (NumberUtil.isLong(number)) {
            return Long.parseLong(number);
        } else {
            this.isNotDouble(this.config.getLong(path) + "", path);
            return 1L;
        }
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public List<String> getStringList(String path) {
        List<String> stringList = this.config.getStringList(path);
        if (stringList.isEmpty()) {
            this.isNotStringlist(path);
            return stringList;
        } else {
            List<String> coloredStringList = new ArrayList();
            Iterator var4 = stringList.iterator();

            while(var4.hasNext()) {
                String string = (String)var4.next();
                coloredStringList.add(FormatUtil.colorString(string));
            }

            return coloredStringList;
        }
    }
}
