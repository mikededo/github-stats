package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.domain.entity.Organization;
import org.junit.jupiter.api.Test;

public class OrganizationRequestTest {

    @Test
    public void shouldReturnTheOrganization_whenGetDataIsCalled() {
        Organization organization = Organization.builder().id(1).login("github-stats").build();
        OrganizationRequest request = new OrganizationRequest(organization);

        assertEquals(request.getData(), organization);
    }
}
