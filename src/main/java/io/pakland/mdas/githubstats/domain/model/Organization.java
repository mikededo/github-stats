package io.pakland.mdas.githubstats.domain.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "organization")
public class Organization {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "organization_url")
  private String organizationUrl;

  @OneToMany(
    mappedBy = "organization",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Team> teams = new ArrayList<>();

  public void addTeam(Team team) {
    teams.add(team);
    team.setOrganization(this);
  }

  public void removeTeam(Team team) {
    teams.remove(team);
    team.setOrganization(null);
  }

}
