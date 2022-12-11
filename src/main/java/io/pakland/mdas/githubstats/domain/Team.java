package io.pakland.mdas.githubstats.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "team")
public class Team {

  @Id
  @Column(updatable = false, nullable = false)
  @JsonProperty("id")
  private Integer id;

  @Column(name = "slug")
  @JsonProperty("slug")
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
  private List<Repository> repositories = new ArrayList<>();

  public void addRepository(Repository repository) {
    if (repositories == null) {
      repositories = new ArrayList<>();
    }

    if (!repositories.contains(repository)) {
      repositories.add(repository);
    }
    repository.setTeam(this);
  }
}
