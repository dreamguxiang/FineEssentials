package plugins.fine.commands.home.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import plugins.fine.commands.ICommand;
import plugins.fine.form.HomeForm;

public class GuiHomeCmd  extends ICommand {

    public GuiHomeCmd() {
        super("home", "", "打开家的GUI");
    }

    @Override
    public String permission() {
        return "fine.home.gui";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                HomeForm.sendHomeFormMain(FloodgateApi.getInstance().getPlayer(player.getUniqueId()));
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
