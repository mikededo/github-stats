package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;

import java.util.List;

public class FetchPullRequestsFromRepository {

    private PullRequestExternalRepository pullRequestExternalRepository;

    public FetchPullRequestsFromRepository(PullRequestExternalRepository pullRequestExternalRepository) {
        this.pullRequestExternalRepository = pullRequestExternalRepository;
    }

    public List<PullRequest> execute(String repositoryOwnerLogin, String repositoryName) throws HttpException {
        return this.pullRequestExternalRepository.fetchPullRequestsFromRepository(repositoryOwnerLogin, repositoryName);
    }
}
