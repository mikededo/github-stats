package io.pakland.mdas.githubstats.infrastructure.repository.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("login")
    private String login;

    @JsonProperty("organizations_url")
    private String organizationsUrl;

}
