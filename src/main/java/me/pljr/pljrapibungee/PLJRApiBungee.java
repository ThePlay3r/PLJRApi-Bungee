package me.pljr.pljrapibungee;

import lombok.Getter;
import me.pljr.pljrapibungee.channels.ChatChannel;
import me.pljr.pljrapibungee.config.ConfigManager;
import me.pljr.pljrapibungee.config.Lang;
import me.pljr.pljrapibungee.database.DataSource;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class PLJRApiBungee extends Plugin {
    private static PLJRApiBungee instance;

    @Getter private BungeeAudiences bungeeAudiences;
    private DataSource dataSource;

    private ConfigManager configFile;

    private ChatChannel chatChannel;

    public static PLJRApiBungee get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.setupMiniMessage();
        this.setupChannels();
        this.setupConfig();
        this.setupListeners();
        this.setupDatabase();
    }

    private void setupConfig() {
        configFile = new ConfigManager(this, "config.yml", true);
        Lang.load(new ConfigManager(this, "lang.yml", false));
    }

    private void setupMiniMessage() {
        bungeeAudiences = BungeeAudiences.create(this);
    }

    private void setupChannels() {
        chatChannel = new ChatChannel(getProxy());
    }

    private void setupListeners() {
        PluginManager pluginManager = this.getProxy().getPluginManager();
        pluginManager.registerListener(this, chatChannel);
    }

    private void setupDatabase() {
        dataSource = new DataSource(configFile);
        dataSource.initPool();
    }

    /**
     * Sends DataSource from provided configuration, if enabled.
     * PLJRApi's DataSource otherwise.
     *
     * @param config {@link DataSource} that will be checked for enabled MySQL.
     * @return {@link DataSource} from provided config, PLJRApi's otherwise.
     */
    public DataSource getDataSource(ConfigManager config){
        if (config.getBoolean("mysql.enabled")){
            return new DataSource(config);
        }
        return dataSource;
    }


    @Override
    public void onDisable() {
        instance = null;
    }
}
