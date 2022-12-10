package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.CommitAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregateCommits {

    public AggregateCommits() {}

    public CommitAggregation execute(List<Commit> commits) {
        return CommitAggregation.aggregate(commits);
    }
}
