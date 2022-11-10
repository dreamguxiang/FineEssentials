package plugins.fine.commands.home;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import plugins.fine.commands.ICommand;
import plugins.fine.commands.home.subcommand.*;

import java.util.*;

public class HomeCommandHandler implements TabExecutor {
    private static HomeCommandHandler instance;
    private Map<String, ICommand> commands = new HashMap<>();

    public Map<String, ICommand> getCommands() {
        return commands;
    }

    public  HomeCommandHandler() {
        instance = this;
        initHandler();
    }

    public static HomeCommandHandler getInstance() {
        return instance;
    }

    private void initHandler() {
        commands.put("add", new AddHomeCmd());
        commands.put("del", new DelHomeCmd());
        commands.put("list", new ListHomeCmd());
        commands.put("go", new GoHomeCmd());
        commands.put("gui", new GuiHomeCmd());
    }

    public void registerCommand(ICommand command) {
        commands.put(command.getCmdName(), command);
    }

    public boolean isBedrockPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
        }
        return false;
    }

    public boolean canJavaPlayerExec(ICommand command,CommandSender sender){
        if (command.isBedrockOnly()){
            return isBedrockPlayer(sender);
        }
        return true;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args == null || args.length < 1) {
            sender.sendMessage("§l§6[Fine]§c 未知的命令");
            return true;
        }
        ICommand cmd = commands.get(args[0].toLowerCase());
        try {
            if (cmd != null && sender.hasPermission(cmd.permission())) {
                if (!canJavaPlayerExec( cmd, sender)) {
                    sender.sendMessage("§l§6[Fine]§c 这个命令只能在基岩版客户端使用");
                    return true;
                }
                String[] params = new String[0];
                if (args.length >= 2) {
                    LinkedList<String> list = new LinkedList<>(Arrays.asList(args));
                    list.removeFirst();
                    params = list.toArray(new String[list.size()]);
                }
                boolean res = cmd.onCommand(sender, params);
                if (!res) {
                    sender.sendMessage(cmd.showUsage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "发生了异常：" + e.getMessage());
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args == null || args.length < 1) {
            return null;
        }
        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            String typingStr = args[0].toLowerCase();
            for (String cmdName : commands.keySet()) {
                if (cmdName.startsWith(typingStr)) {
                    ICommand cmd = commands.get(cmdName);
                    if (sender.hasPermission(cmd.permission())) {
                        if (canJavaPlayerExec( cmd, sender)) {
                            result.add(cmdName);
                        }
                    }
                }
            }
        } else {
            ICommand cmd = commands.get(args[0].toLowerCase());
            if (cmd != null) {
                if (cmd.getTabCompletes().size() >= args.length - 1) {
                    result = cmd.getTabComplete(args.length - 1).apply((Player) sender);
                }
            }
        }
        return result;
    }
}
