package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.domain.model.Organization;
import io.pakland.mdas.githubstats.domain.ports.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetOrganizationFromIdTest {
    @Test
    public void givenValidId_shouldReturnTrue() {
        OrganizationRepository orgMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(orgMock.findById(Mockito.anyLong())).thenReturn(Optional.of(new Organization()));

        GetOrganizationFromId useCase = new GetOrganizationFromId(orgMock);
        boolean res = useCase.execute(1L);

        assertTrue(res);
    }

    @Test
    public void givenInvalidId_shouldReturnFalse() {
         OrganizationRepository orgMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(orgMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        GetOrganizationFromId useCase = new GetOrganizationFromId(orgMock);
        boolean res = useCase.execute(1L);

        assertFalse(res);
    }
}
