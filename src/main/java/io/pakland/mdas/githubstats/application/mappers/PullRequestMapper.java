package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.domain.PullRequest;

public class PullRequestMapper {

    public static PullRequest dtoToEntity(PullRequestDTO dto) {
        return PullRequest.builder()
            .id(dto.getId())
            .number(dto.getNumber())
            .state(dto.getState())
            .merged(dto.getMerged())
            .createdAt(dto.getCreatedAt())
            .closedAt(dto.getClosedAt())
            .numCommits(dto.getNumCommits())
            .additions(dto.getAdditions())
            .deletions(dto.getDeletions())
            .user(UserMapper.dtoToEntity(dto.getUser()))
            .build();
    }

}
