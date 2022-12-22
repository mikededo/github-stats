package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import java.util.Map;

public class GitHubRepositoryDTO implements RepositoryDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    private String ownerLogin;

    @JsonProperty("owner")
    private void unpackNameFromNestedObject(Map<String, String> owner) {
        this.ownerLogin = owner.get("login");
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOwnerLogin() {
        return this.ownerLogin;
    }
}
