package edu.java.bot.util;

import java.util.Locale;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class EnglishTextHandler implements TextHandler {

    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public EnglishTextHandler(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    @Override
    public String handle(String option) {
        return resourceBundleMessageSource.getMessage(option, null, Locale.of("eng"));
    }

}
