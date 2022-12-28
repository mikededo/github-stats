package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.UserMapper;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Repository
public class UserGitHubRepository implements UserExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(UserGitHubRepository.class);

    public UserGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<User> fetchUsersFromTeam(Team team) throws HttpException {
        final String uri = String.format("/orgs/%s/teams/%s/members",
            team.getOrganization().getLogin(),
            team.getSlug()
        );

        try {
            logger.info(" - Fetching users from team: " + team.getOrganization().getLogin() + "/" + team.getSlug());

            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
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
