package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregateCommits {

    public AggregateCommits() {}

    public Map<User, Map<Team, CommitAggregation>> execute(List<Commit> commits) {
        // {
        //   "userA": {
        //      "teamA": [commitsFromUserAInTeamA],
        //      "teamB": [commitsFromUserAInTeamB]
        //   },
        //   "userB": { "teamA": [commitsFromUserBInTeamA] },
        //   "userC": { "teamB": [commitsFromUserCInTeamB] } ...
        // }
        Map<User, Map<Team, List<Commit>>> groupedCommits = commits.stream().collect(
                Collectors.groupingBy(Commit::getUser,
                        Collectors.groupingBy(commit ->
                                commit.getPullRequest().getRepository().getTeam()
                        )));

        // {
        //   "userA": {
        //      "teamA": commitAggregationUserATeamA
        //      "teamB": commitAggregationUserATeamB
        //   },
        //   "userB": { "teamA": commitAggregationUserBTeamA },
        //   "userC": { "teamB": commitAggregationUserCTeamB } ...
        // }
        Map<User, Map<Team, CommitAggregation>> aggCommits = new HashMap<>();
        groupedCommits.keySet().forEach(user -> {
            Map<Team, CommitAggregation> aggCommitsByUser = new HashMap<>();
            groupedCommits.get(user).keySet().forEach(team -> {
                List<Commit> commitsByUserByTeam = groupedCommits.get(user).get(team);
                aggCommitsByUser.put(team, CommitAggregation.aggregate(commitsByUserByTeam));
            });
            aggCommits.put(user, aggCommitsByUser);
        });

        return aggCommits;
    }

}
