package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.PullRequest;

import java.util.List;

public interface PullRequestExternalRepository {
    public List<PullRequest> fetchPullRequestsFromRepository(String repositoryOwnerId, String repositoryId) throws HttpException;
}
