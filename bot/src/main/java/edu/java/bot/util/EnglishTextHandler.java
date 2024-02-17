package edu.java.bot.util;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EnglishTextHandler implements TextHandler {
    private final MessageSource messageSource;

    @Autowired
    public EnglishTextHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String handle(String option) {
        return messageSource.getMessage(option, null, Locale.of("eng"));
    }

}
