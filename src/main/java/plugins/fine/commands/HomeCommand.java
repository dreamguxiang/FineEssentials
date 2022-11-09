package plugins.fine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugins.fine.Form.HomeForm;
import plugins.fine.sql.HomeTable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HomeCommand implements TabExecutor {
    public boolean AddHome(Player player, String name) {
        boolean ret =  HomeTable.addHome(player.getUniqueId().toString(), name, player.getLocation());
        if (ret) {
            player.sendMessage("§6[Fine]§a 成功添加家"+ name);
        } else {
            player.sendMessage("§6[Fine]§c 添加失败！你已经有一个名为§b"+ name +"§c的家了！");
        }
        return true;

    }

    public boolean DelHome(Player player, String name) {
        boolean rte =  HomeTable.deleteHome(player.getUniqueId().toString(), name);
        if (rte) {
            player.sendMessage("§6[Fine]§a 成功删除家"+ name);
        } else {
            player.sendMessage("§6[Fine]§c 删除失败！你没有一个名为§b"+ name +"§c的家！");
        }
        return true;
    }

    public boolean HomeList(Player player) {
        List<String> list = HomeTable.getHomeList(player.getUniqueId().toString());
        StringBuilder homes = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                homes.append(list.get(i));
            } else {
                homes.append(list.get(i)).append("§a, §b");
            }
        }
        Integer count = HomeTable.getHomeCount(player.getUniqueId().toString());
        player.sendMessage("§6[Fine]§a 你一共有§b"+count +"§a个家，分别是：§b"+ homes.toString());
        return true;
    }

    public boolean GoHome(Player player, String name) {
        if (HomeTable.getHomeCount(player.getUniqueId().toString()) == 0) {
            player.sendMessage("§6[Fine]§c 你还没有家！");
            return false;
        }
        if (!HomeTable.isHomeExist(player.getUniqueId().toString(), name)) {
            player.sendMessage("§6[Fine]§c 你没有一个名为§b"+ name +"§c的家！");
            return false;
        }
        player.teleport(HomeTable.getHome(player.getUniqueId().toString(), name));
        player.sendMessage("§6[Fine]§a 成功传送到家"+ name);
        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "add": {
                        if (args.length == 2) {
                            AddHome(player, args[1]);
                        }
                        break;
                    }
                    case "del": {
                        if (args.length == 2) {
                            DelHome(player, args[1]);
                        }
                        break;
                    }
                    case "list": {
                        HomeList(player);
                        break;
                    }
                    case "go": {
                        if (args.length == 2) {
                            GoHome(player, args[1]);
                        }
                        break;
                    }
                    case "gui":{
                        if(FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())){
                            HomeForm.sendHomeFormMain(FloodgateApi.getInstance().getPlayer(player.getUniqueId()));
                        }else{
                            player.sendMessage("§6[Fine]§c 你不是基岩版玩家！");
                        }
                    }
                }
            }
        }
        return true;
    }


    //代码待优化部分
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        LinkedList<String> tips = new LinkedList<>();
        if (args.length == 1) {
            List<String> firstArgList = Arrays.asList("add", "del", "list","go");
            if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())){
                firstArgList = Arrays.asList("gui");
            };
            if (args[0].isEmpty()) {
                tips.addAll(firstArgList);
                return tips;
            }else{
                for (String firstArg : firstArgList) {
                    if (firstArg.toLowerCase().startsWith(args[0].toLowerCase())) {
                        tips.add(firstArg);
                    }
                }
                return tips;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add".toLowerCase())) {
                  return tips;
            }
            if (args[0].equalsIgnoreCase("list".toLowerCase())) {
                    return tips;
            }
            if (args[0].equalsIgnoreCase("del".toLowerCase())) {
                List<String> secondArgList = HomeTable.getHomeList(((Player)sender).getUniqueId().toString());
                if (args[1].isEmpty()) {
                    tips.addAll(secondArgList);
                    return tips;
                } else {
                    secondArgList.forEach(secondArg -> {
                        if (secondArg.toLowerCase().startsWith(args[1].toLowerCase())) {
                            tips.add(secondArg);
                        }
                    });
                    return tips;
                }
            }
            if (args[0].equalsIgnoreCase("go".toLowerCase())) {
                List<String> secondArgList = HomeTable.getHomeList(((Player)sender).getUniqueId().toString());
                if (args[1].isEmpty()) {
                    tips.addAll(secondArgList);
                    return tips;
                } else {
                    secondArgList.forEach(secondArg -> {
                        if (secondArg.toLowerCase().startsWith(args[1].toLowerCase())) {
                            tips.add(secondArg);
                        }
                    });
                    return tips;
                }
            }
        }
        if (args.length == 3) {
            return tips;
        }
        return null;
    }
}


