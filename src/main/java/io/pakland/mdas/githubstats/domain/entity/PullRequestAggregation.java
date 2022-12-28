package io.pakland.mdas.githubstats.domain.entity;

import java.util.List;

public class PullRequestAggregation implements CSVExportable {

    private int createdCount;

    public int getCreatedCount() {
        return createdCount;
    }

    public PullRequestAggregation aggregate(List<PullRequest> pullRequests) {
        this.createdCount = pullRequests.size();
        return this;
    }

    @Override
    public String toCSV() {
        String sep = ",";
        String lineSep = "\n";

        List<String> metrics = List.of("prCount");
        List<Object> data = List.of(getCreatedCount());

        String header = String.join(sep, metrics);
        String body = String.join(sep, data.stream().map(Object::toString).toList());

        return header + lineSep + body;
    }

}
