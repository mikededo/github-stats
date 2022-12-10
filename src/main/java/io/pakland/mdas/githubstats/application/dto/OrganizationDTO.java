package io.pakland.mdas.githubstats.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("login")
    private String login;

    private List<TeamDTO> teams;
}
