package edu.java.bot.util;

import java.util.regex.Pattern;

public final class URLChecker {

    private static Pattern pattern = Pattern.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    private URLChecker() {
    }

    public static boolean isURL(String text) {
        return pattern.matcher(text).matches();
    }
}
