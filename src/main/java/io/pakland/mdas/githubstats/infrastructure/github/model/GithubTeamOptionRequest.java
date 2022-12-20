package io.pakland.mdas.githubstats.infrastructure.github.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
@Builder
public class GithubTeamOptionRequest {
    private String teamName;
    private String apiKey;
    private Date from;
    private Date to;
}
