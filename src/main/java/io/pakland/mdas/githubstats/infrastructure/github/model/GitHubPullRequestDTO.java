package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.application.dto.PullRequestStateDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.domain.enums.PullRequestState;

import java.time.Instant;

public class GitHubPullRequestDTO implements PullRequestDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("state")
    private GitHubPullRequestStateDTO state;

    @JsonProperty("closed_at")
    private Instant closedAt;

    @JsonProperty("additions")
    private Integer additions;

    @JsonProperty("deletions")
    private Integer deletions;

    @JsonProperty("user")
    private GitHubUserDTO user;

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
        return this.state.getValue();
    }

    @Override
    public Instant getClosedAt() {
        return this.closedAt;
    }

    @Override
    public Integer getAdditions() {
        return this.additions;
    }

    @Override
    public Integer getDeletions() {
        return this.deletions;
    }

    @Override
    public UserDTO getUser() {
        return this.user;
    }
}
