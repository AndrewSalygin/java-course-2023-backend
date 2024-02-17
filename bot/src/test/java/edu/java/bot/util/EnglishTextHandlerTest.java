package edu.java.bot.util;

import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnglishTextHandlerTest {

    private static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultLocale(Locale.ENGLISH);
        textHandler = new EnglishTextHandler(messageSource);
    }

    @DisplayName("Getting message from messages file")
    @Test
    void getMessageFromFileTest() {
        String message = textHandler.handle("message.common");
        assertEquals("Common message", message);
    }
}
