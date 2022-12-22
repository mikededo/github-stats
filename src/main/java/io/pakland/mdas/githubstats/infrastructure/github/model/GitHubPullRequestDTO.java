package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.domain.entity.PullRequestState;

public class GitHubPullRequestDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("state")
    private PullRequestState state;

}
