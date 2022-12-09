package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.OrganizationNotFound;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetOrganizationFromIdTest {
    @Test
    public void givenValidId_shouldReturnTrue() throws OrganizationNotFound {
        OrganizationRepository organizationMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(organizationMock.findById(Mockito.anyInt())).thenReturn(Optional.of(new Organization()));

        GetOrganizationFromId useCase = new GetOrganizationFromId(organizationMock);

        assertTrue(useCase.execute(1));
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
