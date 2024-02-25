package edu.java.supplier.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GithubRepoInfo(@JsonProperty("full_name") String title,
                             @JsonProperty("updated_at") OffsetDateTime lastUpdate) {
    public static final GithubRepoInfo EMPTY = new GithubRepoInfo(null, null);
}
