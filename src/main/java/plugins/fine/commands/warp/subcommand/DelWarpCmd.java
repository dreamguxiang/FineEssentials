package plugins.fine.commands.warp.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.WarpTable;

public class DelWarpCmd extends ICommand {

    public boolean DelWarp(Player player, String name) {
        if(!player.hasPermission("fine.warp.del")){
            player.sendMessage("§l§6[Fine]§c 你没有权限删除传送点！");
            return false;
        }
        boolean rte =  WarpTable.deleteWarp(name);
        if (rte) {
            player.sendMessage("§l§6[Fine]§a 成功删除一个名为§b"+ name+ "§a的传送点！");
            return true;
        } else {
            player.sendMessage("§l§6[Fine]§c 删除失败！没有一个名为§b"+ name +"§c的传送点！");
        }
        return false;
    }

    public DelWarpCmd() {
        super("warp","传送点的名字","删除一个传送点");
        setTabComplete(1, (player) -> WarpTable.getWarpList());
    }

    @Override
    public String permission() {
        return "fine.warp.del";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return DelWarp(player, args[0]);
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
