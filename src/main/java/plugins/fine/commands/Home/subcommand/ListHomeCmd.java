package plugins.fine.commands.home.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.HomeTable;

import java.util.List;

public class ListHomeCmd  extends ICommand {
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
        player.sendMessage("§l§6[Fine]§a 你一共有§b" + count + "§a个家，分别是：§b" + homes.toString());
        return true;
    }

    public ListHomeCmd() {
        super("home", "list","", "列出所有家");
    }

    @Override
    public String permission() {
        return "fine.home.list";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                return HomeList(player);
            } else {
                player.sendMessage("§l§6[Fine]§c 参数错误！");
                return false;
            }
        } else {
            sender.sendMessage("§l§6[Fine]§c 只有玩家才能使用此命令！");
            return false;
        }
    }
    @Override
    public boolean isBedrockOnly() {
        return false;
    }
}
