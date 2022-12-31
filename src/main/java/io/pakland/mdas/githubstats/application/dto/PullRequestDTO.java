package io.pakland.mdas.githubstats.application.dto;

import io.pakland.mdas.githubstats.domain.PullRequestState;

import java.time.Instant;

public interface PullRequestDTO {
    Integer getId();

    Integer getNumber();

    PullRequestState getState();

    boolean getMerged();

    Instant getClosedAt();

    Instant getCreatedAt();

    Integer getNumCommits();

    Integer getAdditions();

    Integer getDeletions();

    UserDTO getUser();
}
