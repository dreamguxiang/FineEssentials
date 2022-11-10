package plugins.fine;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import plugins.fine.commands.home.HomeCommandHandler;
import plugins.fine.commands.warp.WarpCommandHandler;
import plugins.fine.sql.FineDatabase;
import plugins.fine.sql.HomeTable;
import plugins.fine.sql.WarpTable;

public final class FineEssentials extends JavaPlugin {
    private static FineEssentials Instance;
    FileConfiguration config;
    FineDatabase fineSql;
    private void registerCommands() {
        getCommand("home").setExecutor(new HomeCommandHandler());
        getCommand("warp").setExecutor(new WarpCommandHandler());
    }

    private void initConfig() {
        saveDefaultConfig();
        reloadConfig();
        config = getConfig();
    }
    private void initSqlManager() {
        String driver = config.getString("ess.database.driver");
        String url = config.getString("ess.database.url");
        String user = config.getString("ess.database.username");
        String password = config.getString("ess.database.password");

        if(StringUtils.isBlank(driver) || StringUtils.isBlank(url) || StringUtils.isBlank(user) || StringUtils.isBlank(password)) {
            getLogger().warning("Database config is not complete, please check your config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        fineSql = new FineDatabase(driver, url, user, password);
    }

    private void initTable() {
        WarpTable.createTable();
        HomeTable.createTable();
    }

    @Override
    public void onEnable() {
        initConfig();
        registerCommands();
        initSqlManager();
        initTable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        FineDatabase.closeSql();
    }

    public static FineEssentials getInstance() {
        return Instance;
    }

}
