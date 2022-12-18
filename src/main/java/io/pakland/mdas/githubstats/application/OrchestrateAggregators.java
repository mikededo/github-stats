package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrchestrateAggregators {

    // TODO: this class needs a better design

    private final AggregateCommits aggregateCommits;
    private CommitAggregation commitAggregation;

    private final AggregateUserReviews aggregateUserReviews;
    private UserReviewAggregation userReviewAggregation;

    // private final AggregatePullRequests aggregatePullRequests;
    // private PullRequestAggregation pullRequestAggregation;

    public OrchestrateAggregators() {
        this.aggregateCommits = new AggregateCommits();
        this.aggregateUserReviews = new AggregateUserReviews();
        // this.aggregatePullRequests = new AggregatePullRequests();
    }

    public void execute(User user, List<PullRequest> pullRequests) {
        pullRequests.forEach(pull -> {
            List<Commit> commits = pull.getCommitsByUser(user);

            CommitAggregation commitAggregation = new AggregateCommits().execute(commits);
            //int linesAdded = commitAggregation.getLinesAdded();
            //int linesRemoved = commitAggregation.getLinesRemoved();
            // aggregate additions, deletions

            List<UserReview> userReviews = pull.getReviewsFromUser(user);

            long numReviewsInsideTeam = userReviews
                    .stream()
                    .filter(review -> review.isReviewFromTeam(pull.getRepository().getTeam()))
                    .count();
            long numReviewsOutsideTeam = userReviews.size() - numReviewsInsideTeam;
            // aggregate numReviewsInsideTeam, numReviewsOutsideTeam
        });
    }

    public CommitAggregation getCommitAggregation() {
        return commitAggregation;
    }

    public UserReviewAggregation getUserReviewAggregation() {
        return userReviewAggregation;
    }
}
