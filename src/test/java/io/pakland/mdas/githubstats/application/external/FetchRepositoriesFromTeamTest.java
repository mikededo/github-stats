package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FetchRepositoriesFromTeamTest {

    private final Team teamEntity = Team.builder().id(1).slug("gs-internal").build();
    private final Organization organization = Organization.builder()
        .teams(Set.of(teamEntity))
        .login("github-stats-22")
        .build();

    @BeforeEach
    public void init() {
        teamEntity.setRepositories(new HashSet<>());
        teamEntity.setOrganization(organization);
    }

    @Test
    public void shouldFetchTheRepositories_givenATeam() throws HttpException {
        Repository repositoryEntity = Repository.builder().id(1).name("github-stats").build();
        Repository repositoryEntity1 = Repository.builder().id(2).name("empty-repository").build();
        List<Repository> repositories = List.of(repositoryEntity, repositoryEntity1);
        RepositoryExternalRepository repository = Mockito.mock(RepositoryExternalRepository.class);
        Mockito.when(repository.fetchRepositoriesFromTeam(this.teamEntity))
            .thenReturn(repositories);

        List<Repository> result = new FetchRepositoriesFromTeam(repository).execute(this.teamEntity);

        assertEquals(2, result.size());
        assertEquals(repositoryEntity.getId(), result.get(0).getId());
        assertEquals(repositoryEntity1.getId(), result.get(1).getId());
        assertEquals(this.teamEntity, result.get(0).getTeam());
        assertEquals(this.teamEntity, result.get(1).getTeam());
    }

    @Test
    public void shouldThrowAnException_whenRepositoryThrows() throws HttpException {
        RepositoryExternalRepository repository = Mockito.mock(RepositoryExternalRepository.class);
        Mockito.when(repository.fetchRepositoriesFromTeam(this.teamEntity))
            .thenThrow(HttpException.class);

        assertThrows(HttpException.class,
            () -> new FetchRepositoriesFromTeam(repository).execute(this.teamEntity));
    }

}
