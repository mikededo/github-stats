package io.pakland.mdas.githubstats.infrastructure.shell.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
@Builder
public class TeamOptionRequest {
    private String teamName;
    private String apiKey;
    private Date from;
    private Date to;
}
