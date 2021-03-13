package me.pljr.pljrapibungee.utils;

import me.pljr.pljrapibungee.config.Lang;
import net.md_5.bungee.api.ChatColor;

import java.util.Random;
import java.util.StringJoiner;

public final class FormatUtil {

    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String formatTime(long sec) {
        long seconds = sec % 60L;
        long minutes = sec / 60L;
        if (minutes >= 60L) {
            long hours = minutes / 60L;
            minutes %= 60L;
            if (hours >= 24L) {
                long days = hours / 24L;
                return String.format(Lang.TIME_FORMAT_DAYS.get(), days, hours % 24L, minutes, seconds);
            } else {
                return String.format(Lang.TIME_FORMAT_HOURS.get(), hours, minutes, seconds);
            }
        } else {
            return String.format(Lang.TIME_FORMAT_MINUTES.get(), minutes, seconds);
        }
    }

    public static String scramble(String inputString) {
        char[] a = inputString.toCharArray();

        for(int i = 0; i < a.length; ++i) {
            int j = (new Random()).nextInt(a.length);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        return new String(a);
    }

    public static String arrayToString(String[] array) {
        StringJoiner joiner = new StringJoiner("");

        for (String s : array) {
            joiner.add(s);
        }

        return joiner.toString();
    }
}
