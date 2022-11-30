package io.pakland.mdas.githubstats.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "historic_queries")
public class HistoricQueries {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Column(nullable = false)
    private String name;

    @Column(name="from", nullable = false)
    private String from;

    @Column(name="to", nullable = false)
    private String to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricQueries)) return false;
        return id != null && id.equals(((HistoricQueries) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
