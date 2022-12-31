package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.RepositoryMapper;
import io.pakland.mdas.githubstats.domain.Repository;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubRepositoryDTO;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class RepositoryGitHubRepository implements RepositoryExternalRepository {

    private final WebClientConfiguration webClientConfiguration;

    public RepositoryGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Repository> fetchRepositoriesFromTeam(Team team) throws HttpException {
        final String uri = String.format("/orgs/%s/teams/%s/repos",
            team.getOrganization().getLogin(),
            team.getSlug()
        );

        try {
            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubRepositoryDTO.class)
                .map(RepositoryMapper::dtoToEntity)
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
