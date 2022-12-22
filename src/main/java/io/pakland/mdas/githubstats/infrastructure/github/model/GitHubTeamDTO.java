package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.TeamDTO;

public class GitHubTeamDTO implements TeamDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("slug")
    private String slug;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }
}
