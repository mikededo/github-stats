package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.OrganizationNotFound;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GetOrganizationFromIdTest {
    @Test
    public void givenValidId_shouldReturnTrue() throws OrganizationNotFound {
        OrganizationRepository organizationMock = Mockito.mock(OrganizationRepository.class);
        Organization organization = new Organization();
        Mockito.when(organizationMock.findById(Mockito.anyInt())).thenReturn(Optional.of(organization));

        GetOrganizationFromId useCase = new GetOrganizationFromId(organizationMock);

        assertEquals(useCase.execute(1), organization);
    }

    @Test
    public void givenInvalidId_shouldThrowOrganizationNotFound() throws OrganizationNotFound {
         OrganizationRepository organizationMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(organizationMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        GetOrganizationFromId useCase = new GetOrganizationFromId(organizationMock);

        assertThrows(OrganizationNotFound.class, () -> {
            useCase.execute(1);
        });
    }
}
