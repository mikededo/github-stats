package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.domain.model.Organization;
import io.pakland.mdas.githubstats.domain.model.Team;
import io.pakland.mdas.githubstats.domain.ports.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetOrganizationFromTeamNameTest {
    @Test
    public void givenTeamName_shouldReturnOrganizationFound() {
        Organization org = new Organization();
        org.setId(1L);
        Team t = new Team();
        t.setOrganization(org);

        TeamRepository teamRepoMock = Mockito.mock(TeamRepository.class);
        Mockito.when(teamRepoMock.findTeamByName(Mockito.anyString())).thenReturn(Optional.of(t));

        GetOrganizationFromTeamName useCase = new GetOrganizationFromTeamName(teamRepoMock);
        Organization res = useCase.execute("some team");

        Mockito.verify(teamRepoMock, Mockito.times(1)).findTeamByName("some team");
        assertEquals(res.getId(), org.getId());
    }

    // Change for expecting an exception
    @Test
    public void givenTeamName_shouldReturnNull_ifTeamNotFound() {
        TeamRepository teamRepoMock = Mockito.mock(TeamRepository.class);
        Mockito.when(teamRepoMock.findTeamByName(Mockito.anyString())).thenReturn(Optional.empty());

        GetOrganizationFromTeamName useCase = new GetOrganizationFromTeamName(teamRepoMock);
        Organization res = useCase.execute("some team");

        Mockito.verify(teamRepoMock, Mockito.times(1)).findTeamByName("some team");
        assertNull(res);
    }
}
