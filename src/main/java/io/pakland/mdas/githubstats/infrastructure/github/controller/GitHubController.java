package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.*;
import io.pakland.mdas.githubstats.domain.entity.*;
import io.pakland.mdas.githubstats.domain.repository.*;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@NoArgsConstructor
public class GitHubController {

    private WebClientConfiguration webClientConfiguration;
    private GitHubUserOptionRequest userOptionRequest;

    public GitHubController(GitHubUserOptionRequest userOptionRequest) {
        this.webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());
        this.userOptionRequest = userOptionRequest;
    }

    public void execute() {
        try {
            // Fetch the API key's available organizations.
            OrganizationExternalRepository organizationExternalRepository =
                new OrganizationGitHubRepository(this.webClientConfiguration);
            List<Organization> organizationList =
                new FetchAvailableOrganizations(organizationExternalRepository)
                    .execute();
            organizationList.forEach(this::fetchTeamsFromOrganization);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchTeamsFromOrganization(Organization organization) {
        try {
            TeamExternalRepository teamExternalRepository =
                new TeamGitHubRepository(this.webClientConfiguration);
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
            RepositoryExternalRepository repositoryExternalRepository =
                new RepositoryGitHubRepository(this.webClientConfiguration);
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
            UserExternalRepository userExternalRepository =
                new UserGitHubRepository(this.webClientConfiguration);
            new FetchUsersFromTeam(userExternalRepository).execute(team);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchPullRequestsFromRepository(Repository repository) {
        try {
            // Fetch pull requests from each team.
            PullRequestExternalRepository pullRequestExternalRepository =
                new PullRequestGitHubRepository(this.webClientConfiguration);
            List<PullRequest> pullRequestList =
                new FetchPullRequestsInPeriodFromRepository(pullRequestExternalRepository)
                    .execute(repository, userOptionRequest.getFrom(), userOptionRequest.getTo());

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
            CommitExternalRepository commitExternalRepository =
                new CommitGitHubRepository(this.webClientConfiguration);
            List<Commit> commitList = new FetchCommitsFromPullRequest(commitExternalRepository)
                .execute(pullRequest);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchReviewsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Reviews from each Pull Request.
            ReviewExternalRepository reviewExternalRepository =
                new ReviewGitHubRepository(this.webClientConfiguration);
            List<Review> reviewList = new FetchReviewsFromPullRequest(reviewExternalRepository)
                .execute(pullRequest);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchCommentsFromPullRequest(PullRequest pullRequest) {
        try {
            // Fetch Comments from each Pull Request.
            CommentExternalRepository commentExternalRepository =
                new CommentGitHubRepository(this.webClientConfiguration);
            List<Comment> commentList = new FetchCommentsFromPullRequest(commentExternalRepository)
                .execute(pullRequest);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
