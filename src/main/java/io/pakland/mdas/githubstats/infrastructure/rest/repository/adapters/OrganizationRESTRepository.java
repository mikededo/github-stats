package io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IOrganizationRESTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class OrganizationRESTRepository implements IOrganizationRESTRepository {

    private final WebClientConfiguration webClientConfiguration;
    Logger logger = LoggerFactory.getLogger(OrganizationRESTRepository.class);

    public OrganizationRESTRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<OrganizationDTO> fetchAvailableOrganizations() {
        try {
            return this.webClientConfiguration.getWebClient().get()
                    .uri("/user/orgs")
                    .retrieve()
                    .bodyToFlux(OrganizationDTO.class)
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
        }    }
}
