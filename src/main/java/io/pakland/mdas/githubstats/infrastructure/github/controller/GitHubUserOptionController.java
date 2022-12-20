package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.infrastructure.github.handlers.*;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.NullRequest;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class GitHubUserOptionController {

    private WebClientConfiguration webClientConfiguration;

    public GitHubUserOptionController(GitHubUserOptionRequest userOptionRequest) {
        this.webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());
    }

    public void execute() {
        FetchOrganizationHandler fetchOrganizationHandler =
            new FetchOrganizationHandler(this.webClientConfiguration).addNext(
                new FetchTeamFromOrganizationHandler(this.webClientConfiguration).addNext(
                    new FetchRepositoriesFromTeamHandler(this.webClientConfiguration).addNext(
                        new FetchPullRequestsFromRepositoryHandler(
                            this.webClientConfiguration
                        ).addNext(
                            new FetchCommitsFromPullRequestHandler(this.webClientConfiguration)
                        )
                    )
                ).addNext(
                    new FetchUsersFromTeamHandler(this.webClientConfiguration)
                )
            );

        // Run the command
        fetchOrganizationHandler.handle(new NullRequest());
    }
}
