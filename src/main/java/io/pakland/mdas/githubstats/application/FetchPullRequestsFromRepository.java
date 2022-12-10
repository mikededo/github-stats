package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;

import java.util.List;

public class FetchPullRequestsFromRepository {

    private PullRequestExternalRepository pullRequestExternalRepository;

    public FetchPullRequestsFromRepository(PullRequestExternalRepository pullRequestExternalRepository) {
        this.pullRequestExternalRepository = pullRequestExternalRepository;
    }

    public List<PullRequestDTO> execute(Integer repositoryOwnerId, Integer repositoryId) throws HttpException {
        return this.pullRequestExternalRepository.fetchPullRequestsFromRepository(repositoryOwnerId, repositoryId);
    }
}
