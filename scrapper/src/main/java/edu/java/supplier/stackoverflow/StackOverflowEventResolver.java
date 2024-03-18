package edu.java.supplier.stackoverflow;

import edu.java.supplier.api.EventResolver;
import edu.java.supplier.api.LinkUpdateEvent;
import edu.java.supplier.stackoverflow.data.StackOverflowItem;
import java.util.Map;

public class StackOverflowEventResolver extends EventResolver<StackOverflowItem> {
    public StackOverflowEventResolver() {
        registerCollector(
            "AnswerEvent",
            item -> new LinkUpdateEvent(
                "stackoverflow.answers_event",
                item.lastUpdate(),
                Map.of("count", String.valueOf(item.answerCount()))
            )
        );
        registerCollector(
            "ScoreEvent",
            item -> new LinkUpdateEvent(
                "stackoverflow.score_event",
                item.lastUpdate(),
                Map.of("score", String.valueOf(item.score()))
            )
        );
    }
}
