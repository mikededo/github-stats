package io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IRepositoryRESTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class RepositoryRESTRepository implements IRepositoryRESTRepository {
    private final WebClientConfiguration webClientConfiguration;
    Logger logger = LoggerFactory.getLogger(OrganizationRESTRepository.class);

    public RepositoryRESTRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<RepositoryDTO> fetchTeamRepositories(Integer orgId, Integer teamId) throws HttpException {
        try {
            return this.webClientConfiguration.getWebClient().get()
                    .uri("/organizations/" + orgId + "/team/" + teamId + "/repos")
                    .retrieve()
                    .bodyToFlux(RepositoryDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
