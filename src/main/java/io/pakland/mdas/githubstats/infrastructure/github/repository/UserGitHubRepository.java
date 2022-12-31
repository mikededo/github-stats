package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.UserMapper;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.User;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserDTO;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Repository
public class UserGitHubRepository implements UserExternalRepository {

    private final WebClientConfiguration webClientConfiguration;

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
            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubUserDTO.class)
                .map(UserMapper::dtoToEntity)
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            System.err.println(ex);
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
