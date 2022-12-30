package io.pakland.mdas.githubstats.infrastructure.github.model;

import io.pakland.mdas.githubstats.domain.OptionType;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class GitHubOptionRequest {
    private String name;
    private String apiKey;
    private OptionType type;
    private Date from;
    private Date to;
}
