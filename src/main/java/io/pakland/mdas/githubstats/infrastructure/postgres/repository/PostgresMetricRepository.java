package io.pakland.mdas.githubstats.infrastructure.postgres.repository;

import io.pakland.mdas.githubstats.domain.entity.Metric;
import io.pakland.mdas.githubstats.domain.repository.MetricRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public interface PostgresMetricRepository extends JpaRepository<Metric, Integer>, MetricRepository {

    @Transactional(readOnly=true)
    @Query(nativeQuery = true, value= """
           SELECT COUNT(1)
           FROM (
                 SELECT COUNT(1)
                   FROM metric
                  WHERE user_name = :#{#req.name}
                    AND "date_month" BETWEEN :#{#req.from} AND :#{#req.to}
               GROUP BY user_name, EXTRACT(YEAR from date_month), EXTRACT(MONTH from date_month)
           ) as sub
    """)
    Long countUserMonthsFromRange(@Param(value="req") ShellRequest req);

    @Transactional(readOnly=true)
    @Query(nativeQuery = true, value= """
             SELECT *
               FROM metric
              WHERE user_name = :#{#req.name}
                AND date_month BETWEEN :#{#req.from} AND :#{#req.to}
           ORDER BY user_name, date_month
    """)
    List<Metric> findUserMetricsFromRange(@Param(value="req") ShellRequest req);

}
