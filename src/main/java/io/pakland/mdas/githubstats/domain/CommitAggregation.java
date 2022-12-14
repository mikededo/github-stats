package io.pakland.mdas.githubstats.domain;

import java.util.List;

public class CommitAggregation {

    private int numCommits;

    public static CommitAggregation aggregate(List<Commit> commits) {
        CommitAggregation commitAggregation = new CommitAggregation();
        commitAggregation.numCommits = (int) commits.stream().distinct().count();
        return commitAggregation;
    }

    public int getNumCommits() {
        return numCommits;
    }

}
