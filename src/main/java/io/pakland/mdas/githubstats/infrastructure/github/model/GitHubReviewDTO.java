package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.UserReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubReviewDTO implements UserReviewDTO {
    @JsonProperty("id")
    private Integer id;

    @Override
    public Integer getId() {
        return this.id;
    }
}
