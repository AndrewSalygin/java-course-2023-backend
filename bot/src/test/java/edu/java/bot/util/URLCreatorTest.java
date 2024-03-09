package edu.java.bot.util;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class URLCreatorTest {

    @Test
    public void createURL_ValidLink_ShouldReturnURLObject() throws MalformedURLException {
        String validLink = "https://google.com";

        URL url = URLCreator.createURL(validLink);

        assertEquals(validLink, url.toString());
    }

    @Test
    public void createURL_InvalidLink_ShouldThrowRuntimeException() {
        String invalidLink = "fewfew://google.com";

        assertThrows(RuntimeException.class, () -> URLCreator.createURL(invalidLink));
    }
}
