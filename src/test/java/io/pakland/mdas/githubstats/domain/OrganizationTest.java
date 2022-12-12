package io.pakland.mdas.githubstats.domain;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import org.junit.jupiter.api.Test;

public class OrganizationTest {

    @Test
    public void shouldAddTheTeamToTheOrganization_andSetTheTeamOrganization() {
        Organization organization = Organization.builder().id(1).login("github-stats").build();
        Team team = Team.builder().id(1).slug("gs-developers").build();

        assertNull(team.getOrganization());
        organization.addTeam(team);

        assertEquals(organization.getTeams().size(), 1);
        assertEquals(team.getOrganization(), organization);
    }

    @Test
    public void shouldNotAddTheTeam_whenTheTeamIsAlreadyContained() {
        Organization organization = Organization.builder().id(1).login("github-stats").build();
        Team team = Team.builder().id(1).slug("gs-developers").build();
        organization.setTeams(Collections.singleton(team));

        assertNull(team.getOrganization());
        assertEquals(organization.getTeams().size(), 1);
        organization.addTeam(team);

        assertEquals(organization.getTeams().size(), 1);
        assertEquals(team.getOrganization(), organization);
    }

}
