package io.pakland.mdas.githubstats.infrastructure.github.handlers;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.application.handlers.RequestHandler;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.OrganizationTeamRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.UserGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import java.util.List;
import org.springframework.data.util.Pair;

public class FetchUsersFromTeamHandler implements RequestHandler {

    private final WebClientConfiguration webConfig;

    public FetchUsersFromTeamHandler(WebClientConfiguration config) {
        this.webConfig = config;
    }

    @Override
    public void addNext(RequestHandler handler) {
        // As of now, we do not need to run any handler
    }

    @Override
    public void handle(Request request) {
        if (!(request instanceof OrganizationTeamRequest)) {
            throw new RuntimeException(
                "FetchUsersFromTeamHandler::handle called with request different of type OrganizationTeamRequest");
        }

        try {
            Pair<Organization, Team> pair = ((OrganizationTeamRequest) request).getData();
            Organization organization = pair.getFirst();
            Team team = pair.getSecond();

            List<User> userList = new io.pakland.mdas.githubstats.application.external.FetchUsersFromTeam(
                new UserGitHubRepository(this.webConfig)
            ).execute(organization.getLogin(), team.getSlug());

            // Add the users to the team
            team.setUsers(userList);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
