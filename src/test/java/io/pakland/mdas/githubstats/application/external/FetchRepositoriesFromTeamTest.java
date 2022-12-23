package io.pakland.mdas.githubstats.application.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FetchRepositoriesFromTeamTest {

    private final Team team = Team.builder().id(1).slug("gs-internal").build();
    private final Organization organization = Organization.builder().teams(Set.of(team))
        .login("github-stats-22").build();

    @BeforeEach
    public void init() {
        team.setRepositories(new HashSet<>());
        team.setOrganization(organization);
    }

    @Test
    public void shouldFetchTheRepositories_givenATeam() throws HttpException {
        Repository repoOne = Repository.builder().id(1).name("github-stats").build();
        Repository repoTwo = Repository.builder().id(2).name("empty-repository").build();
        RepositoryExternalRepository repository = Mockito.mock(RepositoryExternalRepository.class);
        Mockito.when(repository.fetchTeamRepositories(anyString(), anyString())).thenReturn(
            List.of(repoOne, repoTwo));

        List<Repository> result = new FetchRepositoriesFromTeam(repository).execute(team);

        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getId(), 1);
        assertEquals(result.get(1).getId(), 2);
        assertEquals(result.get(0).getTeam(), team);
        assertEquals(result.get(1).getTeam(), team);

    }

    @Test
    public void shouldThrowAnException_whenRepositoryThrows() throws HttpException {
        RepositoryExternalRepository repository = Mockito.mock(RepositoryExternalRepository.class);
        Mockito.when(repository.fetchTeamRepositories(anyString(), anyString()))
            .thenThrow(HttpException.class);

        assertThrows(HttpException.class,
            () -> new FetchRepositoriesFromTeam(repository).execute(team));
    }

}
