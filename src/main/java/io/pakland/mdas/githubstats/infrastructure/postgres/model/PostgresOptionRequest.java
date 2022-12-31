package io.pakland.mdas.githubstats.infrastructure.postgres.model;

import io.pakland.mdas.githubstats.domain.OptionType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.YearMonth;
import java.util.Date;

@Data
@Getter
@Builder
public class PostgresOptionRequest {

    private String name;
    private String apiKey;
    private OptionType type;
    private YearMonth from;
    private YearMonth to;
    private String filePath;
    private boolean silenced;

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