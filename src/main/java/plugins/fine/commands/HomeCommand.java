package plugins.fine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.sql.FineSql;
import plugins.fine.sql.HomeTable;

public class HomeCommand implements CommandExecutor {

    HomeTable homeTable = (HomeTable)FineSql.getInstance();
    public boolean SetHome(Player player, String name) {
        homeTable.addHome(player.getUniqueId().toString(), name, player.getLocation());
        return true;
    }
    public boolean DelHome(Player player, String name) {
        return true;
    }

    public boolean HomeList(Player player) {
        String[] list = homeTable.getHomeList(player.getUniqueId().toString());
        StringBuilder homes = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            if (i == list.length - 1) {
                homes.append(list[i]);
            } else {
                homes.append(list[i]).append(", ");
            }
        }
        player.sendMessage("你的家列表：", homes.toString());
        return true;
    }

    public boolean GoHome(Player player, String name) {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("home")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length > 0){
                    switch (args[0].toLowerCase()) {
                        case "set": {
                            if (args.length == 2) {
                                SetHome(player, args[1]);
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
        }
        return false;
    }
}
