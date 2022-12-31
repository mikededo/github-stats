package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.application.*;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.*;
import io.pakland.mdas.githubstats.domain.repository.*;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class GitHubController {

    private GitHubOptionRequest userOptionRequest;
    private OrganizationExternalRepository organizationRepository;
    private TeamExternalRepository teamRepository;
    private RepositoryExternalRepository repositoryRepository;
    private UserExternalRepository userRepository;
    private PullRequestExternalRepository pullRequestRepository;
    private ReviewExternalRepository reviewRepository;
    private CommentExternalRepository commentRepository;

    public GitHubController(GitHubOptionRequest userOptionRequest) {
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());

        this.userOptionRequest = userOptionRequest;
        this.organizationRepository = new OrganizationGitHubRepository(webClientConfiguration);
        this.teamRepository = new TeamGitHubRepository(webClientConfiguration);
        this.repositoryRepository = new RepositoryGitHubRepository(webClientConfiguration);
        this.userRepository = new UserGitHubRepository(webClientConfiguration);
        this.pullRequestRepository = new PullRequestGitHubRepository(webClientConfiguration);
        this.reviewRepository = new ReviewGitHubRepository(webClientConfiguration);
        this.commentRepository = new CommentGitHubRepository(webClientConfiguration);
    }

    private static void mergeReviewAggregations(
        Map<Team, Map<User, ReviewAggregation>> result,
        Entry<Team, Map<User, ReviewAggregation>> futureEntry
    ) {
        Map<User, ReviewAggregation> maybeReviewAggregation = result.get(
            futureEntry.getKey());
        if (maybeReviewAggregation == null) {
            result.put(futureEntry.getKey(), futureEntry.getValue());
        } else {
            maybeReviewAggregation.entrySet().parallelStream().forEach(ttEntry -> {
                maybeReviewAggregation
                    .put(ttEntry.getKey(),
                        ttEntry.getValue().merge(futureEntry.getValue().get(ttEntry.getKey())));
            });
        }
    }

    private static void mergeCommentAggregation(
        Map<Team, Map<User, CommentAggregation>> result,
        Entry<Team, Map<User, CommentAggregation>> futureEntry
    ) {
        Map<User, CommentAggregation> maybeCommentAggregation = result.get(
            futureEntry.getKey());
        if (maybeCommentAggregation == null) {
            result.put(futureEntry.getKey(), futureEntry.getValue());
        } else {
            maybeCommentAggregation.entrySet().parallelStream().forEach(ttEntry -> {
                maybeCommentAggregation
                    .put(ttEntry.getKey(),
                        ttEntry.getValue().merge(futureEntry.getValue().get(ttEntry.getKey())));
            });
        }
    }

    public void execute() {
        try {
            // Fetch the API key's available organizations.
            List<Organization> organizationList =
                new FetchAvailableOrganizations(organizationRepository).execute();
            organizationList
                .parallelStream()
                .filter(organization -> !userOptionRequest.isOrganizationType()
                    || organization.isNamed(userOptionRequest.getName()))
                .forEach(this::fetchTeamsFromOrganization);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchTeamsFromOrganization(Organization organization) {
        try {
            List<Team> teamList = new FetchTeamsFromOrganization(teamRepository)
                .execute(organization);
            teamList
                .parallelStream()
                .filter(
                    team -> !userOptionRequest.isTeamType()
                        || team.isNamed(userOptionRequest.getName()))
                .forEach(team -> {
                    fetchUsersFromTeam(team);
                    fetchRepositoriesFromTeam(team);
                });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchRepositoriesFromTeam(Team team) {
        try {
            // Fetch the repositories for each team.
            List<Repository> repositoryList = new FetchRepositoriesFromTeam(
                repositoryRepository).execute(team);
            // Add the team to the repository
            repositoryList.parallelStream().forEach(this::fetchPullRequestsFromRepository);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchUsersFromTeam(Team team) {
        try {
            // Fetch the members of each team.
            new FetchUsersFromTeam(userRepository).execute(team);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchPullRequestsFromRepository(
        Repository repository) {
        try {
            // Fetch pull requests from each team.
            List<PullRequest> pullRequestList =
                new FetchPullRequestsInPeriodFromRepository(pullRequestRepository)
                    .execute(repository, userOptionRequest.getFrom(), userOptionRequest.getTo());

            ExecutorService executor = Executors.newFixedThreadPool(3);
            Map<Team, Map<User, ReviewAggregation>> reviewAggregationResult = new HashMap<>();
            Map<Team, Map<User, CommentAggregation>> commentAggregationResult = new HashMap<>();
            pullRequestList.parallelStream().forEach(pullRequest -> {
                Future<Map<Team, Map<User, ReviewAggregation>>> reviewsFuture = executor.submit(
                    () -> this.fetchReviewsFromPullRequest(pullRequest));
                Future<Map<Team, Map<User, CommentAggregation>>> commentsFuture = executor.submit(
                    () -> this.fetchCommentsFromPullRequest(pullRequest));

                try {
                    commentsFuture.get().entrySet().parallelStream().forEach(futureEntry -> {
                        mergeCommentAggregation(commentAggregationResult, futureEntry);
                    });
                    reviewsFuture.get().entrySet().parallelStream().forEach(futureEntry -> {
                        mergeReviewAggregations(reviewAggregationResult, futureEntry);
                    });
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            new MetricCsvExporter().export(new MergeAggregatesIntoMetrics().execute(
                new AggregatePullRequests().execute(
                    pullRequestList
                        .stream()
                        .filter(pr -> !userOptionRequest.isUserType()
                            || pr.isAuthorNamed(userOptionRequest.getName()))
                        .toList()
                ),
                commentAggregationResult,
                reviewAggregationResult,
                getRequestDateRange()
            ), "result.csv");

        } catch (HttpException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Team, Map<User, ReviewAggregation>> fetchReviewsFromPullRequest(
        PullRequest pullRequest) {
        try {
            // Fetch Reviews from each Pull Request.
            List<Review> reviewList = new FetchReviewsFromPullRequest(reviewRepository)
                .execute(pullRequest, getRequestDateRange())
                .parallelStream()
                .filter(review -> !userOptionRequest.isUserType()
                    || review.isAuthorNamed(userOptionRequest.getName())).toList();
            return new AggregateReviews().execute(reviewList);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Team, Map<User, CommentAggregation>> fetchCommentsFromPullRequest(
        PullRequest pullRequest) {
        try {
            // Fetch Comments from each Pull Request.
            List<Comment> commentList = new FetchCommentsFromPullRequest(commentRepository)
                .execute(pullRequest, getRequestDateRange())
                .parallelStream()
                .filter(comment -> !userOptionRequest.isUserType()
                    || comment.isAuthorNamed(userOptionRequest.getName()))
                .toList();
            return new AggregateComments().execute(commentList);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private DateRange getRequestDateRange() {
        return DateRange.builder()
            .from(userOptionRequest.getFrom().toInstant())
            .to(userOptionRequest.getTo().toInstant())
            .build();
    }
}
