package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class GitHubRepositoryDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    private String ownerLogin;

    @JsonProperty("owner")
    private void unpackNameFromNestedObject(Map<String, String> owner) {
        this.ownerLogin = owner.get("login");
    }
}
