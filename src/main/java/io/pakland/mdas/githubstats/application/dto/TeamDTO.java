package io.pakland.mdas.githubstats.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("slug")
    private String slug;

    private List<UserDTO> users;

    private List<RepositoryDTO> repositories;
}
