package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.application.GetOrganizationFromId;
import io.pakland.mdas.githubstats.application.exceptions.OrganizationNotFound;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetOrganizationFromIdTest {
    @Test
    public void givenValidId_shouldReturnTrue() throws OrganizationNotFound {
        OrganizationRepository organizationMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(organizationMock.findById(Mockito.anyLong())).thenReturn(Optional.of(new Organization()));

        GetOrganizationFromId useCase = new GetOrganizationFromId(organizationMock);

        assertTrue(useCase.execute(1L));
    }

    @Test
    public void givenInvalidId_shouldThrowOrganizationNotFound() throws OrganizationNotFound {
         OrganizationRepository organizationMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(organizationMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        GetOrganizationFromId useCase = new GetOrganizationFromId(organizationMock);

        assertThrows(OrganizationNotFound.class, () -> {
            useCase.execute(1L);
        });
    }
}
