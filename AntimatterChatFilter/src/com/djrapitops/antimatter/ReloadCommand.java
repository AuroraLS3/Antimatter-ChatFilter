package com.djrapitops.antimatter;

import com.djrapitops.antimatter.filters.AntiReplacer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class ReloadCommand implements CommandExecutor {

    private final AntiReplacer replacer;

    public ReloadCommand(AntiReplacer replacer) {
        this.replacer = replacer;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        boolean console = !(cs instanceof Player);
        if (!console) {
            if (!cs.hasPermission("antimatter.reload")) {
                cs.sendMessage(ChatColor.RED + "You do not have permission for this command!");
                return true;
            }
        }
        getPlugin(Antimatter.class).reloadConfig();
        replacer.reloadRules();
        cs.sendMessage(ChatColor.GREEN+"[Antimatter] Plugin config reloaded");
        return true;
    }
}
