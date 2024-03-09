package edu.java.supplier.stackoverflow;

import edu.java.configuration.supplier.StackOverflowConfig;
import edu.java.supplier.api.LinkInfo;
import edu.java.supplier.api.WebClientInfoSupplier;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class StackOverflowInfoSupplier extends WebClientInfoSupplier {

    private static final String TYPE_SUPPLIER = "stackoverflow";
    private final Pattern questionsPattern;

    public StackOverflowInfoSupplier(StackOverflowConfig config) {
        super(config.url());
        questionsPattern = Pattern.compile(config.patterns().questions());
    }

    @Override
    public LinkInfo fetchInfo(URL url) {
        Pattern pattern = supportedPattern(url);
        if (pattern == null) {
            return null;
        }

        StackOverflowQuestionInfoItems info = null;
        Matcher matcher = pattern.matcher(url.toString());
        if (matcher.find()) {
            String questionId = matcher.group(1);

            if (pattern == questionsPattern) {
                info = executeRequestGet(
                    "/questions/" + questionId + "?site=stackoverflow",
                    StackOverflowQuestionInfoItems.class,
                    StackOverflowQuestionInfoItems.EMPTY
                );
            }
        }

        if (info == null) {
            return null;
        }

        return new LinkInfo(url, info.items().getFirst().title(), info.items().getFirst().lastUpdate());
    }

    @Override
    public Pattern supportedPattern(URL url) {
        if (questionsPattern.matcher(url.toString()).matches()) {
            return questionsPattern;
        }
        return null;
    }
}
