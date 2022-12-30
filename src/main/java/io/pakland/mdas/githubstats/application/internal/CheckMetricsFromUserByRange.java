package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.infrastructure.postgres.repository.PostgresMetricRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class CheckMetricsFromUserByRange {
    private final PostgresMetricRepository postgresMetricRepository;

    protected CheckMetricsFromUserByRange(PostgresMetricRepository postgresMetricRepository) {
        this.postgresMetricRepository = postgresMetricRepository;
    }

    public Boolean execute(ShellRequest req) {
        return calculateMonthFromGivenRange(req)
                .equals(postgresMetricRepository.countUserMonthsFromRange(req));
    }

    private Long calculateMonthFromGivenRange(ShellRequest req) {
        return ChronoUnit.MONTHS.between(req.getDateFrom(),req.getDateTo());
    }

    private YearMonth convertDateToYearMonth(Date date) {
        return YearMonth.from(
            Instant
                .ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );
    }

}
