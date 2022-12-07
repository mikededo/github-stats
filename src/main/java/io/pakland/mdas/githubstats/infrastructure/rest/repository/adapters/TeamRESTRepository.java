package io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.ITeamRESTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class TeamRESTRepository implements ITeamRESTRepository {

    private final WebClientConfiguration webClientConfiguration;
    Logger logger = LoggerFactory.getLogger(TeamRESTRepository.class);

    public TeamRESTRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<TeamDTO> fetchTeamsFromOrganization(String organizationName) {
        try {
            return this.webClientConfiguration.getWebClient().get()
                    .uri(String.format("/orgs/%s/teams", organizationName))
                    .retrieve()
                    .bodyToFlux(TeamDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            // TODO: How do we prettend to handle exceptions?
            logger.error(ex.toString());
            throw ex;
        } catch (Exception ex) {
            logger.error(ex.toString());
            // TODO: How do we prettend to handle exceptions?
            throw ex;
        }
    }
}
