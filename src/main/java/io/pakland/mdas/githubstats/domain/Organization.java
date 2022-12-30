package io.pakland.mdas.githubstats.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {

    @NotNull
    private Integer id;

    private String login;

    private Set<Team> teams = new HashSet<>();

    public void addTeam(Team team) {
        if (teams == null) {
            teams = new HashSet<>();
        }

        teams.add(team);
        team.setOrganization(this);
    }

    public boolean isNamed(String name) {
        return this.login.equals(name);
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
