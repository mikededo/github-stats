package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AggregatePullRequests {

    PullRequestAggregation pullRequestAggregation = new PullRequestAggregation();
    public AggregatePullRequests() {}

    public Map<User, Map<Team, PullRequestAggregation>> execute(List<PullRequest> pullRequests) {
        // {
        //   "userA": {
        //      "teamA": [prsFromUserAInTeamA],
        //      "teamB": [prsFromUserAInTeamB]
        //   },
        //   "userB": { "teamA": [prsFromUserBInTeamA] },
        //   "userC": { "teamB": [prsFromUserCInTeamB] } ...
        // }
        Map<User, Map<Team, List<PullRequest>>> groupedPullRequests = pullRequests.stream().collect(
            Collectors.groupingBy(PullRequest::getUser,
                Collectors.groupingBy(pr ->
                    pr.getRepository().getTeam()
                )));

        // {
        //   "userA": {
        //      "teamA": prAggregationUserATeamA
        //      "teamB": prAggregationUserATeamB
        //   },
        //   "userB": { "teamA": prAggregationUserBTeamA },
        //   "userC": { "teamB": prAggregationUserCTeamB } ...
        // }
        Map<User, Map<Team, PullRequestAggregation>> aggPullRequests = new HashMap<>();
        groupedPullRequests.keySet().forEach(user -> {
            Map<Team, PullRequestAggregation> aggPullRequestsByUser = new HashMap<>();
            groupedPullRequests.get(user).keySet().forEach(team -> {
                List<PullRequest> pullRequestsByUserByTeam = groupedPullRequests.get(user).get(team);
                aggPullRequestsByUser.put(team, pullRequestAggregation.aggregate(pullRequestsByUserByTeam));
            });
            aggPullRequests.put(user, aggPullRequestsByUser);
        });

        return aggPullRequests;
    }

}
