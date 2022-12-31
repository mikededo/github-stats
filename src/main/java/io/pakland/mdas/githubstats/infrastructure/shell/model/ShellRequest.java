package io.pakland.mdas.githubstats.infrastructure.shell.model;

import io.pakland.mdas.githubstats.domain.OptionType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

@Data
@Builder
public class ShellRequest {

    private String apiKey;

    private OptionType entityType;

    private String name;

    private YearMonth dateFrom;

    private YearMonth dateTo;

    public Date transformYearMonthToDate(YearMonth yearMonth) {
        return Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
