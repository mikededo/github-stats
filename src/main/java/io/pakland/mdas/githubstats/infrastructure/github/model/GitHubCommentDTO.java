package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubCommentDTO implements CommentDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("body")
    private String body;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getBody() {
        return this.body;
    }
}
