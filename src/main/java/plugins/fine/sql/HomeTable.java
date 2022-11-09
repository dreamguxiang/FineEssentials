package plugins.fine.sql;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.SQLQuery;
import cc.carm.lib.easysql.api.SQLTable;
import cc.carm.lib.easysql.api.enums.NumberType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;

public class HomeTable {

    static public void createTable(){
        FineSql.getInstance().sqlManager.createTable("HomeList")
                .addAutoIncrementColumn("id",  true)
                .addColumn("Uuid", "TEXT NOT NULL")
                .addColumn("HomeName", "VARCHAR(50) NOT NULL")
                .addColumn("PosX", "DOUBLE NOT NULL")
                .addColumn("PosY", "DOUBLE NOT NULL")
                .addColumn("PosZ", "DOUBLE NOT NULL")
                .addColumn("World", "VARCHAR(32) NOT NULL")
                .addColumn("Yaw", "FLOAT NOT NULL")
                .addColumn("Pitch", "FLOAT NOT NULL")
                .build().execute(null);
    }

    static public void addHome(String uuid, String homename, double PosX, double PosY, double PosZ, String world, float yaw, float pitch) {
        FineSql.getInstance().sqlManager.createInsert("HomeList")
                .setColumnNames("Uuid", "HomeName", "PosX", "PosY", "PosZ", "World", "Yaw", "Pitch")
                .setParams(uuid, homename, PosX, PosY, PosZ, world, yaw, pitch)
                .returnGeneratedKey().execute((exception, action) -> {
                    exception.printStackTrace();
                });
    }

    static public void addHome(String uuid, String homename, Location location) {
        FineSql.getInstance().sqlManager.createInsert("HomeList")
                .setColumnNames("Uuid", "HomeName", "PosX", "PosY", "PosZ", "World", "Yaw", "Pitch")
                .setParams(uuid, homename, location.getX(),  location.getY(),  location.getZ(),  location.getWorld().getName(), location.getYaw(), location.getPitch())
                .returnGeneratedKey().execute((exception, action) -> {
                    exception.printStackTrace();
                });
    }

    static public void deleteHome(String uuid, String homename) {
        FineSql.getInstance().sqlManager.createDelete("HomeList")
                .addCondition("Uuid", uuid)
                .addCondition("HomeName", homename)
                .build()
                .execute((exception, action) -> {
                    exception.printStackTrace();
                });
    }


    static public Location getHome(String uuid, String homename) {
        try (SQLQuery query =         FineSql.getInstance().sqlManager.createQuery().inTable("HomeList")
                .addCondition("Uuid", uuid)
                .addCondition("HomeName", homename)
                .build().execute()){
            if (query.getResultSet().next()){
                return new Location(Bukkit.getWorld(query.getResultSet().getString("World")),
                        query.getResultSet().getDouble("PosX"),
                        query.getResultSet().getDouble("PosY"),
                        query.getResultSet().getDouble("PosZ"),
                        query.getResultSet().getFloat("Yaw"),
                        query.getResultSet().getFloat("Pitch"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean isHomeExist(String uuid, String homename) {
        try (SQLQuery query =         FineSql.getInstance().sqlManager.createQuery().inTable("HomeList")
                .addCondition("Uuid", uuid)
                .addCondition("HomeName", homename)
                .build().execute()){
            return query.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static public int getHomeCount(String uuid) {
        try (SQLQuery query =         FineSql.getInstance().sqlManager.createQuery().inTable("HomeList")
                .addCondition("Uuid", uuid)
                .build().execute()) {
            int count = 0;
            while (query.getResultSet().next()) {
                count++;
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    static public List<String> getHomeList(String uuid) {
        try (SQLQuery query = FineSql.getInstance().sqlManager.createQuery()
                .inTable("HomeList")
                .selectColumns("Uuid", "HomeName")
                .addCondition("Uuid", uuid)
                .build().execute()) {
            List<String> list = new java.util.ArrayList<>();

            for (int i = 0; query.getResultSet().next(); i++) {
                list.add(query.getResultSet().getString("HomeName"));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
