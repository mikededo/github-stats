package io.pakland.mdas.githubstats.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.domain.entity.Team;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TeamMapperTest {

    @Test
    public void shouldConvertDtoToEntity() {
        TeamDTO dto = Mockito.mock(TeamDTO.class);
        Mockito.when(dto.getId()).thenReturn(1);
        Mockito.when(dto.getSlug()).thenReturn("gs-internal");

        Team entity = TeamMapper.dtoToEntity(dto);

        assertEquals(1, (int) entity.getId());
        assertEquals("gs-internal", entity.getSlug());
    }
}
