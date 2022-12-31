package io.pakland.mdas.githubstats.domain;

import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PullRequest implements Authored {

    private Integer id;

    private Integer number;

    private PullRequestState state;

    private boolean merged;

    private Instant closedAt;

    private Instant createdAt;

    private Integer numCommits;

    private Integer additions;

    private Integer deletions;

    @ToString.Exclude
    private Repository repository;

    private User user;

    public boolean isMerged() {
        return this.merged;
    }

    public boolean userBelongsToTeam(User user) {
        return this.repository.getTeam().hasUser(user);
    }

    @Override
    public boolean isAuthorNamed(String name) {
        return this.user.isNamed(name);
    }

    @Override
    public boolean isAuthorFromEntityTeam() {
        return this.repository.getTeam().hasUser(user);
    }
}
