package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubOrganizationDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("login")
    private String login;
}
