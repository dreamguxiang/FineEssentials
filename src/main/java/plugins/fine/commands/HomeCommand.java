package plugins.fine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugins.fine.sql.HomeTable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HomeCommand implements TabExecutor {

    public boolean AddHome(Player player, String name) {
        HomeTable.addHome(player.getUniqueId().toString(), name, player.getLocation());
        player.sendMessage("§6[Fine]§a 设置家成功！");
        return true;
    }

    public boolean DelHome(Player player, String name) {
        return true;
    }

    public boolean HomeList(Player player) {
        List<String> list = HomeTable.getHomeList(player.getUniqueId().toString());
        StringBuilder homes = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                homes.append(list.get(i));
            } else {
                homes.append(list.get(i)).append(", ");
            }
        }
        Integer count = HomeTable.getHomeCount(player.getUniqueId().toString());
        player.sendMessage("§6[Fine]§a 你一共有§b"+count +"§a个家，分别是：§b", homes.toString());
        return true;
    }

    public boolean GoHome(Player player, String name) {
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
                        player.sendMessage("Delete home");
                        break;
                    }
                    case "list": {
                        HomeList(player);
                        break;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        LinkedList<String> tips = new LinkedList<>();
        if (args.length == 1) {
            List<String> firstArgList = Arrays.asList("add", "del", "list");
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
        }
        return null;
    }
}
