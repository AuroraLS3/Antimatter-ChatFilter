package com.djrapitops.antimatter.filters;

import com.djrapitops.antimatter.Antimatter;
import java.util.List;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AntiSameChars {

    public static boolean pass(String message) {
        if (!getPlugin(Antimatter.class).getConfig().getBoolean("enabled.antiSamechars")) {
            return true;
        }
        char lastChar = 'Å';
        char secondLast = '¤';
        char thirdLast;
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            thirdLast = secondLast;
            secondLast = lastChar;
            lastChar = chars[i];
            if (lastChar == secondLast && lastChar == chars[i] && secondLast == thirdLast) {
                return false;
            }
        }
        return true;
    }
    
    public static String parseNew(String message) {
        char lastChar = 'Å';
        char secondLast = '¤';
        char thirdLast;
        char[] chars = message.toCharArray();
        StringBuilder newMsg = new StringBuilder(message.length());
        for (int i = 0; i < chars.length; i++) {
            thirdLast = secondLast;
            secondLast = lastChar;
            lastChar = chars[i];
            if (!(lastChar == secondLast && lastChar == chars[i] && secondLast == thirdLast)) {
                newMsg.append(chars[i]);
            }
        }
        return newMsg.toString();
    }

}
