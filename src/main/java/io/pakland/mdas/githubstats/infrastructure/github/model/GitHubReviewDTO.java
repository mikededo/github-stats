package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.ReviewDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubReviewDTO implements ReviewDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("body")
    private String body;

    @JsonProperty("user")
    private GitHubUserDTO user;

    @JsonProperty("author_association")
    private String authorAssociation;

    @JsonProperty("submitted_at")
    private Date submittedAt;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public UserDTO getUser() {
        return this.user;
    }

    @Override
    public Date getSubmittedAt() {
        return this.submittedAt;
    }
}
