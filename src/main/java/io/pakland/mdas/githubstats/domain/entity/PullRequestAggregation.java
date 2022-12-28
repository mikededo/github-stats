package io.pakland.mdas.githubstats.domain.entity;

import java.util.List;

public class PullRequestAggregation {
    private int linesAdded;

    private int linesRemoved;

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public PullRequestAggregation aggregate(List<PullRequest> pullRequests) {
        // TODO
        return new PullRequestAggregation();
    }

}
