package plugins.fine.commands.home.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.HomeTable;

import java.util.Arrays;

public class DelHomeCmd  extends ICommand {

    public boolean DelHome(Player player, String name) {
        boolean rte =  HomeTable.deleteHome(player.getUniqueId().toString(), name);
        if (rte) {
            player.sendMessage("§l§6[Fine]§a 成功删除一个名为§b`"+ name+ "`§a的家了！");
        } else {
            player.sendMessage("§l§6[Fine]§c 删除失败！你没有一个名为§b"+ name +"§c的家！");
        }
        return true;
    }

    public DelHomeCmd() {
        super("home","家的名字","删除一个家");
        setTabComplete(1, (player) -> HomeTable.getHomeList((player.getUniqueId().toString())));
    }

    @Override
    public String permission() {
        return "fine.home.del";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return DelHome(player, args[0]);
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
