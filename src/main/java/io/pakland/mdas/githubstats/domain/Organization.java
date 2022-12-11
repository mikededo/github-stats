package io.pakland.mdas.githubstats.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
  private Integer id;

  @Column
  @JsonProperty("login")
  private String login;

  @OneToMany(
    mappedBy = "organization",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Team> teams = new ArrayList<>();

  public void addTeam(Team team) {
    if (teams == null) {
      teams = new ArrayList<>();
    }

    if (!teams.contains(team)) {
      teams.add(team);
    }
    team.setOrganization(this);
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
