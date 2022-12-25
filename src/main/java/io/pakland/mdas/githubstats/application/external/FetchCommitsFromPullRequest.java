package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import java.util.ArrayList;
import java.util.List;

public class FetchCommitsFromPullRequest {

    private final CommitExternalRepository commitExternalRepository;

    public FetchCommitsFromPullRequest(CommitExternalRepository commitExternalRepository) {
        this.commitExternalRepository = commitExternalRepository;
    }

    public List<Commit> execute(PullRequest pullRequest) throws HttpException {
        int page = 1, responseResults = 0;
        List<Commit> commitList = new ArrayList<>();

        do {
            Repository repository = pullRequest.getRepository();
            CommitExternalRepository.FetchCommitsFromPullRequestRequest request = CommitExternalRepository.FetchCommitsFromPullRequestRequest.builder()
                .repositoryOwner(repository.getOwnerLogin())
                .repositoryName(repository.getName())
                .pullRequestNumber(pullRequest.getNumber())
                .page(page)
                .perPage(100)
                .build();
            List<Commit> apiResults = this.commitExternalRepository.fetchCommitsFromPullRequest(
                request);

            commitList.addAll(apiResults);
            pullRequest.addCommits(commitList);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return commitList;
    }
}
