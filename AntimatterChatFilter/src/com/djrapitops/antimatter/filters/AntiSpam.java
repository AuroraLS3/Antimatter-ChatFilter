
package com.djrapitops.antimatter.filters;

import com.djrapitops.antimatter.Antimatter;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AntiSpam {
    
    public static boolean pass(AsyncPlayerChatEvent event) {
        if (!getPlugin(Antimatter.class).getConfig().getBoolean("enabled.antiSpam")) {
            return true;
        }
        String lastMessage = "";
        try {
            lastMessage = getPlugin(Antimatter.class).getLastMessages().get(event.getPlayer().getUniqueId());
        } catch (Exception e) {
            return true;
        }
        if (lastMessage == null) {
            return true;
        }
        String message = event.getMessage();
        int lenght;
        if (lastMessage.length() > message.length()) {
            lenght = message.length();
        } else {
            lenght = lastMessage.length();
        }
        int same = 0;
        for (int i = 0; i < lenght; i++) {
            if (message.toLowerCase().charAt(i) == lastMessage.charAt(i)) {
                same++;
            }
        }
        int percent = 90;
        try {
            percent = getPlugin(Antimatter.class).getConfig().getInt("spamsimilaritypercent");
        } catch (Exception e) {
            getPlugin(Antimatter.class).logError("Config: maxcapspercent is not valid Integer, using default");
        }
        double per = percent * 0.01;
        return (same * 1.0 / lenght) < per;
    }
}
