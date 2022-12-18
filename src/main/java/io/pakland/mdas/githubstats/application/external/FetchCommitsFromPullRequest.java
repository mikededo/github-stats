package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;

import java.util.ArrayList;
import java.util.List;

public class FetchCommitsFromPullRequest {
    private final CommitExternalRepository commitExternalRepository;

    public FetchCommitsFromPullRequest(CommitExternalRepository commitExternalRepository) {
        this.commitExternalRepository = commitExternalRepository;
    }

    public List<Commit> execute(String repositoryOwner, String repositoryName, Integer pullRequestNumber) throws HttpException {
        int page = 1;
        List<Commit> commitList = new ArrayList<>();
        int responseResults;
        do {
            CommitExternalRepository.FetchCommitsFromPullRequestRequest request = CommitExternalRepository.FetchCommitsFromPullRequestRequest.builder()
                    .repositoryOwner(repositoryOwner)
                    .repositoryName(repositoryName)
                    .pullRequestNumber(pullRequestNumber)
                    .page(page)
                    .perPage(100)
                    .build();
            List<Commit> apiResults = this.commitExternalRepository.fetchCommitsFromPullRequest(
                    request);

            commitList.addAll(apiResults);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return commitList;
    }
}
