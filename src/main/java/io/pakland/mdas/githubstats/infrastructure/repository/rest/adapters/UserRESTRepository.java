package io.pakland.mdas.githubstats.infrastructure.repository.rest.adapters;

import io.pakland.mdas.githubstats.infrastructure.repository.rest.GithubWebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.repository.rest.dto.UserDTO;
import io.pakland.mdas.githubstats.infrastructure.repository.rest.ports.IUserRESTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class UserRESTRepository implements IUserRESTRepository {

    @Autowired
    private WebClient webClient;

    Logger logger = LoggerFactory.getLogger(UserRESTRepository.class);

    @Override
    public List<UserDTO> getUsersFromTeam(String organizationName, String teamSlug) {
        try {
            logger.info("About to fetch User list for organization: " + organizationName + " and team: " + teamSlug);
            return webClient.get()
                    .uri("/orgs/" + organizationName + "/teams/" + teamSlug + "/members")
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
