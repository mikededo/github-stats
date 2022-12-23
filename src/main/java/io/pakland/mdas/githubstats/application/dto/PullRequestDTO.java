package io.pakland.mdas.githubstats.application.dto;

import io.pakland.mdas.githubstats.domain.enums.PullRequestState;

import java.time.Instant;

public interface PullRequestDTO {
    Integer getId();

    Integer getNumber();

    PullRequestState getState();

    Instant getClosedAt();

    Integer getAdditions();

    Integer getDeletions();
}
