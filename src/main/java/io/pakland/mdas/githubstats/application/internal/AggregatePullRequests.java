package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.PullRequestAggregation;
import io.pakland.mdas.githubstats.domain.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AggregatePullRequests {

    public Map<User, PullRequestAggregation> execute(List<PullRequest> pullRequests) {
        Map<User, PullRequestAggregation> groupedAggregations = new HashMap<>();
        Map<User, List<PullRequest>> groupedPullRequests = pullRequests.parallelStream().collect(
            Collectors.groupingBy(PullRequest::getUser));
        groupedPullRequests.forEach((key, value) -> groupedAggregations.put(key,
            new PullRequestAggregation().aggregate(value)));
        return groupedAggregations;
    }

}
