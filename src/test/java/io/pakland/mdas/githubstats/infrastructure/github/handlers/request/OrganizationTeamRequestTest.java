package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import org.junit.jupiter.api.Test;

public class OrganizationTeamRequestTest {

    @Test
    public void shouldReturnAPair_whenGetDataIsCalled() {
        Organization organization = Organization.builder().id(1).login("github-stats").build();
        Team team = Team.builder().id(2).slug("gs-developers").build();
        OrganizationTeamRequest request = new OrganizationTeamRequest(organization, team);

        assertEquals(request.getData().getFirst(), organization);
        assertEquals(request.getData().getSecond(), team);
    }
}
