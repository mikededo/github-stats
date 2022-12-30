package io.pakland.mdas.githubstats.domain;

import java.util.List;

public class PullRequestAggregation {
    private int linesAdded;

    private int createdCount;

    public int getCreatedCount() {
        return createdCount;
    }

    public PullRequestAggregation aggregate(List<PullRequest> pullRequests) {
        this.createdCount = pullRequests.size();
        return this;
    }
}
