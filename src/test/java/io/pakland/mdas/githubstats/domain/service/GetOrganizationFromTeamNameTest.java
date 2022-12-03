package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.application.GetOrganizationFromTeamName;
import io.pakland.mdas.githubstats.domain.model.Organization;
import io.pakland.mdas.githubstats.domain.model.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetOrganizationFromTeamNameTest {
    @Test
    public void givenTeamName_shouldReturnOrganizationFound() {
        Organization organization = new Organization();
        organization.setId(1L);
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
    public void givenTeamName_shouldReturnNull_ifTeamNotFound() {
        TeamRepository teamRepoMock = Mockito.mock(TeamRepository.class);
        Mockito.when(teamRepoMock.findTeamByName(Mockito.anyString())).thenReturn(Optional.empty());

        GetOrganizationFromTeamName useCase = new GetOrganizationFromTeamName(teamRepoMock);
        Organization result = useCase.execute("some team");

        Mockito.verify(teamRepoMock, Mockito.times(1)).findTeamByName("some team");
        assertNull(result);
    }
}
