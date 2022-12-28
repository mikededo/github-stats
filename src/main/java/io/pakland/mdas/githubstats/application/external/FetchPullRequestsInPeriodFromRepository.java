package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchPullRequestsInPeriodFromRepository {

    private final PullRequestExternalRepository pullRequestExternalRepository;

    public FetchPullRequestsInPeriodFromRepository(
        PullRequestExternalRepository pullRequestExternalRepository) {
        this.pullRequestExternalRepository = pullRequestExternalRepository;
    }

    public List<PullRequest> execute(Repository repository, Date from, Date to)
        throws HttpException {
        int page = 1;
        List<PullRequest> pullRequestList = new ArrayList<>();
        int responseResults;
        do {
            List<PullRequest> apiResults = this.pullRequestExternalRepository
                .fetchPullRequestsFromRepositoryByPeriodAndPage(repository, from, to, page);

            pullRequestList.addAll(apiResults);
            repository.addPullRequests(pullRequestList);
            responseResults = apiResults.size();
            page++;
        } while (responseResults == 25);

        return pullRequestList;
    }
}
