package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.domain.entity.User;
import java.util.ArrayList;

public class UserMapper {
    public static User dtoToEntity(UserDTO dto) {
        return User.builder()
            .id(dto.getId())
            .login(dto.getLogin())
            .reviews(new ArrayList<>())
            .commits(new ArrayList<>())
            .pullRequests(new ArrayList<>())
            .build();
    }
}
