package plugins.fine.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public abstract class ICommand {


    private String cmdName;
    /**
     * 子指令名
     */
    private String subcmdName;

    /**
     * 子指令参数
     */
    private String params;

    /**
     * 子指令描述
     */
    private String info;
    /**
     * 补全参数
     */
    private HashMap<Integer, Function<Player,List<String>>> tabComplete = new HashMap<>();


    public ICommand(String cmdName) {
        this.cmdName = cmdName;
    }

    public ICommand(String cmdName,String subcmdName, String params,String usage) {
        this.cmdName = cmdName;
        this.subcmdName = subcmdName;
        this.info = usage;
        this.params = params;
    }

    public String getCmdName() {
        return cmdName;
    }


    public String showUsage() {
        return ChatColor.AQUA + getCmdName() + " §r" + subcmdName + " "+ params + " -- " + info;
    }
    /**
     * 指令内容
     * @param sender    发送者
     * @param args      参数
     * @return          是否执行成功
     */
    public abstract boolean onCommand(CommandSender sender, String[] args);

    /**
     * 指令权限
     * @return        权限
     */
    public abstract String permission();

    public abstract boolean isBedrockOnly();

    public void setTabComplete(Integer index, Function<Player,List<String>> a1) {
        tabComplete.put(index,a1);
    }

    public HashMap<Integer, Function<Player,List<String>>> getTabCompletes() {
        return tabComplete;
    }

    public  Function<Player,List<String>> getTabComplete(Integer index) {
        return tabComplete.get(index);
    }
}