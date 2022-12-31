package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MergeAggregatesIntoMetrics {

    public List<Metric> execute(
        Map<Team, Map<User, PullRequestAggregation>> pullRequestAggregations,
        Map<Team, Map<User, CommentAggregation>> commentAggregations,
        Map<Team, Map<User, ReviewAggregation>> reviewAggregations,
        DateRange range
    ) {
        List<Metric> metrics = new ArrayList<>();

        for (Team team : pullRequestAggregations.keySet()) {
            for (User user : pullRequestAggregations.get(team).keySet()) {
                Metric metric = new Metric();
                metric.setOrganization(team.getOrganization().getLogin());
                metric.setTeamSlug(team.getSlug());
                metric.setUserName(user.getLogin());

                CommentAggregation commentAggregation =
                    commentAggregations.get(team).get(user);
                PullRequestAggregation pullRequestAggregation =
                    pullRequestAggregations.get(team).get(user);
                ReviewAggregation reviewAggregation =
                    reviewAggregations.get(team).get(user);

                metric.setCommentsAvgLength(commentAggregation.averageBodyLength());

                metric.setExternalReviews(reviewAggregation.getExternalReviewCount());
                metric.setInternalReviews(reviewAggregation.getInternalReviewCount());

                metric.setLinesAdded(pullRequestAggregation.getAdditions());
                metric.setLinesRemoved(pullRequestAggregation.getDeletions());
                metric.setTotalPulls(pullRequestAggregation.getPullRequestCount());
                metric.setMergedPulls(pullRequestAggregation.getMergePullRequests());
                metric.setCommitsCount(pullRequestAggregation.getCommitCount());

                metric.setTo(range.getTo().atZone(ZoneId.systemDefault()).toLocalDate());
                metric.setFrom(range.getFrom().atZone(ZoneId.systemDefault()).toLocalDate());

                metrics.add(metric);
            }
        }

        return metrics;
    }

}
