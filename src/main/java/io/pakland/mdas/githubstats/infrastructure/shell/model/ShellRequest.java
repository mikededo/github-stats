package io.pakland.mdas.githubstats.infrastructure.shell.model;

import io.pakland.mdas.githubstats.domain.EntityType;
import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;
import java.util.Date;

@Data
@Builder
public class ShellRequest {

    private String apiKey;

    private EntityType entityType;

    private String name;

    private YearMonth dateFrom;

    private YearMonth dateTo;

    private String filePath;
}
