package com.djrapitops.antimatter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {

    public ClearChatCommand() {

    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        boolean console = !(cs instanceof Player);
        if (!console) {
            if (!cs.hasPermission("antimatter.clear")) {
                cs.sendMessage(ChatColor.RED + "You do not have permission for this command!");
                return true;
            }
        }
        Bukkit.getServer().getOnlinePlayers().parallelStream().forEach((p) -> {
            for (int i = 0; i < 150; i++) {
                p.sendMessage(" ");
            }
        });
        return true;
    }

}
