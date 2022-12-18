package io.pakland.mdas.githubstats.domain;

import java.util.List;

public class CommitAggregation {

    private int numCommits;

    private int linesAdded;

    private int linesRemoved;

    public static CommitAggregation aggregate(List<Commit> commits) {
        CommitAggregation commitAggregation = new CommitAggregation();
        commitAggregation.numCommits = (int) commits.stream().distinct().count();
        commitAggregation.linesAdded = commits.stream().mapToInt(Commit::getAdditions).sum();
        commitAggregation.linesRemoved = commits.stream().mapToInt(Commit::getDeletions).sum();
        return commitAggregation;
    }

    public int getNumCommits() {
        return numCommits;
    }

    public int getLinesAdded() { return linesAdded; }

    public int getLinesRemoved() { return linesRemoved; }

}
