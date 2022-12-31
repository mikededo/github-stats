package io.pakland.mdas.githubstats.infrastructure.shell.model;

import io.pakland.mdas.githubstats.domain.OptionType;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShellRequest {

    private String apiKey;

    private OptionType entityType;

    private String name;

    private YearMonth dateFrom;

    private YearMonth dateTo;

    private String filePath;

    private boolean silence;

    public Date transformYearMonthToDate(YearMonth yearMonth) {
        System.out.format("%s, %s\n", yearMonth.toString(),
            yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toString());
        return Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
