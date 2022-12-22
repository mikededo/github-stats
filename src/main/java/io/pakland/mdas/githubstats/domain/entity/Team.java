package io.pakland.mdas.githubstats.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "team")
public class Team {

    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "slug")
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @OneToMany(
        mappedBy = "team",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<User> users = new ArrayList<>();

    @OneToMany(
        mappedBy = "team",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Repository> repositories = new HashSet<>();

    public void addRepository(Repository repository) {
        if (repositories == null) {
            repositories = new HashSet<>();
        }

        repositories.add(repository);
        repository.setTeam(this);
    }

    public List<Repository> getRepositories() {
        return repositories.stream().toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Team team = (Team) o;

        return id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
