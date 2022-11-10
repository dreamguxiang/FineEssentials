package plugins.fine.sql;

import cc.carm.lib.easysql.api.SQLQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class WarpTable {
    static public void createTable(){
        FineDatabase.getInstance().sqlManager.createTable("WarpList")
                .addAutoIncrementColumn("id",  true)
                .addColumn("WarpName", "VARCHAR(50) NOT NULL UNIQUE KEY")
                .addColumn("PosX", "DOUBLE NOT NULL")
                .addColumn("PosY", "DOUBLE NOT NULL")
                .addColumn("PosZ", "DOUBLE NOT NULL")
                .addColumn("World", "VARCHAR(32) NOT NULL")
                .addColumn("Yaw", "FLOAT NOT NULL")
                .addColumn("Pitch", "FLOAT NOT NULL")
                .build().execute(null);
    }

    static public boolean addWarp(String homename, double PosX, double PosY, double PosZ, String world, float yaw, float pitch) {
        if (isWarpExist(homename)) return false;
        FineDatabase.getInstance().sqlManager.createInsert("WarpList")
                .setColumnNames("WarpName", "PosX", "PosY", "PosZ", "World", "Yaw", "Pitch")
                .setParams(homename, PosX, PosY, PosZ, world, yaw, pitch)
                .returnGeneratedKey().execute((exception, action) -> {
                    exception.printStackTrace();
                });
        return true;
    }

    static public boolean addWarp(String warpname, Location location) {
        if (isWarpExist(warpname)) return false;
        FineDatabase.getInstance().sqlManager.createInsert("WarpList")
                .setColumnNames("WarpName", "PosX", "PosY", "PosZ", "World", "Yaw", "Pitch")
                .setParams(warpname, location.getX(),  location.getY(),  location.getZ(),  location.getWorld().getName(), location.getYaw(), location.getPitch())
                .returnGeneratedKey().execute((exception, action) -> {
                    exception.printStackTrace();
                });
        return true;
    }

    static public boolean deleteWarp(String warpname) {
        if (!isWarpExist(warpname)) return false;
        FineDatabase.getInstance().sqlManager.createDelete("WarpList")
                .addCondition("WarpName", warpname)
                .build()
                .execute((exception, action) -> {
                    exception.printStackTrace();
                });
        return true;
    }

    static public boolean isWarpExist(String warpname) {
        try (SQLQuery query = FineDatabase.getInstance().sqlManager.createQuery().inTable("WarpList")
                .addCondition("WarpName", warpname)
                .build().execute()){
            return query.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static public int getWarpCount() {
        try (SQLQuery query = FineDatabase.getInstance().sqlManager.createQuery().inTable("WarpList")
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

    static public Location getWarp(String warpname) {
        try (SQLQuery query = FineDatabase.getInstance().sqlManager.createQuery().inTable("WarpList")
                .addCondition("WarpName", warpname)
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

    static public List<String> getWarpList() {
        try (SQLQuery query = FineDatabase.getInstance().sqlManager.createQuery()
                .inTable("WarpList")
                .build().execute()) {
            List<String> list = new java.util.ArrayList<>();

            for (int i = 0; query.getResultSet().next(); i++) {
                list.add(query.getResultSet().getString("WarpName"));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
