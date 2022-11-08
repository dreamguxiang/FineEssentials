package plugins.fine.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private static FileConfiguration config;

    public Config(JavaPlugin plugin) {
        config = plugin.getConfig();
    }
}
