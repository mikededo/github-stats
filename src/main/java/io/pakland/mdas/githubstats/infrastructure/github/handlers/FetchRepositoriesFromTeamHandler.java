package io.pakland.mdas.githubstats.infrastructure.github.handlers;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.FetchRepositoriesFromTeam;
import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.application.handlers.RequestHandler;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.OrganizationTeamRequest;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.RepositoryRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.RepositoryGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.util.Pair;

public class FetchRepositoriesFromTeamHandler implements RequestHandler {

    private final WebClientConfiguration webConfig;
    private final List<RequestHandler> next;

    public FetchRepositoriesFromTeamHandler(WebClientConfiguration config) {
        this.webConfig = config;
        this.next = new ArrayList<>();
    }

    @Override
    public void addNext(RequestHandler handler) {
        this.next.add(handler);
    }

    @Override
    public void handle(Request request) {
        if (!(request instanceof OrganizationTeamRequest)) {
            throw new RuntimeException(
                "FetchRepositoriesFromTeamHandler::handle called with request different of type OrganizationTeamRequest");
        }

        try {
            Pair<Organization, Team> pair = ((OrganizationTeamRequest) request).getData();
            Organization organization = pair.getFirst();
            Team team = pair.getSecond();

            List<Repository> repositoryList = new FetchRepositoriesFromTeam(
                new RepositoryGitHubRepository(this.webConfig)
            ).execute(organization.getLogin(), team.getSlug());

            // Add the team to the repository and fetch the pull requests
            repositoryList.forEach(repository -> {
                repository.setTeam(team);
                this.next.forEach(handler -> handler.handle(new RepositoryRequest(repository)));
            });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
