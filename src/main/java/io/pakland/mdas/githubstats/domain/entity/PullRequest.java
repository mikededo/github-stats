package io.pakland.mdas.githubstats.domain.entity;

import io.pakland.mdas.githubstats.domain.enums.PullRequestState;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    private Instant createdAt;

    @ToString.Exclude
    private Repository repository;

    private User user;

    private List<Review> reviews = new ArrayList<>();
}
