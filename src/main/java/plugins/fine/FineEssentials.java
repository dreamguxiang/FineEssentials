package plugins.fine;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import plugins.fine.commands.HomeCommand;
import plugins.fine.sql.FineSql;
import plugins.fine.sql.HomeTable;

public final class FineEssentials extends JavaPlugin {
    private static FineEssentials Instance;
    FileConfiguration config;
    FineSql fineSql;
    private void registerCommands() {
        getCommand("home").setExecutor(new HomeCommand());
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
        fineSql = new FineSql(driver, url, user, password);
    }

    private void initTable() {
        ((HomeTable)fineSql).createTable();
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
        FineSql.closeSql();
    }

    public static FineEssentials getInstance() {
        return Instance;
    }

}
