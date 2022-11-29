package io.pakland.mdas.githubstats.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "historic_queries")
public class HistoricQueries {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team teamId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String from;

    @Column(nullable = false)
    private String to;
}
