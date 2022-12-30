package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.domain.User;

public class UserMapper {
    public static User dtoToEntity(UserDTO dto) {
        return User.builder()
            .id(dto.getId())
            .login(dto.getLogin())
            .build();
    }
}
