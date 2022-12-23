package io.pakland.mdas.githubstats.domain.entity;

import java.util.List;

public class CommitAggregation implements CSVExportable {

    private int numCommits;


    public static CommitAggregation aggregate(List<Commit> commits) {
        CommitAggregation commitAggregation = new CommitAggregation();
        commitAggregation.numCommits = (int) commits.stream().distinct().count();
        return commitAggregation;
    }

    public int getNumCommits() {
        return numCommits;
    }

    @Override
    public String toCSV() {
        String sep = ",";
        String lineSep = "\n";

        List<String> metrics = List.of("numCommits");
        List<Object> data = List.of(numCommits);

        String header = String.join(sep, metrics);
        String body = String.join(sep, data.stream().map(Object::toString).toList());

        return header + lineSep + body;
    }
}
