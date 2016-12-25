package com.djrapitops.antimatter.filters;

import com.djrapitops.antimatter.Antimatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        boolean stop = true;
        for (String toReplace : replacer.keySet()) {
            if (message.contains(toReplace)) {
                stop = false;
            }
        }
        if (stop) {
            return message;
        }

        String newMsg = message;
        for (String toReplace : replacer.keySet()) {
            newMsg = newMsg.replaceAll(toReplace, replacer.get(toReplace).toLowerCase());
        }
        return newMsg;
    }

    public void reloadRules() {
        List<String> replacerules = getPlugin(Antimatter.class).getConfig().getStringList("replacerules");
        replacer.clear();
        replacerules.parallelStream()
                .map((replacerule) -> replacerule.split(" > "))
                .forEach((rule) -> {
                    try {
                        List<String> permutations = new ArrayList<>();
                        char[] chars = rule[0].toLowerCase().toCharArray();
                        int iterations = (int) Math.pow(2, chars.length);

                        for (int i = 0; i <= iterations; i++) {
                            char[] permutation = new char[chars.length];
                            for (int j = 0; j < chars.length; j++) {
                                permutation[j] = (isBitSet(i, j)) ? Character.toUpperCase(chars[j]) : chars[j];
                            }
                            permutations.add(parseString(permutation));
                        }
                        try {
                            permutations.parallelStream().forEach((perm) -> {
                            replacer.put(perm, rule[1]);
                        });
                        } catch (Exception e) {
                        }
                    } catch (Exception e) {

                    }
                });
    }

    boolean isBitSet(int n, int offset) {
        return (n >> offset & 1) != 0;
    }

    private String parseString(char[] permutation) {
        StringBuilder string = new StringBuilder(permutation.length*2);
        for (int i = 0; i < permutation.length; i++) {
            string.append(permutation[i]);
        }
        return string.toString();
    }
}
