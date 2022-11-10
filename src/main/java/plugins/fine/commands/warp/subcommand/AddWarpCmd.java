package plugins.fine.commands.warp.subcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.WarpTable;

public class AddWarpCmd extends ICommand {
    public boolean AddWarp(Player player, String name) {
        if(!player.hasPermission("fine.warp.add")){
            player.sendMessage("§l§6[Fine]§c 你没有权限添加传送点！");
            return false;
        }
        boolean ret =  WarpTable.addWarp(name, player.getLocation());
        if (ret) {
            player.sendMessage("§l§6[Fine]§a 成功添加一个名为§b`"+ name+ "`§a的传送点了！");
            return true;
        } else {
            player.sendMessage("§l§6[Fine]§c 添加失败！你已经有一个名为§b`"+ name +"`§c的传送点了！");
        }
        return false;
    }

    public AddWarpCmd() {
        super("warp","传送点的名字","添加一个传送点");
    }
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return AddWarp(player, args[0]);
            } else {
                player.sendMessage("§l§6[Fine]"+ ChatColor.RED + " 参数错误！");
                return false;
            }
        } else {
            sender.sendMessage("§l§6[Fine]"+ChatColor.RED + " 只有玩家才能使用此命令！");
            return false;
        }
    }

    @Override
    public String permission() {
        return "fine.warp.add";
    }

    @Override
    public boolean isBedrockOnly() {
        return false;
    }
}
