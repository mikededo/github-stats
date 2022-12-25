package io.pakland.mdas.githubstats.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class PullRequestAggregationTest extends CSVTest {

    @Test
    public void emptyAggregationToCSV_shouldGiveValidCSV() {
        PullRequestAggregation pullRequestAggregation = new PullRequestAggregation();
        assertTrue(isValidCSV(pullRequestAggregation.toCSV()));
    }

    @Test
    public void nonEmptyAggregationToCSV_shouldGiveValidCSV() {
        List<PullRequest> pullRequests = List.of(mock(PullRequest.class), mock(PullRequest.class), mock(PullRequest.class));
        PullRequestAggregation pullRequestAggregation = new PullRequestAggregation().aggregate(pullRequests);
        assertTrue(isValidCSV(pullRequestAggregation.toCSV()));
    }

}
