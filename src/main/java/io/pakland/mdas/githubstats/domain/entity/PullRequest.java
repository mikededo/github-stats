package io.pakland.mdas.githubstats.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "pull_request")
public class PullRequest {

    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "state")
    private PullRequestState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Repository repository;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(
        mappedBy = "pullRequest",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Commit> commits = new ArrayList<>();

    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy = "pullRequest",
        orphanRemoval = true
    )
    private List<UserReview> userReviews = new ArrayList<>();

    public List<Commit> getCommitsByUser(User user) {
        return commits
            .stream()
            .filter(commit -> commit.getUser().equals(user))
            .collect(Collectors.toList());
    }

    public boolean isClosed() {
        return state.equals(PullRequestState.CLOSED);
    }

    public boolean isCreatedByUser(User user) {
        return this.user.equals(user);
    }

    public List<UserReview> getReviewsFromUser(User user) {
        return userReviews
            .stream()
            .filter(x -> x.getUser().equals(user))
            .collect(Collectors.toList());
    }

}
