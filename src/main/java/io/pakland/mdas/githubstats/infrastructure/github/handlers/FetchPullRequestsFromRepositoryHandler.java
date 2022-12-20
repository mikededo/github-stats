package io.pakland.mdas.githubstats.infrastructure.github.handlers;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.FetchPullRequestsFromRepository;
import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.application.handlers.RequestHandler;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.RepositoryPullRequestRequest;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.RepositoryRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.PullRequestGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import java.util.ArrayList;
import java.util.List;

public class FetchPullRequestsFromRepositoryHandler implements RequestHandler {

    private final WebClientConfiguration webConfig;
    private final List<RequestHandler> next;

    public FetchPullRequestsFromRepositoryHandler(WebClientConfiguration webConfig) {
        this.webConfig = webConfig;
        this.next = new ArrayList<>();
    }

    @Override
    public void addNext(RequestHandler handler) {
        this.next.add(handler);
    }

    @Override
    public void handle(Request request) {
        if (!(request instanceof RepositoryRequest)) {
            throw new RuntimeException(
                "FetchPullRequestsFromRepositoryHandler::handle called with request different of type RepositoryRequest");
        }

        Repository repository = ((RepositoryRequest) request).getData();

        try {
            List<PullRequest> pullRequestList = new FetchPullRequestsFromRepository(
                new PullRequestGitHubRepository(this.webConfig)
            ).execute(repository.getOwnerLogin(), repository.getName());

            pullRequestList.forEach(pr -> this.next.forEach(
                handler -> handler.handle(new RepositoryPullRequestRequest(repository, pr))));
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

}
