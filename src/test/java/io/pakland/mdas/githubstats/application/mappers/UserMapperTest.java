package io.pakland.mdas.githubstats.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserMapperTest {
    @Test
    public void shouldConvertDtoToEntity() {
        UserDTO dto = Mockito.mock(UserDTO.class);
        Mockito.when(dto.getId()).thenReturn(1);
        Mockito.when(dto.getLogin()).thenReturn("github-stats");

        User entity = UserMapper.dtoToEntity(dto);

        assertEquals(1, (int) entity.getId());
        assertEquals("github-stats", entity.getLogin());
    }

}
