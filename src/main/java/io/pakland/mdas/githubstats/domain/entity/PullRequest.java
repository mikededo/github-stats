package io.pakland.mdas.githubstats.domain.entity;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PullRequest {

    private Integer id;

    private Integer number;

    private PullRequestState state;

    @ToString.Exclude
    private Repository repository;

    private User user;

    private Set<Commit> commits = new HashSet<>();

    private List<UserReview> userReviews = new ArrayList<>();

    public void addCommits(Collection<Commit> commits) {
        if (this.commits == null) {
            this.commits = new HashSet<>();
        }

        commits.forEach(commit -> {
            commit.setPullRequest(this);
            this.commits.add(commit);
        });
    }
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
