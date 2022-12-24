package io.pakland.mdas.githubstats.domain.entity;

import io.pakland.mdas.githubstats.domain.enums.EntityType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "historic_queries")
public class HistoricQueries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name="entity_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate from;

    @Column(nullable = false)
    private LocalDate to;

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
