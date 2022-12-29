package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;

public class PullRequestMapper {

    public static PullRequest dtoToEntity(PullRequestDTO dto) {
        return PullRequest.builder()
            .id(dto.getId())
            .number(dto.getNumber())
            .state(dto.getState())
            .createdAt(dto.getCreatedAt())
            .closedAt(dto.getClosedAt())
            .user(UserMapper.dtoToEntity(dto.getUser()))
            .build();
    }

}
