package edu.java.bot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URLChecker {

    private URLChecker() {
    }

    public static boolean isURL(String text) {
        String regex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
