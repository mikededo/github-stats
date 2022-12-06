package io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters;

import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IUserRESTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Repository
public class UserRESTRepository implements IUserRESTRepository {

    private final WebClientConfiguration webClientConfiguration;
    Logger logger = LoggerFactory.getLogger(UserRESTRepository.class);

    public UserRESTRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<UserDTO> getUsersFromTeam(String organizationName, String teamSlug) {
        try {
            return this.webClientConfiguration.getWebClient().get()
                    .uri(String.format("/orgs/%s/teams/%s/members", organizationName, teamSlug))
                    .retrieve()
                    .bodyToFlux(UserDTO.class)
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
