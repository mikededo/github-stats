package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.CommentDTO;
import io.pakland.mdas.githubstats.domain.entity.Comment;

public class CommentMapper {

    public static Comment dtoToEntity(CommentDTO dto) {
        return Comment.builder()
            .id(dto.getId())
            .body(dto.getBody())
            .createdAt(dto.getCreatedAt())
            .build();
    }
}
