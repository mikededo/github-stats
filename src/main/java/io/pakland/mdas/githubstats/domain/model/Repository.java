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
@Table(name = "repository")
public class Repository {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String owner;

  @Column(name = "merges_url")
  private String mergesUrl;

  @Column(name = "commits_url")
  private String commitsUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  private Team team;

  @OneToMany(
    mappedBy = "repository",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<PullRequest> pullRequests = new ArrayList<>();

  public void addPullRequests(PullRequest pullRequest) {
    pullRequests.add(pullRequest);
    pullRequest.setRepository(this);
  }

  public void removePullRequests(PullRequest pullRequest) {
    pullRequests.remove(pullRequest);
    pullRequest.setRepository(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Repository )) return false;
    return id != null && id.equals(((Repository) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
