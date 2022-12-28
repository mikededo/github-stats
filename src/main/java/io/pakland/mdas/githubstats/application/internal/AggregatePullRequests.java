package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AggregatePullRequests {

    PullRequestAggregation pullRequestAggregation = new PullRequestAggregation();

    public AggregatePullRequests() {
    }

    public Map<Team, Map<User, PullRequestAggregation>> execute(List<PullRequest> pullRequests) {
        // {
        //   "userA": {
        //      "teamA": [prsFromUserAInTeamA],
        //      "teamB": [prsFromUserAInTeamB]
        //   },
        //   "userB": { "teamA": [prsFromUserBInTeamA] },
        //   "userC": { "teamB": [prsFromUserCInTeamB] } ...
        // }
        Map<Team, Map<User, List<PullRequest>>> groupedPullRequests = pullRequests.stream().collect(
            Collectors.groupingBy(pr -> pr.getRepository().getTeam(),
                Collectors.groupingBy(PullRequest::getUser)));

        // {
        //   "userA": {
        //      "teamA": prAggregationUserATeamA
        //      "teamB": prAggregationUserATeamB
        //   },
        //   "userB": { "teamA": prAggregationUserBTeamA },
        //   "userC": { "teamB": prAggregationUserCTeamB } ...
        // }
        Map<Team, Map<User, PullRequestAggregation>> aggPullRequests = new HashMap<>();
        groupedPullRequests.forEach((team, userPrMap) -> {
            Map<User, PullRequestAggregation> aggPullRequestsByUser = new HashMap<>();
            userPrMap.forEach((user, prList) -> aggPullRequestsByUser.put(user,
                pullRequestAggregation.aggregate(prList)));
            aggPullRequests.put(team, aggPullRequestsByUser);
        });

        return aggPullRequests;
    }

}
