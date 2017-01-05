package com.djrapitops.antimatter.filters;

import com.djrapitops.antimatter.Antimatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AntiReplacer {

    private final HashMap<String, String> replacer;

    public AntiReplacer() {
        this.replacer = new HashMap<>();
        reloadRules();
    }

    public String parseMessage(String message) {
        if (!getPlugin(Antimatter.class).getConfig().getBoolean("enabled.antiReplacer")) {
            return message;
        }
        if (replacer.isEmpty()) {
            reloadRules();
        }
        String newMsg = message;
        for (String toReplace : replacer.keySet()) {
            newMsg = newMsg.replaceAll("(?i)" + toReplace, ChatColor.translateAlternateColorCodes('&', replacer.get(toReplace)));
        }
        return newMsg;
    }

    public void reloadRules() {
        List<String> replacerules = getPlugin(Antimatter.class).getConfig().getStringList("replacerules");
        replacer.clear();
        replacerules.parallelStream()
                .map((replacerule) -> replacerule.split(" > "))
                .forEach((rule) -> {
                    replacer.put(rule[0], rule[1]);
                });
    }

    boolean isBitSet(int n, int offset) {
        return (n >> offset & 1) != 0;
    }

    private String parseString(char[] permutation) {
        StringBuilder string = new StringBuilder(permutation.length * 2);
        for (int i = 0; i < permutation.length; i++) {
            string.append(permutation[i]);
        }
        return string.toString();
    }
}
