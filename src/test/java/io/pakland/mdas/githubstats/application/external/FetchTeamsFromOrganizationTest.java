package io.pakland.mdas.githubstats.application.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FetchTeamsFromOrganizationTest {

    private final Organization organization = Organization.builder().id(1).login("github-stats-22")
        .build();

    @BeforeEach
    public void init() {
        organization.setTeams(new HashSet<>());
    }

    @Test
    public void shouldFetchTheTeams_givenAnOrganization() throws HttpException {
        Team teamOne = Team.builder().id(1).slug("gs-internal").build();
        Team teamTwo = Team.builder().id(2).slug("gs-external").build();
        TeamExternalRepository repository = Mockito.mock(TeamExternalRepository.class);
        Mockito.when(repository.fetchTeamsFromOrganization(any(Organization.class)))
            .thenReturn(List.of(teamOne, teamTwo));

        List<Team> result = new FetchTeamsFromOrganization(repository).execute(organization);

        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getId(), 1);
        assertEquals(result.get(1).getId(), 2);
        assertEquals(result.get(0).getOrganization(), organization);
        assertEquals(result.get(1).getOrganization(), organization);
    }

    @Test
    public void shouldThrowAnException_whenRepositoryThrows() throws HttpException {
        TeamExternalRepository repository = Mockito.mock(TeamExternalRepository.class);
        Mockito.when(repository.fetchTeamsFromOrganization(any(Organization.class)))
            .thenThrow(HttpException.class);

        assertThrows(HttpException.class,
            () -> new FetchTeamsFromOrganization(repository).execute(organization));
    }
}
