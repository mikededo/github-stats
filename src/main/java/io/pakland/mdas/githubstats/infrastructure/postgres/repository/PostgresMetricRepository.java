package io.pakland.mdas.githubstats.infrastructure.postgres.repository;

import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.domain.repository.MetricRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public interface PostgresMetricRepository extends JpaRepository<Metric, Integer>, MetricRepository {

    @Transactional(readOnly=true)
    @Query(nativeQuery = true, value= """
           SELECT COUNT(*)
           FROM (
                 SELECT COUNT(*)
                   FROM metric
                  WHERE user_name = :#{#req.name}
                    AND year_month 
                        BETWEEN to_date(:#{#req.dateFrom.toString()}, 'YYYY-MM')
                        AND to_date(:#{#req.dateTo.toString()}, 'YYYY-MM')
               GROUP BY user_name,
                        EXTRACT(YEAR from year_month), 
                        EXTRACT(MONTH from year_month)
           ) as sub
    """)
    Long countUserMonthsFromRange(@Param(value="req") ShellRequest req);

    @Transactional(readOnly=true)
    @Query(nativeQuery = true, value= """
             SELECT *
               FROM metric
              WHERE user_name = :#{#req.name}
                AND year_month 
                    BETWEEN to_date(:#{#req.dateFrom.toString()}, 'YYYY-MM') 
                    AND to_date(:#{#req.dateTo.toString()}, 'YYYY-MM')
           ORDER BY user_name, year_month
    """)
    List<Metric> findUserMetricsFromRange(@Param(value="req") ShellRequest req);

}
