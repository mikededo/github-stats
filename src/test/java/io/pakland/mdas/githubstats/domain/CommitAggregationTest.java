package io.pakland.mdas.githubstats.domain;

import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.CommitAggregation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CommitAggregationTest extends CSVTest {

    @Test
    public void emptyAggregationToCSV_shouldGiveValidCSV() {
        CommitAggregation commitAggregation = new CommitAggregation();
        assertTrue(isValidCSV(commitAggregation.toCSV()));
    }

    @Test
    public void nonEmptyAggregationToCSV_shouldGiveValidCSV() {
        List<Commit> commits = List.of(mock(Commit.class), mock(Commit.class), mock(Commit.class));
        CommitAggregation commitAggregation = CommitAggregation.aggregate(commits);
        assertTrue(isValidCSV(commitAggregation.toCSV()));
    }

}
