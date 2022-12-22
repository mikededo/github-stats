package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;

public class GitHubOrganizationDTO implements OrganizationDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("login")
    private String login;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getLogin() {
        return this.login;
    }
}
