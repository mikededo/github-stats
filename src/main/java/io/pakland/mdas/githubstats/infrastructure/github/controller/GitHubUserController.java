package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.*;
import io.pakland.mdas.githubstats.domain.entity.*;
import io.pakland.mdas.githubstats.domain.repository.*;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import java.util.concurrent.*;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class GitHubUserController {

    Logger logger = LoggerFactory.getLogger(GitHubUserController.class);
    private OrganizationExternalRepository organizationExternalRepository;
    private TeamExternalRepository teamExternalRepository;
    private UserExternalRepository userExternalRepository;
    private RepositoryExternalRepository repositoryExternalRepository;
    private PullRequestExternalRepository pullRequestExternalRepository;
    private CommitExternalRepository commitExternalRepository;
    private ReviewExternalRepository reviewExternalRepository;
    private CommentExternalRepository commentExternalRepository;


    public GitHubUserController(GitHubUserOptionRequest userOptionRequest) {
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
                "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationExternalRepository = new OrganizationGitHubRepository(
                webClientConfiguration);
        this.teamExternalRepository = new TeamGitHubRepository(webClientConfiguration);
        this.userExternalRepository = new UserGitHubRepository(webClientConfiguration);
        this.repositoryExternalRepository = new RepositoryGitHubRepository(webClientConfiguration);
        this.pullRequestExternalRepository = new PullRequestGitHubRepository(
                webClientConfiguration);
        this.organizationExternalRepository = new OrganizationGitHubRepository(
                webClientConfiguration);
        this.teamExternalRepository = new TeamGitHubRepository(webClientConfiguration);
        this.userExternalRepository = new UserGitHubRepository(webClientConfiguration);
        this.repositoryExternalRepository = new RepositoryGitHubRepository(webClientConfiguration);
        this.pullRequestExternalRepository = new PullRequestGitHubRepository(
                webClientConfiguration);
        this.commitExternalRepository = new CommitGitHubRepository(webClientConfiguration);
        this.reviewExternalRepository = new ReviewGitHubRepository(webClientConfiguration);
        this.commentExternalRepository = new CommentGitHubRepository(webClientConfiguration);
    }

    public void execute() {
        try {
            // TODO: If the execution succeeds, we should make an entry to the historic_queries table.
            // Fetch the API key's available organizations.
            List<Organization> organizationList = new FetchAvailableOrganizations(
                    this.organizationExternalRepository)
                    .execute();
            organizationList.forEach(this::fetchTeamsFromOrganization);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchTeamsFromOrganization(Organization organization) {
        try {
            List<Team> teamList = new FetchTeamsFromOrganization(teamExternalRepository)
                    .execute(organization);
            teamList.parallelStream().forEach(team -> {
                this.fetchRepositoriesFromTeam(team);
                this.fetchUsersFromTeam(team);
            });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchRepositoriesFromTeam(Team team) {
        try {
            // Fetch the repositories for each team.
            List<Repository> repositoryList = new FetchRepositoriesFromTeam(
                    repositoryExternalRepository).execute(team);
            // Add the team to the repository
            repositoryList.parallelStream().forEach(this::fetchPullRequestsFromRepository);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchUsersFromTeam(Team team) {
        try {
            // Fetch the members of each team.
            new FetchUsersFromTeam(userExternalRepository).execute(team);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchPullRequestsFromRepository(Repository repository) {
        try {
            // Fetch pull requests from each team.
            List<PullRequest> pullRequestList = new FetchPullRequestsFromRepository(
                    pullRequestExternalRepository)
                    .execute(repository);

            ExecutorService executor = Executors.newFixedThreadPool(3);
            pullRequestList.parallelStream().forEach(pullRequest -> {
                Future<?> future1 = executor.submit(() -> this.fetchCommitsFromPullRequest(pullRequest));
                Future<?> future2 = executor.submit(() -> this.fetchReviewsFromPullRequest(pullRequest));
                Future<?> future3 = executor.submit(() -> this.fetchCommentsFromPullRequest(pullRequest));

                try {
                    future1.get();
                    future2.get();
                    future3.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchCommitsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Commits from each Pull Request.
            List<Commit> commitList = new FetchCommitsFromPullRequest(commitExternalRepository)
                    .execute(pullRequest);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchReviewsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Reviews from each Pull Request.
            List<UserReview> reviewList = new FetchReviewsFromPullRequest(reviewExternalRepository)
                    .execute(pullRequest);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchCommentsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Comments from each Pull Request.
            List<Comment> commentList = new FetchCommentsFromPullRequest(commentExternalRepository)
                    .execute(pullRequest);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
