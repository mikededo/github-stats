package io.pakland.mdas.githubstats.domain.repository.impl;

import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.domain.repository.MetricRepositoryCustom;
import io.pakland.mdas.githubstats.infrastructure.postgres.model.PostgresOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly=true)
public class MetricRepositoryImpl implements MetricRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Metric> getMetricUsingUniqueConstraint(Metric metric) {
       return (Optional<Metric>) entityManager.createNativeQuery("""
                SELECT *
                  FROM metric
                 WHERE organization = :organization
                   AND team_slug = :team_slug
                   AND user_name = :user_name
                   AND "from" = to_date(:from, 'YYYY-MM')
                   AND "to" = to_date(:to, 'YYYY-MM')
            """, Metric.class)
           .setParameter("organization", metric.getOrganization() )
           .setParameter("team_slug", metric.getTeamSlug() )
           .setParameter("user_name", metric.getUserName() )
           .setParameter("from", metric.getFrom().toString() )
           .setParameter("to", metric.getTo().toString() )
           .getResultStream().findFirst();
    }

    @Override
    public BigInteger countAggregatesFromOption(PostgresOptionRequest req) {

        return (BigInteger) entityManager.createNativeQuery(String.format("""
                   SELECT COUNT(*)
                     FROM metric
                    WHERE %s = :name
                      AND "from" = to_date(:from, 'YYYY-MM')
                      AND "to" = to_date(:to, 'YYYY-MM')
            """,req.getType().getOption()))
            .setParameter("name", req.getName() )
            .setParameter("from", req.getFrom().toString() )
            .setParameter("to", req.getTo().toString() )
            .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Metric> getAggregatesFromOption(PostgresOptionRequest req) {

        return (List<Metric>) entityManager.createNativeQuery(String.format("""
                       SELECT *
                         FROM metric
                        WHERE %s = :name
                          AND "from" = to_date(:from, 'YYYY-MM')
                          AND "to" = to_date(:to, 'YYYY-MM')
                """, req.getType().getOption()), Metric.class)
                .setParameter("name", req.getName() )
                .setParameter("from", req.getFrom().toString() )
                .setParameter("to", req.getTo().toString() )
                .getResultList();
    }

    /*@Transactional(readOnly=true)
    @Query(nativeQuery = true, value="""
            SELECT *
              FROM metric
             WHERE organization = :#{#metric.organization}
               AND team_slug = :#{#metric.teamSlug}
               AND user_name = :#{#metric.userName}
               AND year_month = to_date(:#{#metric.yearMonth.toString()}, 'YYYY-MM')
    """)
    Metric getMetricUsingUniqueConstraint(@Param("metric") Metric metric);

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
    */
}