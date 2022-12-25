package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.PullRequestStateDTO;
import io.pakland.mdas.githubstats.domain.enums.PullRequestState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GitHubPullRequestStateDTO implements PullRequestStateDTO {

    @JsonProperty("state")
    private String value;

    @Override
    public PullRequestState getValue() {
        return switch (this.value) {
            case "closed" -> PullRequestState.CLOSED;
            case "open" -> PullRequestState.OPEN;
            default -> PullRequestState.ALL;
        };
    }
}
