package io.pakland.mdas.githubstats.domain;

import io.pakland.mdas.githubstats.domain.utils.YearMonthDateAttributeConverter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "metric",
    uniqueConstraints = {@UniqueConstraint(columnNames = {
        "organization", "team_slug", "user_name", "to", "from"})
    })
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String organization;

    @NaturalId
    @Column(name = "team_slug", nullable = false)
    private String teamSlug;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "total_pulls", nullable = false)
    private Integer totalPulls;

    @Column(name = "merged_pulls", nullable = false)
    private Integer mergedPulls;

    @Column(name = "internal_reviews", nullable = false)
    private Integer internalReviews;

    @Column(name = "external_reviews", nullable = false)
    private Integer externalReviews;

    @Column(name = "comments_avg_length", nullable = false)
    private Integer commentsAvgLength;

    @Column(name = "commits_count", nullable = false)
    private Integer commitsCount;

    @Column(name = "lines_added", nullable = false)
    private Integer linesAdded;

    @Column(name = "lines_removed", nullable = false)
    private Integer linesRemoved;

    @Column(
        name = "to",
        columnDefinition = "date",
        nullable = false
    )
    @Convert(
        converter = YearMonthDateAttributeConverter.class
    )
    private YearMonth to;

    @Column(
        name = "from",
        columnDefinition = "date",
        nullable = false
    )
    @Convert(
        converter = YearMonthDateAttributeConverter.class
    )
    private YearMonth from;

    public static Metric empty() {
        Metric result = new Metric();
        result.totalPulls = 0;
        result.mergedPulls = 0;
        result.internalReviews = 0;
        result.externalReviews = 0;
        result.commentsAvgLength = 0;
        result.commitsCount = 0;
        result.linesAdded = 0;
        result.linesRemoved = 0;
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metric)) {
            return false;
        }
        return id != null && id.equals(((Metric) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public ArrayList<String> getValuesAsStringArrayList() {
        String to = this.to.atDay(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String from = this.from.atDay(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return new ArrayList<>(Arrays.asList(
            organization, teamSlug, userName,
            Integer.toString(totalPulls), Integer.toString(mergedPulls),
            Integer.toString(internalReviews), Integer.toString(externalReviews),
            Integer.toString(commentsAvgLength), Integer.toString(commitsCount),
            Integer.toString(linesAdded),
            Integer.toString(linesRemoved), from, to)
        );
    }
}
