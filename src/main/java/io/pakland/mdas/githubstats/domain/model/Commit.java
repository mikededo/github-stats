package io.pakland.mdas.githubstats.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "commit")
public class Commit {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "pull_requests_id")
    private int pullRequestsId;

    @Column
    private int additions;

    @Column
    private int deletions;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private Instant date;

}
