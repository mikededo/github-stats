package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.CommentDTO;
import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommentDTO;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {

    @Test
    public void shouldConvertDTOToEntity() {
        CommentDTO commentDTO = new GitHubCommentDTO(1, "This is a test comment");
        Comment commentEntity = CommentMapper.dtoToEntity(commentDTO);

        assertEquals(1, (int) commentEntity.getId());
        assertEquals(22, commentEntity.getLength());
    }
}
