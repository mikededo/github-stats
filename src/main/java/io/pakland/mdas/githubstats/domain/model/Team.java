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
@Table(name = "team")
public class Team {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String slug;

  @Column(name = "member_url")
  private String memberUrl;

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

  @OneToMany(
    mappedBy = "team",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<HistoricQueries> queries = new ArrayList<>();

  public void addUser(User user) {
    users.add(user);
    user.setTeam(this);
  }

  public void removeUser(User user) {
    users.remove(user);
    user.setTeam(null);
  }

  public void addRepository(Repository repository) {
    repositories.add(repository);
    repository.setTeam(this);
  }

  public void removeRepository(Repository repository) {
    repositories.remove(repository);
    repository.setTeam(null);
  }

  public void addQuery(HistoricQueries historicQuery) {
    queries.add(historicQuery);
    historicQuery.setTeam(this);
  }

  public void removeQuery(HistoricQueries historicQuery) {
    queries.remove(historicQuery);
    historicQuery.setTeam(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Team )) return false;
    return id != null && id.equals(((Team) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
