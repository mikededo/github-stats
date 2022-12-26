package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.RepositoryMapper;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubRepositoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class RepositoryGitHubRepository implements RepositoryExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(OrganizationGitHubRepository.class);

    public RepositoryGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Repository> fetchRepositoriesFromTeam(Team team) throws HttpException {
        try {
            final String uri = String.format("/orgs/%s/teams/%s/repos",
                team.getOrganization().getLogin(),
                team.getSlug()
            );

            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubRepositoryDTO.class)
                .map(RepositoryMapper::dtoToEntity)
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
