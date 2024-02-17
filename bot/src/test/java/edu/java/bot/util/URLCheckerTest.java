package edu.java.bot.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.util.URLChecker.isURL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class URLCheckerTest {
    @DisplayName("Right url (http)")
    @Test
    public void rightURLHttpTest() {
        assertTrue(isURL("http://www.google.com/search?q=+java"));
    }

    @DisplayName("Right url (https)")
    @Test
    public void rightURLHttpsTest() {
        assertTrue(isURL("https://www.google.com/search?q=+java"));
    }

    @DisplayName("Wrong protocol")
    @Test
    public void wrongProtocolTest() {
        assertFalse(isURL("ftp://123.211.34.21"));
    }

    @DisplayName("Wrong url")
    @Test
    public void wrongURLTest() {
        assertFalse(isURL("test"));
    }
}
