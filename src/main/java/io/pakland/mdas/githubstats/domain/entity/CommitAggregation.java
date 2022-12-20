package io.pakland.mdas.githubstats.domain.entity;

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

    public String toCSV() {
        String sep = ",";
        String lineSep = "\n";

        List<String> metrics = List.of("numCommits", "linesAdded", "linesRemoved");
        List<Object> data = List.of(numCommits, linesAdded, linesRemoved);

        String header = String.join(sep, metrics);
        String body = String.join(sep, data.stream().map(Object::toString).toList());

        return header + lineSep + body;
    }
}
