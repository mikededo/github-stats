package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.TeamNotFound;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GetOrganizationFromTeamNameTest {
    @Test
    public void givenTeamName_shouldReturnOrganizationFound() throws TeamNotFound {
        Organization organization = new Organization();
        organization.setId(1);
        Team team = new Team();
        team.setOrganization(organization);

        TeamRepository teamRepoMock = Mockito.mock(TeamRepository.class);
        Mockito.when(teamRepoMock.findTeamByName(Mockito.anyString())).thenReturn(Optional.of(team));

        GetOrganizationFromTeamName useCase = new GetOrganizationFromTeamName(teamRepoMock);
        Organization result = useCase.execute("some team");

        Mockito.verify(teamRepoMock, Mockito.times(1)).findTeamByName("some team");
        assertEquals(result.getId(), organization.getId());
    }

    // Change for expecting an exception
    @Test
    public void givenTeamName_shouldThrowTeamNotFound_ifTeamNotFound() {
        TeamRepository teamRepoMock = Mockito.mock(TeamRepository.class);
        Mockito.when(teamRepoMock.findTeamByName(Mockito.anyString())).thenReturn(Optional.empty());

        GetOrganizationFromTeamName useCase = new GetOrganizationFromTeamName(teamRepoMock);
        assertThrows(TeamNotFound.class, () -> {
            useCase.execute("some team");
        });

        Mockito.verify(teamRepoMock, Mockito.times(1)).findTeamByName("some team");
    }
}
