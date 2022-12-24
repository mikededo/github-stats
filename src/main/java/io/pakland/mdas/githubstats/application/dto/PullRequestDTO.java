package io.pakland.mdas.githubstats.application.dto;

import io.pakland.mdas.githubstats.domain.enums.PullRequestState;

public interface PullRequestDTO {
    Integer getId();
    Integer getNumber();
    PullRequestState getState();
}
