package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AggregatePullRequests {

    public AggregatePullRequests() {
    }

    private static Map<Team, Map<User, List<PullRequest>>> groupPullRequestsByTeam(
        List<PullRequest> pullRequests) {
        return pullRequests.stream().collect(
            Collectors.groupingBy(pr -> pr.getRepository().getTeam(),
                Collectors.groupingBy(PullRequest::getUser)));
    }

    public Map<Team, Map<User, PullRequestAggregation>> execute(List<PullRequest> pullRequests) {
        Map<Team, Map<User, List<PullRequest>>> groupedPullRequests = groupPullRequestsByTeam(
            pullRequests);

        Map<Team, Map<User, PullRequestAggregation>> aggPullRequests = new HashMap<>();
        groupedPullRequests.forEach((team, userPrMap) -> aggPullRequests.put(team,
            groupPullRequestAggregationsByUser(userPrMap))
        );

        return aggPullRequests;
    }

    private Map<User, PullRequestAggregation> groupPullRequestAggregationsByUser(
        Map<User, List<PullRequest>> userPrMap) {
        Map<User, PullRequestAggregation> aggPullRequestsByUser = new HashMap<>();
        userPrMap.forEach((user, prList) -> aggPullRequestsByUser.put(user,
            PullRequestAggregation.aggregate(prList)));
        return aggPullRequestsByUser;
    }

}
