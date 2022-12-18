package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.internal.GetUserByLogin;
import io.pakland.mdas.githubstats.application.internal.OrchestrateAggregators;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.exceptions.UserLoginNotFound;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.OrganizationGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;

import java.util.List;

public class UserControllerFromDb {

    private final UserOptionRequest userOptionRequest;

    private final GetUserByLogin getUserByLogin;

    private final OrganizationExternalRepository organizationExternalRepository;

    public UserControllerFromDb(UserOptionRequest userOptionRequest, GetUserByLogin getUserByLogin) {
        this.userOptionRequest = userOptionRequest;
        this.getUserByLogin = getUserByLogin;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
                "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationExternalRepository = new OrganizationGitHubRepository(
                webClientConfiguration);
    }

    public void execute() throws UserLoginNotFound {
        List<Organization> orgs;

        try {
            orgs = new FetchAvailableOrganizations(this.organizationExternalRepository).execute();
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }

        User user = getUserByLogin.execute(userOptionRequest.getUserName());

        List<PullRequest> pullRequests = orgs.stream()
            .flatMap(org -> org.getTeams().stream())
            .flatMap(team -> team.getRepositories().stream())
            .flatMap(repo -> repo.getPullRequests().stream())
            .filter(pull -> pull.isClosed() && pull.isCreatedByUser(user))
            .toList();

        // TODO: we could have a chain of responsibility instead of a use case orchestrator
        OrchestrateAggregators orchestrator = new OrchestrateAggregators();
        orchestrator.execute(user, pullRequests);
    }
}