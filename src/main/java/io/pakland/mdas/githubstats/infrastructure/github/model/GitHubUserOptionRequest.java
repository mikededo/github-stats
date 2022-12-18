package io.pakland.mdas.githubstats.infrastructure.github.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class GitHubUserOptionRequest {
    private String userName;
    private String apiKey;
    private Date from;
    private Date to;
}
