package edu.java.bot.util;

import java.util.Collections;
import java.util.Map;

public interface TextHandler {

    String handle(String option, Map<String, String> keyWords);

    String handle(String option, Map<String, String> keyWords, String defaultValue);

    default String handle(String option) {
        return handle(option, Collections.emptyMap());
    }
}
