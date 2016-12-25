package com.djrapitops.antimatter.listener;

import com.djrapitops.antimatter.Antimatter;
import com.djrapitops.antimatter.filters.AntiCaps;
import com.djrapitops.antimatter.filters.AntiIP;
import com.djrapitops.antimatter.filters.AntiSameChars;
import com.djrapitops.antimatter.filters.AntiSpam;
import com.djrapitops.antimatter.filters.AntiUrl;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AntimatterChatListener implements Listener {

    private final Antimatter plugin;

    public AntimatterChatListener(Antimatter plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("antimatter.bypass")) {
            return;
        }
        String blocked = "[Antimatter] Your message was blocked by chat filter ";
        boolean bypassIP = p.hasPermission("antimatter.bypass.ip");
        boolean bypassUrl = p.hasPermission("antimatter.bypass.url");
        boolean bypassSpam = p.hasPermission("antimatter.bypass.spam");
        String msg = event.getMessage();
        boolean sendMsg = getPlugin(Antimatter.class).getConfig().getBoolean("sendMsgSenderMsgIfBlocked");
        if (!bypassIP) {
            if (!AntiIP.pass(msg)) {
                event.setCancelled(true);
                if (sendMsg) {
                    event.getPlayer().sendMessage(ChatColor.RED + blocked + "(IP)");
                }
                return;
            }
        }
        if (!bypassUrl) {
            if (!AntiUrl.pass(msg)) {
                event.setCancelled(true);
                if (sendMsg) {
                    event.getPlayer().sendMessage(ChatColor.RED + blocked + "(Url)");
                }
                return;
            }
        }
        if (!bypassSpam) {
            if (!AntiSpam.pass(event)) {
                event.setCancelled(true);
                if (sendMsg) {
                    event.getPlayer().sendMessage(ChatColor.RED + blocked + "(Spam)");
                }
            }
        }
        boolean bypassFilter = p.hasPermission("antimatter.bypass.customfilter");
        boolean bypassCaps = p.hasPermission("antimatter.bypass.caps");
        boolean bypassChars = p.hasPermission("antimatter.bypass.char");
        String newMsg = msg;
        if (!bypassFilter) {
            newMsg = plugin.getReplacer().parseMessage(msg);
        }
        if (!bypassCaps) {
            if (!AntiCaps.pass(newMsg) && !bypassCaps) {
                newMsg = AntiCaps.parseNewMsg(newMsg);
            }
        }
        List<String> names = getPlayerNames();
        for (String name : names) {
            if (newMsg.contains(name.toLowerCase())) {
                newMsg = newMsg.replaceAll(name.toLowerCase(), name);
            }
        }
        if (!bypassChars) {
            if (!AntiSameChars.pass(newMsg) && !bypassChars) {
                newMsg = AntiSameChars.parseNew(newMsg);
            }
        }
        plugin.getLastMessages().put(event.getPlayer().getUniqueId(), msg.toLowerCase());
        event.setMessage(newMsg);
    }

    private List<String> getPlayerNames() {
        List<String> names = new ArrayList<>();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            names.add(p.getName());
        }
        return names;
    }
}
