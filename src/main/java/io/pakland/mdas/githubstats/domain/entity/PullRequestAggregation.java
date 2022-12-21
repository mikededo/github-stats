package io.pakland.mdas.githubstats.domain.entity;

import java.util.List;

public class PullRequestAggregation implements CSVExportable {

    public PullRequestAggregation aggregate(List<PullRequest> pullRequests) {
        // TODO
        return new PullRequestAggregation();
    }

    @Override
    public String toCSV() {
        // TODO
        return "";
    }

}
