package io.pakland.mdas.githubstats.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "metric")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String organization;

    @NaturalId
    @Column(name="team_slug", nullable = false)
    private String teamSlug;

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="merged_pulls", nullable = false)
    private Integer mergedPulls;

    @Column(name="internal_reviews", nullable = false)
    private Integer internalReviews;

    @Column(name="external_reviews", nullable = false)
    private Integer externalReviews;

    @Column(name="comments_avg_length", nullable = false)
    private Integer commentsAvgLength;

    @Column(name="commits_count", nullable = false)
    private Integer commitsCount;

    @Column(name="lines_added", nullable = false)
    private Integer linesAdded;

    @Column(name="lines_removed", nullable = false)
    private Integer linesRemoved;

    @Column(nullable = false)
    private LocalDate from;

    @Column(nullable = false)
    private LocalDate to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Metric)) return false;
        return id != null && id.equals(((Metric) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
