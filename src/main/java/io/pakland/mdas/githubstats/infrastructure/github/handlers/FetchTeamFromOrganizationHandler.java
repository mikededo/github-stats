package io.pakland.mdas.githubstats.infrastructure.github.handlers;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.FetchTeamsFromOrganization;
import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.application.handlers.RequestHandler;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.OrganizationRequest;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.OrganizationTeamRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.TeamGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import java.util.ArrayList;
import java.util.List;

public class FetchTeamFromOrganizationHandler implements RequestHandler {

    private final WebClientConfiguration webConfig;
    private final List<RequestHandler> next;

    public FetchTeamFromOrganizationHandler(WebClientConfiguration config) {
        this.webConfig = config;
        this.next = new ArrayList<>();
    }

    @Override
    public FetchTeamFromOrganizationHandler addNext(RequestHandler handler) {
        this.next.add(handler);
        return this;
    }

    @Override
    public void handle(Request request) {
        if (!(request instanceof OrganizationRequest)) {
            throw new RuntimeException(
                "FetchTeamFromOrganizationHandler::handle called with request different of type OrganizationRequest");
        }

        try {
            Organization organization = ((OrganizationRequest) request).getData();
            List<Team> teamList = new FetchTeamsFromOrganization(
                new TeamGitHubRepository(this.webConfig)).execute(organization.getLogin());
            
            // Fetch teams and users from organization
            teamList.forEach(team -> {
                this.next.forEach(handler -> handler.handle(new OrganizationTeamRequest(organization, team)));
            });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
