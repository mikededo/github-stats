package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubTeamDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("slug")
    private String slug;
}
