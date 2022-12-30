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
           SELECT COUNT(1)
           FROM (
                 SELECT COUNT(1)
                   FROM metric
                  WHERE user_name = :name
                    AND year_month BETWEEN :from AND :to
               GROUP BY user_name, EXTRACT(YEAR from year_month), EXTRACT(MONTH from year_month)
           ) as sub
    """)
    Long countUserMonthsFromRangeQuery(@Param(value="name") String name, @Param(value="from") Date from, @Param(value="to") Date to);

    default Long countUserMonthsFromRange(ShellRequest req) {
        Date from = Date.from(req.getDateFrom().atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(req.getDateTo().atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(from);
        return countUserMonthsFromRangeQuery(req.getName(), from, to);
    }

    @Transactional(readOnly=true)
    @Query(nativeQuery = true, value= """
             SELECT *
               FROM metric
              WHERE user_name = :name
                AND year_month BETWEEN :from AND :to
           ORDER BY user_name, year_month
    """)
    List<Metric> findUserMetricsFromRangeQuery(@Param(value="name") String name, @Param(value="from") Date from, @Param(value="to") Date to);

    default List<Metric> findUserMetricsFromRange(ShellRequest req) {
        Date from = Date.from(req.getDateFrom().atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(req.getDateTo().atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(from);
        return findUserMetricsFromRangeQuery(req.getName(), from, to);
    }

}
