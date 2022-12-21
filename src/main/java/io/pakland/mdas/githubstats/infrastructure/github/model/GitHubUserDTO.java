package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.UserDTO;

public class GitHubUserDTO implements UserDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("login")
    private String login;

    public Integer getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }
}
