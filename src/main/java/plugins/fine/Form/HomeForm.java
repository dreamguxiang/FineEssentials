package plugins.fine.Form;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.floodgate.api.player.FloodgatePlayer;
import plugins.fine.sql.HomeTable;

import java.util.List;

public class HomeForm {

    public static void sendHomeFormMain(FloodgatePlayer player){
        player.sendForm(SimpleForm.builder()
                .title("家园系统")
                .content("请选择你要操作的类型")
                .button("传送到一个家")
                .button("创建一个家")
                .button("删除一个家")
                .button("查看我的家")
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
        sim.title("传送到一个家");
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
        cust.title("创建一个家");
        cust.input("请输入家的名字", "家的名字");

        cust.validResultHandler(response -> {
            String name = response.asInput(0);
            Player pl = Bukkit.getServer().getPlayer(player.getJavaUniqueId());
            if(pl != null){
                pl.performCommand("home add " + name);
            }
        }).build();
    }

    private static void sendHomeFormDelHome(FloodgatePlayer player){
        SimpleForm.Builder sim =  SimpleForm.builder();
        sim.title("删除一个家");
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
        sim.title("查看我的家");
        List<String> homelist =  HomeTable.getHomeList(player.getJavaUniqueId().toString());
        homelist.forEach(s -> {
            Location pos = HomeTable.getHome(player.getJavaUniqueId().toString(), s);
            sim.label("名字: " + s + " | 位置: " + pos.getBlockX() + " " + pos.getBlockY() + " " + pos.getBlockZ()+ " | 世界：" + pos.getWorld().getName());
        });
        player.sendForm(sim.build());
    }

}
