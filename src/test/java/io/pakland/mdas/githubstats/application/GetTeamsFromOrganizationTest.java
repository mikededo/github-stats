package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Team;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTeamsFromOrganizationTest {
    @Test
    public void givenOrganization_shouldReturnListOfTeams() {
        Team team = new Team();
        team.setId(1);
        Organization organizationMock = Mockito.mock(Organization.class);
        Mockito.when(organizationMock.getTeams()).thenReturn(List.of(team));

        GetTeamsFromOrganization useCase = new GetTeamsFromOrganization();
        List<Team> teams = useCase.execute(organizationMock);

        Mockito.verify(organizationMock, Mockito.times(1)).getTeams();
        assertEquals(teams.get(0).getId(), team.getId());
    }
}
