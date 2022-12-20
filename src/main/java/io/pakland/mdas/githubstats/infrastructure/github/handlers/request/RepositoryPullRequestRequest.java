package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import org.springframework.data.util.Pair;

public class RepositoryPullRequestRequest implements Request {

    private final Repository repository;
    private final PullRequest pullRequest;

    public RepositoryPullRequestRequest(Repository repository, PullRequest pullRequest) {
        this.repository = repository;
        this.pullRequest = pullRequest;
    }

    @Override
    public Pair<Repository, PullRequest> getData() {
        return Pair.of(repository, pullRequest);
    }
}
