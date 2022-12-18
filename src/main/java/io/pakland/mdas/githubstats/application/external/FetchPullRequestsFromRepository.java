package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.PullRequestState;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository.FetchPullRequestFromRepositoryRequest;

import java.util.ArrayList;
import java.util.List;

public class FetchPullRequestsFromRepository {

    private final PullRequestExternalRepository pullRequestExternalRepository;

    public FetchPullRequestsFromRepository(
            PullRequestExternalRepository pullRequestExternalRepository) {
        this.pullRequestExternalRepository = pullRequestExternalRepository;
    }

    public List<PullRequest> execute(String repositoryOwnerLogin, String repositoryName)
            throws HttpException {
        int page = 1;
        List<PullRequest> pullRequestList = new ArrayList<>();
        int responseResults;
        do {
            FetchPullRequestFromRepositoryRequest request = FetchPullRequestFromRepositoryRequest.builder()
                    .repositoryOwner(repositoryOwnerLogin)
                    .repository(repositoryName)
                    .page(page)
                    .perPage(100)
                    .state(PullRequestState.ALL)
                    .build();
            List<PullRequest> apiResults = this.pullRequestExternalRepository.fetchPullRequestsFromRepository(
                    request);

            pullRequestList.addAll(apiResults);
            responseResults = apiResults.size();
            page++;
        } while (responseResults == 25);

        return pullRequestList;
    }
}
