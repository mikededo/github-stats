package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.CommitAggregation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AggregateCommitsTest {

    @Test
    public void aggregatingCommits_shouldGiveValidNumCommits() {
        List<Commit> commits = new ArrayList<>();

        int numCommits = new Random().nextInt(10);

        for (int i = 0; i < numCommits; i++) {
            commits.add(mock(Commit.class));
        }

        CommitAggregation commitAggregation = CommitAggregation.aggregate(commits);
        assertEquals(commitAggregation.getNumCommits(), numCommits);
    }

    @Test
    public void aggregatingCommits_shouldGiveValidLinesAdded() {
        //TODO: Additions should come form PullRequests.
//        List<Commit> commits = new ArrayList<>();
//
//        int totalLines = 0;
//
//        for (int i = 0; i < 10; i++) {
//            int numLines = new Random().nextInt(1000);
//            Commit commit = mock(Commit.class);
//            when(commit.getAdditions()).thenReturn(numLines);
//            commits.add(commit);
//            totalLines += numLines;
//        }
//
//        CommitAggregation commitAggregation = CommitAggregation.aggregate(commits);
//        assertEquals(commitAggregation.getLinesAdded(), totalLines);
    }

    @Test
    public void aggregatingCommits_shouldGiveValidLinesRemoved() {
        //TODO: Additions should come form PullRequests.
//        List<Commit> commits = new ArrayList<>();
//
//        int totalLines = 0;
//
//        for (int i = 0; i < 10; i++) {
//            int numLines = new Random().nextInt(1000);
//            Commit commit = mock(Commit.class);
//            when(commit.getDeletions()).thenReturn(numLines);
//            commits.add(commit);
//            totalLines += numLines;
//        }
//
//        CommitAggregation commitAggregation = CommitAggregation.aggregate(commits);
//        assertEquals(commitAggregation.getLinesRemoved(), totalLines);
    }

}
