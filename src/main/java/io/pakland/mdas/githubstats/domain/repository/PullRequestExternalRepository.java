package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface PullRequestExternalRepository {
    public List<PullRequestDTO> fetchPullRequestsFromRepository(Integer repositoryOwnerId, Integer repositoryId) throws HttpException;
}
