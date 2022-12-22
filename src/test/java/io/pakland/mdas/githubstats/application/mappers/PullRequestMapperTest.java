package io.pakland.mdas.githubstats.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.PullRequestState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PullRequestMapperTest {

    @Test
    public void shouldConvertDtoToEntity() {
        PullRequestDTO dto = Mockito.mock(PullRequestDTO.class);
        Mockito.when(dto.getId()).thenReturn(1);
        Mockito.when(dto.getNumber()).thenReturn(1);
        Mockito.when(dto.getState()).thenReturn(PullRequestState.ALL);

        PullRequest entity = PullRequestMapper.dtoToEntity(dto);

        assertEquals(1, entity.getId());
        assertEquals(1, entity.getNumber());
        assertEquals(PullRequestState.ALL, entity.getState());
    }

}
