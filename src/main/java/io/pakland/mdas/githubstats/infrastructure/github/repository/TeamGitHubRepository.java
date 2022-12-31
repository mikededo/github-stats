package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.TeamMapper;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubTeamDTO;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class TeamGitHubRepository implements TeamExternalRepository {

    private final WebClientConfiguration webClientConfiguration;

    public TeamGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Team> fetchTeamsFromOrganization(Organization organization) throws HttpException {
        final String uri = String.format("/orgs/%s/teams", organization.getLogin());

        try {
            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubTeamDTO.class)
                .map(TeamMapper::dtoToEntity)
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
