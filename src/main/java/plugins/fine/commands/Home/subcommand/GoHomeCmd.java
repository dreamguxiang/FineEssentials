package plugins.fine.commands.home.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.HomeTable;

public class GoHomeCmd  extends ICommand {

    public boolean GoHome(Player player, String name) {
        if (HomeTable.getHomeCount(player.getUniqueId().toString()) == 0) {
            player.sendMessage("§l§6[Fine]§c 你还没有家！");
            return false;
        }
        if (!HomeTable.isHomeExist(player.getUniqueId().toString(), name)) {
            player.sendMessage("§l§6[Fine]§c 你没有一个名为§b" + name + "§c的家！");
            return false;
        }
        player.teleport(HomeTable.getHome(player.getUniqueId().toString(), name));
        player.sendMessage("§l§6[Fine]§a 成功传送到家" + name);
        return true;
    }

    public GoHomeCmd() {
        super("home","go", "家的名字", "传送到一个家");
        setTabComplete(1, (player) -> HomeTable.getHomeList((player.getUniqueId().toString())));
    }

    @Override
    public String permission() {
        return "fine.home.go";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return GoHome(player, args[0]);
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
