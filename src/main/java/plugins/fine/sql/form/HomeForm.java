package plugins.fine.sql.form;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.floodgate.api.player.FloodgatePlayer;
import plugins.fine.sql.HomeTable;

import java.util.List;

public class HomeForm {

    public static void sendHomeFormMain(FloodgatePlayer player){
        player.sendForm(SimpleForm.builder()
                .title("§d家园系统")
                .content("§b请选择你要操作的类型")
                .button("§l§6传送到一个家")
                .button("§l§a创建一个家")
                .button("§l§c删除一个家")
                .button("§l§e查看我的家")
                .validResultHandler(response -> {
                    if (response.clickedButtonId() == 0) {
                        sendHomeFormGoHome(player);
                    } else if (response.clickedButtonId() == 1) {
                        sendHomeFormAddHome(player);
                    } else if (response.clickedButtonId() == 2) {
                        sendHomeFormDelHome(player);
                    } else if (response.clickedButtonId() == 3) {
                        sendHomeFormListHome(player);
                    }
                }).build());
    }

    private static void sendHomeFormGoHome(FloodgatePlayer player){
        SimpleForm.Builder sim =  SimpleForm.builder();
        sim.title("§d传送到一个家");
        List<String> homelist =  HomeTable.getHomeList(player.getJavaUniqueId().toString());
        homelist.forEach(sim::button);
        player.sendForm(sim.validResultHandler(response -> {
            homelist.forEach(s -> {
                if (response.clickedButtonId() == homelist.indexOf(s)) {
                    Player pl = Bukkit.getServer().getPlayer(player.getJavaUniqueId());
                    if(pl != null){
                        pl.performCommand("home go " + s);
                    }
                }
            });
        }).build());
    }

    private static void sendHomeFormAddHome(FloodgatePlayer player){
        CustomForm.Builder cust = CustomForm.builder();
        cust.title("§d创建一个家");
        cust.input("§l§6请输入家的名字", "家的名字");

        player.sendForm( cust.validResultHandler(response -> {
            String name = response.asInput(0);
            Player pl = Bukkit.getServer().getPlayer(player.getJavaUniqueId());
            if(pl != null){
                pl.performCommand("home add " + name);
            }
        }).build());
    }

    private static void sendHomeFormDelHome(FloodgatePlayer player){
        SimpleForm.Builder sim =  SimpleForm.builder();
        sim.title("§d删除一个家");
        List<String> homelist =  HomeTable.getHomeList(player.getJavaUniqueId().toString());
        homelist.forEach(sim::button);
        player.sendForm(sim.validResultHandler(response -> {
            homelist.forEach(s -> {
                if (response.clickedButtonId() == homelist.indexOf(s)) {
                    Player pl = Bukkit.getServer().getPlayer(player.getJavaUniqueId());
                    if(pl != null){
                        pl.performCommand("home del " + s);
                    }
                }
            });
        }).build());
    }
    private  static void sendHomeFormListHome(FloodgatePlayer player){
        CustomForm.Builder sim =  CustomForm.builder();
        sim.title("§d查看我的家");
        List<String> homelist =  HomeTable.getHomeList(player.getJavaUniqueId().toString());
        homelist.forEach(s -> {
            Location pos = HomeTable.getHome(player.getJavaUniqueId().toString(), s);
            sim.label("§a名字: §b" + s + " §a| 位置: §5" + pos.getBlockX() + " " + pos.getBlockY() + " " + pos.getBlockZ()+ " §a| 世界：§6" + pos.getWorld().getName());
        });
        player.sendForm(sim.build());
    }

}
