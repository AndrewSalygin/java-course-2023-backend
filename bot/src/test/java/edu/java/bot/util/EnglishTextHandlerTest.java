package edu.java.bot.util;

import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnglishTextHandlerTest {

    private static TextHandler textHandler;

    @BeforeEach
    public void setUp() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("messagesTest.yml"));
        resourceBundleMessageSource.setCommonMessages(yamlPropertiesFactoryBean.getObject());
        textHandler = new EnglishTextHandler(resourceBundleMessageSource);
    }

    @DisplayName("Getting message from messages file")
    @Test
    void getMessageFromFileTest() {
        String message = textHandler.handle("message.common");
        assertEquals("Common message", message);
    }
}
