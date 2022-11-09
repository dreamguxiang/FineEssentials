package plugins.fine.sql;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import org.bukkit.Bukkit;
import plugins.fine.FineEssentials;

import java.sql.SQLException;
import java.util.Objects;

public class FineDatabase {
    private static FineDatabase Instance;
    public SQLManager sqlManager;

    public FineDatabase(String driver, String url, String user, String password) {
        sqlManager = EasySQL.createManager(driver, url, user, password);

        try {
            if (!sqlManager.getConnection().isValid(5)){
                FineEssentials.getInstance().getLogger().info("数据库连接超时！插件已关闭！");
                Bukkit.getPluginManager().disablePlugin(FineEssentials.getInstance());
            };

        } catch (SQLException e) {
            FineEssentials.getInstance().getLogger().info("数据库连接失败！请检查配置文件！");
            Bukkit.getPluginManager().disablePlugin(FineEssentials.getInstance());
            return;
        }
        Instance = this;
    }

    public static void closeSql(){
        if (Objects.nonNull(FineDatabase.getInstance().sqlManager)){
            EasySQL.shutdownManager(FineDatabase.getInstance().sqlManager);
        }
    }
    public static FineDatabase getInstance() {
        return Instance;
    }
}
