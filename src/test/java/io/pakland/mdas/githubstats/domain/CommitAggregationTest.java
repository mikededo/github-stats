package io.pakland.mdas.githubstats.domain;

import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.CommitAggregation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CommitAggregationTest {

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

    private boolean isValidCSV(String candidate) {
        List<String> lines = List.of(candidate.split("\n"));
        if (lines.size() < 1) return false;

        String header = lines.get(0);
        if (header.isBlank()) return false;
        else if (lines.size() == 1) return true;

        List<String> fields = List.of(header.split(","));
        long invalidLines = lines.stream().filter(line -> line.split(",").length != fields.size()).count();
        return invalidLines == 0;
    }
}
