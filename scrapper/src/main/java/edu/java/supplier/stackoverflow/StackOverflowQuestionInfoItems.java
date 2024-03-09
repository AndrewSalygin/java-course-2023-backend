package edu.java.supplier.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowQuestionInfoItems(@JsonProperty("items") List<StackOverflowQuestionInfo> items) {
    public static final StackOverflowQuestionInfoItems EMPTY = new StackOverflowQuestionInfoItems(null);
}
