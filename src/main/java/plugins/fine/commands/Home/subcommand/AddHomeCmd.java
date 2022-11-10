package plugins.fine.commands.home.subcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.HomeTable;

import java.util.Arrays;

public class AddHomeCmd extends ICommand {

    public boolean AddHome(Player player, String name) {
        boolean ret =  HomeTable.addHome(player.getUniqueId().toString(), name, player.getLocation());
        if (ret) {
            player.sendMessage("§l§6[Fine]§a 成功添加一个名为§b"+ name+ "§a的家了！");
        } else {
            player.sendMessage("§l§6[Fine]§c 添加失败！你已经有一个名为§b"+ name +"§c的家了！");
        }
        return true;
    }

    public AddHomeCmd() {
        super("home","家的名字","添加一个家");
    }
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return AddHome(player, args[0]);
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
        return "fine.home.add";
    }

    @Override
    public boolean isBedrockOnly() {
        return false;
    }
}
