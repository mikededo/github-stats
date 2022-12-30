package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.CommentDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import java.util.Date;
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

    @JsonProperty("user")
    private GitHubUserDTO user;

    @JsonProperty("created_at")
    private Date createdAt;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public UserDTO getUser() {
        return this.user;
    }

}
