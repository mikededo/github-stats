package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.CommentDTO;
import io.pakland.mdas.githubstats.domain.Comment;

public class CommentMapper {

    public static Comment dtoToEntity(CommentDTO dto) {
        return Comment.builder()
            .id(dto.getId())
            .body(dto.getBody())
            .user(UserMapper.dtoToEntity(dto.getUser()))
            .createdAt(dto.getCreatedAt())
            .build();
    }
}
