package plugins.fine.commands.warp.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.WarpTable;

public class GoWarpCmd extends ICommand {

    public boolean GoWarp(Player player, String name) {
        if(!player.hasPermission("fine.warp.go")){
            player.sendMessage("§l§6[Fine]§c 你没有权限传送到传送点！");
            return false;
        }
        if (!WarpTable.isWarpExist(name)) {
            player.sendMessage("§l§6[Fine]§c 没有一个名为§b"+ name +"§c的传送点！");
            return false;
        }
        player.teleport(WarpTable.getWarp(name));
        player.sendMessage("§l§6[Fine]§a 成功传送到传送点"+ name);
        return true;
    }

    public GoWarpCmd() {
        super("warp", "go","传送点的名字", "传送到一个传送点");
        setTabComplete(1, (player) -> WarpTable.getWarpList());
    }

    @Override
    public String permission() {
        return "fine.warp.go";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return GoWarp(player, args[0]);
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
