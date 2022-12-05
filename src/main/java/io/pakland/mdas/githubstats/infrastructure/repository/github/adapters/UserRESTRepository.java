package io.pakland.mdas.githubstats.infrastructure.repository.github.adapters;

import io.pakland.mdas.githubstats.infrastructure.repository.github.GithubRepositoryBase;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.infrastructure.repository.github.ports.IUserRESTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Repository
public class UserRESTRepository extends GithubRepositoryBase implements IUserRESTRepository {
    Logger logger = LoggerFactory.getLogger(UserRESTRepository.class);

    public UserRESTRepository(WebClient.Builder builder, @Value("${github.api.url}") String githubApiUrl, @Value("${github.api.key}") String githubApiKey) {
        super(builder, githubApiUrl, githubApiKey);
    }

    @Override
    public List<UserDTO> getUsersFromTeam(String organizationName, String teamSlug) {
        try {
            logger.info(String.format("About to fetch User list for organization: %s and team: %s", organizationName, teamSlug));
            List<UserDTO> userDTOList = this.githubClient.get()
                    .uri(String.format("/orgs/%s/teams/%s/members", organizationName, teamSlug))
                    .retrieve()
                    .bodyToFlux(UserDTO.class)
                    .collectList()
                    .block();
            logger.info(userDTOList.toString());
            return userDTOList;
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
