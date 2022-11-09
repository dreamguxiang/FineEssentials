package plugins.fine.Form;

import plugins.fine.sql.FineDatabase;

public class WarpForm {

    static public void createTable(){
        FineDatabase.getInstance().sqlManager.createTable("HomeList")
                .addAutoIncrementColumn("id",  true)
                .addColumn("WarpName", "VARCHAR(50) NOT NULL")
                .addColumn("PosX", "DOUBLE NOT NULL")
                .addColumn("PosY", "DOUBLE NOT NULL")
                .addColumn("PosZ", "DOUBLE NOT NULL")
                .addColumn("World", "VARCHAR(32) NOT NULL")
                .addColumn("Yaw", "FLOAT NOT NULL")
                .addColumn("Pitch", "FLOAT NOT NULL")
                .build().execute(null);
    }



}
