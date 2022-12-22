package io.pakland.mdas.githubstats.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OrganizationMapperTest {

    @Test
    public void shouldConvertDtoToEntity() {
        OrganizationDTO dto = Mockito.mock(OrganizationDTO.class);
        Mockito.when(dto.getId()).thenReturn(1);
        Mockito.when(dto.getLogin()).thenReturn("github-stats-22");

         Organization entity = OrganizationMapper.dtoToEntity(dto);

        assertEquals(1, (int) entity.getId());
        assertEquals("github-stats-22", entity.getLogin());
    }
}
