package io.pakland.mdas.githubstats.domain;

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
@Table(name = "pull_request")
public class PullRequest {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToMany(
    mappedBy = "pullRequest",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserReview> userReviews = new ArrayList<>();

  @OneToMany(
    mappedBy = "pullRequest",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Commit> commits = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  private Repository repository;

  public void addReview(UserReview userReview) {
    userReviews.add(userReview);
    userReview.setPullRequest(this);
  }

  public void removeReview(UserReview userReview) {
    userReviews.remove(userReview);
    userReview.setPullRequest(null);
  }

  public void addCommit(Commit commit) {
    commits.add(commit);
    commit.setPullRequest(this);
  }

  public void removeCommit(Commit commit) {
    commits.remove(commit);
    commit.setPullRequest(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PullRequest)) return false;
    return id != null && id.equals(((PullRequest) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
