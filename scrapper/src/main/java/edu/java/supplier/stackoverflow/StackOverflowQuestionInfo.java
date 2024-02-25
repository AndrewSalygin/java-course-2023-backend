package edu.java.supplier.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverflowQuestionInfo(@JsonProperty("title") String title,
                                        @JsonProperty("last_activity_date") OffsetDateTime lastUpdate) {
}
