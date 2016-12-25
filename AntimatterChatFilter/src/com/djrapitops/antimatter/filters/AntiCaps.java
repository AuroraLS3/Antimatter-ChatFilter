package com.djrapitops.antimatter.filters;

import com.djrapitops.antimatter.Antimatter;
import java.util.ArrayList;
import java.util.List;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AntiCaps {

    public static boolean pass(String message) {
        if (!getPlugin(Antimatter.class).getConfig().getBoolean("enabled.antiCaps")) {
            return true;
        }
        if (message.length() <= 3) {
            return true;
        }
        int percent = 40;
        try {
            percent = getPlugin(Antimatter.class).getConfig().getInt("maxcapspercent");
        } catch (Exception e) {
            getPlugin(Antimatter.class).logError("Config: maxcapspercent is not valid Integer, using default");
        }
        double per = percent * 0.01;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        List<Character> bigLetters = new ArrayList<>();
        for (Character letter : alphabet) {
            bigLetters.add(letter);
        }
        int bigChars = 0;
        for (Character letter : message.toCharArray()) {
            if (bigLetters.contains(letter)) {
                bigChars++;
            }
        }
        return (bigChars * 1.0 / message.length()) <= per;
    }

    public static String parseNewMsg(String message) {
        return message.charAt(0)+message.substring(1).toLowerCase();
    }
}
