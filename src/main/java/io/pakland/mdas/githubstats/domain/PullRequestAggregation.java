package io.pakland.mdas.githubstats.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class PullRequestAggregation {

    private int commitCount;

    private int additions;

    private int deletions;

    private int pullRequestCount;

    private int mergePullRequests;

    private PullRequestAggregation() {
        this.commitCount = 0;
        this.additions = 0;
        this.deletions = 0;
        this.pullRequestCount = 0;
    }

    public static PullRequestAggregation aggregate(List<PullRequest> pullRequests) {
        PullRequestAggregation aggregation = new PullRequestAggregation();
        if (pullRequests == null) {
            return aggregation;
        }

        aggregation.pullRequestCount = pullRequests.size();
        pullRequests.forEach(pr -> {
            aggregation.commitCount += pr.getNumCommits();
            aggregation.deletions += pr.getDeletions();
            aggregation.additions += pr.getAdditions();
            if (pr.isMerged()) {
                aggregation.mergePullRequests++;
            }
        });

        return aggregation;
    }
}