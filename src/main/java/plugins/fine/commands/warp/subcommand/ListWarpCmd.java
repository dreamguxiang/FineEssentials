package plugins.fine.commands.warp.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.WarpTable;

import java.util.List;

public class ListWarpCmd extends ICommand {

    public boolean WarpList(Player player) {
        if(!player.hasPermission("fine.warp.list")){
            player.sendMessage("§l§6[Fine]§c 你没有权限查看传送点列表！");
            return false;
        }
        List<String> list = WarpTable.getWarpList();
        StringBuilder homes = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                homes.append(list.get(i));
            } else {
                homes.append(list.get(i)).append("§a, §b");
            }
        }
        player.sendMessage("§l§6[Fine]§a 一共有§b"+WarpTable.getWarpCount() +"§a个传送点，分别是：§b"+ homes.toString());
        return true;
    }
    public ListWarpCmd() {
        super("warp", "", "列出所有传送点");
    }

    @Override
    public String permission() {
        return "fine.warp.list";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                return WarpList(player);
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
