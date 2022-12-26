package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.TeamMapper;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubTeamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class TeamGitHubRepository implements TeamExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(TeamGitHubRepository.class);

    public TeamGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Team> fetchTeamsFromOrganization(Organization organization) throws HttpException {
        try {
            final String uri = String.format("/orgs/%s/teams", organization.getLogin());
            logger.info(" - Fetching teams from organization: " + organization.getLogin());
            
            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubTeamDTO.class)
                .map(TeamMapper::dtoToEntity)
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
