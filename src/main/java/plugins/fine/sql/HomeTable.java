package plugins.fine.sql;

import cc.carm.lib.easysql.api.SQLQuery;
import cc.carm.lib.easysql.api.enums.NumberType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class HomeTable extends FineSql{

    public HomeTable(String driver, String url, String user, String password) {
        super(driver, url, user, password);
    }

    public void createTable(){
        sqlManager.createTable("HomeList")
                .addAutoIncrementColumn("id",  true)
                .addColumn("Uuid", "VARCHAR(32) NOT NULL UNIQUE KEY")
                .addColumn("HomeName", "VARCHAR(50) NOT NULL")
                .addColumn("PosX", "DOUBLE NOT NULL")
                .addColumn("PosY", "DOUBLE NOT NULL")
                .addColumn("PosZ", "DOUBLE NOT NULL")
                .addColumn("World", "VARCHAR(32) NOT NULL")
                .addColumn("Yaw", "FLOAT NOT NULL")
                .addColumn("Pitch", "FLOAT NOT NULL")
                .build().execute(null);
    }

    public void addHome(String uuid, String homename, double PosX, double PosY, double PosZ, String world, float yaw, float pitch) {
        sqlManager.createInsert("HomeList")
                .setColumnNames("Uuid", "HomeName", "PosX", "PosY", "PosZ", "World", "Yaw", "Pitch")
                .setParams(uuid, homename, PosX, PosY, PosZ, world)
                .returnGeneratedKey().execute((exception, action) -> {
                    exception.printStackTrace();
                });
    }

    public void addHome(String uuid, String homename, Location location) {
        sqlManager.createInsert("HomeList")
                .setColumnNames("Uuid", "HomeName", "PosX", "PosY", "PosZ", "World", "Yaw", "Pitch")
                .setParams(uuid, homename, location.getX(),  location.getY(),  location.getZ(),  location.getWorld().getName(), location.getYaw(), location.getPitch())
                .returnGeneratedKey().execute((exception, action) -> {
                    exception.printStackTrace();
                });
    }

    public void deleteHome(String uuid, String homename) {
        sqlManager.createDelete("HomeList")
                .addCondition("Uuid", uuid)
                .addCondition("HomeName", homename)
                .build()
                .execute((exception, action) -> {
                    exception.printStackTrace();
                });
    }


    public Location getHome(String uuid, String homename) {
        try (SQLQuery query = sqlManager.createQuery().inTable("HomeList")
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

    public boolean isHomeExist(String uuid, String homename) {
        try (SQLQuery query = sqlManager.createQuery().inTable("HomeList")
                .addCondition("Uuid", uuid)
                .addCondition("HomeName", homename)
                .build().execute()){
            return query.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getHomeCount(String uuid) {
        try (SQLQuery query = sqlManager.createQuery().inTable("HomeList")
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

    public String[] getHomeList(String uuid) {
        try (SQLQuery query = sqlManager.createQuery().inTable("HomeList")
                .addCondition("Uuid", uuid)
                .build().execute()) {
            String[] list = new String[getHomeCount(uuid)];
            int i = 0;
            while (query.getResultSet().next()) {
                list[i] = query.getResultSet().getString("HomeName");
                i++;
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
