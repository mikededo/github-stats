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
    private String filePath;

    public boolean isOrganizationType() {
        return type.equals(OptionType.ORGANIZATION);
    }

    public boolean isTeamType() {
        return type.equals(OptionType.TEAM);
    }

    public boolean isUserType() {
        return type.equals(OptionType.USER);
    }
}
