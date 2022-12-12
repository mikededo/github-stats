package io.pakland.mdas.githubstats.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.HashSet;
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
        organization.setTeams(new HashSet<>(Collections.singletonList(team)));

        assertNull(team.getOrganization());
        assertEquals(organization.getTeams().size(), 1);
        organization.addTeam(team);

        assertEquals(organization.getTeams().size(), 1);
        assertEquals(team.getOrganization(), organization);
    }

    @Test
    public void shouldCheckForEqualOrganizations() {
        Organization original = Organization.builder().id(1).build();
        Organization equal = Organization.builder().id(1).build();
        Organization notEqual = Organization.builder().id(2).build();

        assertEquals(original, equal);
        assertNotEquals(original, notEqual);
    }

}
