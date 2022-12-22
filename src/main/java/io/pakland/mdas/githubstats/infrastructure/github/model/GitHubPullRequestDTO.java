package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.domain.entity.PullRequestState;

public class GitHubPullRequestDTO implements PullRequestDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("state")
    private PullRequestState state;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Integer getNumber() {
        return this.number;
    }

    @Override
    public PullRequestState getState() {
        return this.state;
    }

}
