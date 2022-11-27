package io.pakland.mdas.githubstats.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "historic_queries")
public class HistoricQueries {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "team_id")
    private Team teamId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "from", nullable = false)
    private String from;

    @Column(name = "to", nullable = false)
    private String to;
}