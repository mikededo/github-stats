package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class SaveOrganizationTest {
    @Test
    public void shouldSaveOrganization_whenDoesNotExist() {
        Organization organization = new Organization();
        organization.setId(1);
        OrganizationRepository orgRepositoryMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(orgRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        new SaveOrganization(orgRepositoryMock).execute(organization);

        Mockito.verify(orgRepositoryMock, Mockito.times(1)).findById(1);
        Mockito.verify(orgRepositoryMock, Mockito.times(1)).save(organization);
    }

    @Test
    public void shouldNotSaveOrganization_wheDoesExist() {
        Organization organization = new Organization();
        organization.setId(1);
        OrganizationRepository orgRepositoryMock = Mockito.mock(OrganizationRepository.class);
        Mockito.when(orgRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(organization));

        new SaveOrganization(orgRepositoryMock).execute(organization);

        Mockito.verify(orgRepositoryMock, Mockito.times(1)).findById(1);
        Mockito.verify(orgRepositoryMock, Mockito.times(0)).save(Mockito.any(Organization.class));
    }
}
