package io.pakland.mdas.githubstats.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "organization")
public class Organization {

    @Id
    @Column(updatable = false, nullable = false)
    @JsonProperty("id")
    @NotNull
    private Integer id;

    @Column
    @JsonProperty("login")
    private String login;

    @OneToMany(
        mappedBy = "organization",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Team> teams = new HashSet<>();

    public void addTeam(Team team) {
        if (teams == null) {
            teams = new HashSet<>();
        }

        teams.add(team);
        team.setOrganization(this);
    }

    public List<Team> getTeams() {
        return teams.stream().toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Organization that = (Organization) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
