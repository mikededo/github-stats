package io.pakland.mdas.githubstats.domain;

import io.pakland.mdas.githubstats.domain.utils.YearMonthDateAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "metric",
    uniqueConstraints = {@UniqueConstraint(columnNames = {
            "organization", "team_slug", "user_name", "year_month" })
})
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String organization;

    @Column(name="team_slug", nullable = false)
    private String teamSlug;

    @Column(name = "user_name", nullable = false)
    private String userName;

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
            name = "year_month",
            columnDefinition = "date",
            nullable = false
    )
    @Convert(
            converter = YearMonthDateAttributeConverter.class
    )
    private YearMonth yearMonth;

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

    public ArrayList<String> getValuesAsStringArrayList() {

        String yearMonthDate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return new ArrayList<>(Arrays.asList(
            Integer.toString(id), organization, teamSlug, userName,
            Integer.toString(mergedPulls), Integer.toString(internalReviews), Integer.toString(externalReviews),
            Integer.toString(commentsAvgLength), Integer.toString(commitsCount), Integer.toString(linesAdded),
            Integer.toString(linesRemoved), yearMonthDate
        ));
    }
}
