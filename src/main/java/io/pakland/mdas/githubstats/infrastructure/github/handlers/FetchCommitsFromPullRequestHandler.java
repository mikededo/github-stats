package io.pakland.mdas.githubstats.infrastructure.github.handlers;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.FetchCommitsFromPullRequest;
import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.application.handlers.RequestHandler;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.infrastructure.github.controller.GitHubUserOptionController;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.RepositoryPullRequestRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.CommitGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

public class FetchCommitsFromPullRequestHandler implements RequestHandler {

    private final WebClientConfiguration webConfig;

    public FetchCommitsFromPullRequestHandler(WebClientConfiguration webConfig) {
        this.webConfig = webConfig;
    }

    @Override
    public void addNext(RequestHandler handler) {
        // As of now, we do not need to run any handler
    }

    @Override
    public void handle(Request request) {
        if (!(request instanceof RepositoryPullRequestRequest)) {
            throw new RuntimeException(
                "FetchCommitsFromPullRequest::handle called with request different of type RepositoryPullRequestRequest");
        }

        Pair<Repository, PullRequest> pair = ((RepositoryPullRequestRequest) request).getData();
        Repository repository = pair.getFirst();
        PullRequest pullRequest = pair.getSecond();

        try {
            List<Commit> commitList = new FetchCommitsFromPullRequest(
                new CommitGitHubRepository(this.webConfig)
            ).execute(repository.getOwnerLogin(), repository.getName(), pullRequest.getNumber());
            LoggerFactory.getLogger(GitHubUserOptionController.class).info(commitList.toString());

        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

}
