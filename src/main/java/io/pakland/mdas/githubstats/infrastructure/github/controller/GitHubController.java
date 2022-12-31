package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.application.*;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.*;
import io.pakland.mdas.githubstats.domain.repository.*;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class GitHubController {

    private final SaveMetrics saveMetrics;

    private GitHubOptionRequest userOptionRequest;
    private OrganizationExternalRepository organizationRepository;
    private TeamExternalRepository teamRepository;
    private RepositoryExternalRepository repositoryRepository;
    private UserExternalRepository userRepository;
    private PullRequestExternalRepository pullRequestRepository;
    private ReviewExternalRepository reviewRepository;
    private CommentExternalRepository commentRepository;

    public GitHubController(SaveMetrics saveMetrics) {
        this.saveMetrics = saveMetrics;
    }

    private void githubInit(GitHubOptionRequest userOptionRequest) {
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

    private boolean isLoggerSilenced() {
        return this.userOptionRequest.isSilenced();
    }

    private void logIfNotSilenced(String text) {
        if (!isLoggerSilenced()) {
            System.out.println(text);
        }
    }

    public void execute(GitHubOptionRequest userOptionRequest) {
        githubInit(userOptionRequest);

        logIfNotSilenced("- Fetching organizations");

        try {
            // Fetch the API key's available organizations.
            List<Organization> organizationList =
                new FetchAvailableOrganizations(organizationRepository).execute();
            List<Metric> resultMetrics = new ArrayList<>();
            organizationList
                .parallelStream()
                .filter(organization -> !userOptionRequest.isOrganizationType()
                    || organization.isNamed(userOptionRequest.getName()))
                .forEach(organization -> {
                    logIfNotSilenced(
                        String.format("- Fetching teams of -> %s", organization.getLogin()));
                    resultMetrics.addAll(
                        this.fetchTeamsFromOrganization(organization));
                });

            logIfNotSilenced("- Saving data to database...");
            saveMetrics.execute(resultMetrics);

            logIfNotSilenced("- Preparing CSV...");
            // Finally export the results
            new ExportMetricsToFile(new GitHubMetricCsvExporter())
                .execute(resultMetrics, userOptionRequest.getFilePath());
        } catch (HttpException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Metric> fetchTeamsFromOrganization(Organization organization) {
        try {
            List<Team> teamList = new FetchTeamsFromOrganization(teamRepository)
                .execute(organization);
            List<Metric> teamMetrics = new ArrayList<>();
            teamList
                .parallelStream()
                .filter(
                    team -> !userOptionRequest.isTeamType()
                        || team.isNamed(userOptionRequest.getName()))
                .forEach(team -> {
                    logIfNotSilenced(
                        String.format("- Fetching user of team -> %s", team.getSlug()));
                    fetchUsersFromTeam(team);

                    logIfNotSilenced(
                        String.format("- Fetching repositories of team -> %s", team.getSlug()));
                    teamMetrics.addAll(fetchRepositoriesFromTeam(team));
                });
            return teamMetrics;
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Metric> fetchRepositoriesFromTeam(Team team) {
        try {
            // Fetch the repositories for each team.
            List<Repository> repositoryList = new FetchRepositoriesFromTeam(
                repositoryRepository).execute(team);
            // Add the team to the repository
            List<Metric> repositoryMetrics = new ArrayList<>();
            repositoryList.parallelStream().forEach(repository -> {
                logIfNotSilenced(
                    String.format("- Fetching pull requests of repository -> %s", team.getSlug()));
                repositoryMetrics.addAll(this.fetchPullRequestsFromRepository(repository));
            });
            return repositoryMetrics;
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchUsersFromTeam(Team team) {
        // We use this method to link the users to the team to later check if a user belongs to a
        // team
        try {
            // Fetch the members of each team.
            new FetchUsersFromTeam(userRepository).execute(team);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Metric> fetchPullRequestsFromRepository(
        Repository repository) {
        try {
            // Fetch pull requests from each team.
            List<PullRequest> pullRequestList =
                new FetchPullRequestsInPeriodFromRepository(pullRequestRepository)
                    .execute(repository, userOptionRequest.getFrom(), userOptionRequest.getTo());

            ExecutorService executor = Executors.newFixedThreadPool(3);
            List<Review> reviewList = new ArrayList<>();
            List<Comment> commentList = new ArrayList<>();
            pullRequestList.parallelStream().forEach(pullRequest -> {
                logIfNotSilenced(
                    String.format(
                        "- Fetching reviews of pull request -> %s",
                        pullRequest.getNumber()
                    )
                );
                Future<List<Review>> reviewsFuture = executor.submit(
                    () -> this.fetchReviewsFromPullRequest(pullRequest));

                logIfNotSilenced(
                    String.format(
                        "- Fetching comments of pull request -> %s",
                        pullRequest.getNumber()
                    )
                );
                Future<List<Comment>> commentsFuture = executor.submit(
                    () -> this.fetchCommentsFromPullRequest(pullRequest));

                try {
                    commentList.addAll(commentsFuture.get());
                    reviewList.addAll(reviewsFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            logIfNotSilenced(
                String.format("- Aggregation data for repository -> %s", repository.getName()));
            return new MergeAggregatesIntoMetrics().execute(
                new AggregatePullRequests().execute(
                    pullRequestList
                        .stream()
                        .filter(this::isAuthorValidForUserOption)
                        .toList()
                ),
                new AggregateComments().execute(commentList),
                new AggregateReviews().execute(reviewList),
                getRequestDateRange()
            );

        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Review> fetchReviewsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Reviews from each Pull Request.
            return new FetchReviewsFromPullRequest(reviewRepository)
                .execute(pullRequest, getRequestDateRange())
                .parallelStream()
                .filter(this::isAuthorValidForUserOption)
                .toList();
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Comment> fetchCommentsFromPullRequest(
        PullRequest pullRequest) {
        try {
            // Fetch Comments from each Pull Request.
            return new FetchCommentsFromPullRequest(commentRepository)
                .execute(pullRequest, getRequestDateRange())
                .parallelStream()
                .filter(this::isAuthorValidForUserOption)
                .toList();
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

    private boolean isAuthorValidForUserOption(Authored entity) {
        boolean isValidUser = !userOptionRequest.isUserType()
            || entity.isAuthorNamed(userOptionRequest.getName());
        boolean isValidTeam = !userOptionRequest.isTeamType()
            || entity.isAuthorFromEntityTeam();
        return isValidUser && isValidTeam;
    }
}
