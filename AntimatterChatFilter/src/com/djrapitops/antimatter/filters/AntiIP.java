
package com.djrapitops.antimatter.filters;

import com.djrapitops.antimatter.Antimatter;
import java.util.Arrays;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AntiIP {

    public static boolean pass(String message) {
        if (!getPlugin(Antimatter.class).getConfig().getBoolean("enabled.antiIp")) {
            return true;
        }
        return !message.matches(".*([01]?\\d\\d?|2[0-4]\\d|25[0-5])." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5]).*");
    }
    
}
