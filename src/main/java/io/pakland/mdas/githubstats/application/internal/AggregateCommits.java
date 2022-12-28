package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AggregateCommits {

    public AggregateCommits() {
    }

    public Map<Team, Map<User, CommitAggregation>> execute(List<Commit> commits) {
        // {
        //   "userA": {
        //      "teamA": [commitsFromUserAInTeamA],
        //      "teamB": [commitsFromUserAInTeamB]
        //   },
        //   "userB": { "teamA": [commitsFromUserBInTeamA] },
        //   "userC": { "teamB": [commitsFromUserCInTeamB] } ...
        // }
        Map<Team, Map<User, List<Commit>>> groupedCommits = commits.stream().collect(
            Collectors.groupingBy(commit -> commit.getPullRequest().getRepository().getTeam(),
                Collectors.groupingBy(Commit::getUser)));

        // {
        //   "userA": {
        //      "teamA": commitAggregationUserATeamA
        //      "teamB": commitAggregationUserATeamB
        //   },
        //   "userB": { "teamA": commitAggregationUserBTeamA },
        //   "userC": { "teamB": commitAggregationUserCTeamB } ...
        // }
        Map<Team, Map<User, CommitAggregation>> aggCommits = new HashMap<>();
        groupedCommits.keySet().forEach(team -> {
            Map<User, CommitAggregation> aggCommitsByUser = new HashMap<>();
            groupedCommits.get(team).keySet().forEach(user -> {
                List<Commit> commitsByUserByTeam = groupedCommits.get(team).get(user);
                aggCommitsByUser.put(user, CommitAggregation.aggregate(commitsByUserByTeam));
            });
            aggCommits.put(team, aggCommitsByUser);
        });

        return aggCommits;
    }

}
