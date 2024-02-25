package edu.java.bot.util;

import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import static edu.java.bot.Utils.messageSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnglishTextHandlerTest {

    private static TextHandler textHandler;

    @DisplayName("Getting message from messages file")
    @Test
    void getMessageFromFileTest() {
        textHandler = new EnglishTextHandler(messageSource());
        String message = textHandler.handle("message.common");
        assertEquals("Common message", message);
    }
}
