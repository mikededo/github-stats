package io.pakland.mdas.githubstats.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RepositoryMapperTest {

    @Test
    public void shouldConvertDtoToEntity() {
        RepositoryDTO dto = Mockito.mock(RepositoryDTO.class);
        Mockito.when(dto.getId()).thenReturn(1);
        Mockito.when(dto.getName()).thenReturn("github-stats");
        Mockito.when(dto.getOwnerLogin()).thenReturn("github-stats-22");

        Repository entity = RepositoryMapper.dtoToEntity(dto);

        assertEquals(1, (int) entity.getId());
        assertEquals("github-stats", entity.getName());
        assertEquals("github-stats-22", entity.getOwnerLogin());
    }
}
