package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.domain.model.Organization;
import io.pakland.mdas.githubstats.domain.model.Team;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTeamsFromOrganizationTest {
    @Test
    public void givenOrganization_shouldReturnListOfTeams() {
        Team t = new Team();
        t.setId(1L);
        Organization mockOrg = Mockito.mock(Organization.class);
        Mockito.when(mockOrg.getTeams()).thenReturn(List.of(t));

        GetTeamsFromOrganization useCase = new GetTeamsFromOrganization();
        List<Team> teams = useCase.execute(mockOrg);

        Mockito.verify(mockOrg, Mockito.times(1)).getTeams();
        assertEquals(teams.get(0).getId(), t.getId());
    }
}
