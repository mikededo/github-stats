package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.CommitDTO;
import io.pakland.mdas.githubstats.domain.entity.Commit;

public class CommitMapper {

    public static Commit dtoToEntity(CommitDTO dto) {
        return Commit.builder()
            .sha(dto.getSha())
            .user(UserMapper.dtoToEntity(dto.getUser()))
            .additions(dto.getAdditions())
            .deletions(dto.getDeletions())
            .committedAt(dto.getCommittedAt())
            .build();
    }
}
