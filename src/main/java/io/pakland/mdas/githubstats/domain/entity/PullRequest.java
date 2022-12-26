package io.pakland.mdas.githubstats.domain.entity;

import io.pakland.mdas.githubstats.domain.enums.PullRequestState;
import java.time.Instant;
import java.util.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PullRequest {

    private Integer id;

    private Integer number;

    private PullRequestState state;

    private Instant closedAt;

    private Integer additions;

    private Integer deletions;

    @ToString.Exclude
    private Repository repository;

    private User user;

    private Set<Commit> commits = new HashSet<>();

    private List<Review> reviews = new ArrayList<>();

    public void addCommits(Collection<Commit> commits) {
        if (this.commits == null) {
            this.commits = new HashSet<>();
        }

        commits.forEach(commit -> {
            commit.setPullRequest(this);
            this.commits.add(commit);
        });
    }
}
