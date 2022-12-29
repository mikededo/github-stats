package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MergeAggregatesIntoMetrics {

    public List<Metric> execute(
            Map<Team, Map<User, CommitAggregation>> commitAggregations,
            Map<Team, Map<User, PullRequestAggregation>> pullRequestAggregations,
            Map<Team, Map<User, ReviewAggregation>> reviewAggregations
    ) {
        List<Metric> metrics = new ArrayList<>();

        for (Team team : commitAggregations.keySet()) {
            for (User user : commitAggregations.get(team).keySet()) {
                Metric metric = new Metric();
                metric.setTeamSlug(team.getSlug());
                metric.setUserName(user.getLogin());

                CommitAggregation commitAggregation =
                        commitAggregations.get(team).get(user);
                PullRequestAggregation pullRequestAggregation =
                        pullRequestAggregations.get(team).get(user);
                ReviewAggregation reviewAggregation =
                        reviewAggregations.get(team).get(user);

                metric.setCommitsCount(commitAggregation.getNumCommits());
                metric.setCommentsAvgLength(Math.round(reviewAggregation.getTotalCommentLengthAvg()));
                metric.setLinesAdded(pullRequestAggregation.getLinesAdded());
                metric.setLinesRemoved(pullRequestAggregation.getLinesRemoved());

                metrics.add(metric);
            }
        }

        return metrics;
    }

}
