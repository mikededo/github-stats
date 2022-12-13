package io.pakland.mdas.githubstats.domain;

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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private PullRequest pullRequest;

    private int additions;

    private int deletions;

    private Instant created_at;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commit)) return false;
        return id != null && id.equals(((Commit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
