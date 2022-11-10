package plugins.fine.commands.warp.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import plugins.fine.commands.ICommand;
import plugins.fine.sql.form.WarpForm;

public class GuiWarpCmd  extends ICommand {

    public GuiWarpCmd() {
        super("warp","gui","", "打开传送点的GUI");
    }

    @Override
    public String permission() {
        return "fine.warp.gui";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                WarpForm.sendWarpFormMain(FloodgateApi.getInstance().getPlayer(player.getUniqueId()));
                return true;
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
        return true;
    }
}
