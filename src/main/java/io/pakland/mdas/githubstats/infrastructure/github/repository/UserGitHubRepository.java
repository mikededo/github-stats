package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.UserMapper;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Repository
public class UserGitHubRepository implements UserExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(UserGitHubRepository.class);

    public UserGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<User> fetchUsersFromTeam(String organizationName, String teamName)
        throws HttpException {
        try {
            return this.webClientConfiguration.getWebClient().get()
                .uri(String.format("/orgs/%s/teams/%s/members", organizationName, teamName))
                .retrieve()
                .bodyToFlux(GitHubUserDTO.class)
                .map(UserMapper::dtoToEntity)
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
