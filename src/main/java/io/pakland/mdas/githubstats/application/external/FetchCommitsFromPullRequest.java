package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
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
            List<Commit> apiResults = this.commitExternalRepository
                .fetchCommitsFromPullRequestByPage(pullRequest, page);

            commitList.addAll(apiResults);
            pullRequest.addCommits(commitList);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return commitList;
    }
}
