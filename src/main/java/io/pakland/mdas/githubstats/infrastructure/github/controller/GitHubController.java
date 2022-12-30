package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.application.*;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.*;
import io.pakland.mdas.githubstats.domain.repository.*;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class GitHubController {

    private GitHubOptionRequest userOptionRequest;
    private OrganizationExternalRepository organizationRepository;
    private TeamExternalRepository teamRepository;
    private RepositoryExternalRepository repositoryRepository;
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
        this.pullRequestRepository = new PullRequestGitHubRepository(webClientConfiguration);
        this.reviewRepository = new ReviewGitHubRepository(webClientConfiguration);
        this.commentRepository = new CommentGitHubRepository(webClientConfiguration);
    }

    public void execute() {
        try {
            // Fetch the API key's available organizations.
            List<Organization> organizationList =
                new FetchAvailableOrganizations(organizationRepository).execute();
            organizationList
                .parallelStream()
                .filter(organization -> !userOptionRequest.getType().equals(OptionType.ORGANIZATION)
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
                    team -> !userOptionRequest.getType().equals(OptionType.TEAM)
                        || team.isNamed(userOptionRequest.getName()))
                .forEach(this::fetchRepositoriesFromTeam);
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

    private void fetchPullRequestsFromRepository(Repository repository) {
        try {
            // Fetch pull requests from each team.
            List<PullRequest> pullRequestList =
                new FetchPullRequestsInPeriodFromRepository(pullRequestRepository)
                    .execute(repository, userOptionRequest.getFrom(), userOptionRequest.getTo());

            ExecutorService executor = Executors.newFixedThreadPool(3);
            pullRequestList.parallelStream().forEach(pullRequest -> {
                Future<?> reviewsFuture = executor.submit(
                    () -> this.fetchReviewsFromPullRequest(pullRequest));
                Future<?> commentsFuture = executor.submit(
                    () -> this.fetchCommentsFromPullRequest(pullRequest));

                try {
                    reviewsFuture.get();
                    commentsFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            Map<Team, Map<User, PullRequestAggregation>> prAggregation = new AggregatePullRequests().execute(
                pullRequestList);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchReviewsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Reviews from each Pull Request.
            List<Review> reviewList = new FetchReviewsFromPullRequest(reviewRepository)
                .execute(pullRequest, getRequestDateRange());
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchCommentsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Comments from each Pull Request.
            List<Comment> commentList = new FetchCommentsFromPullRequest(commentRepository)
                .execute(pullRequest, getRequestDateRange());
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isBetweenRequestRange(Instant instant) {
        return instant.isAfter(userOptionRequest.getFrom().toInstant()) && instant.isBefore(
            userOptionRequest.getTo().toInstant());
    }

    private DateRange getRequestDateRange() {
        return DateRange.builder()
            .from(userOptionRequest.getFrom().toInstant())
            .to(userOptionRequest.getTo().toInstant())
            .build();
    }
}
