package io.pakland.mdas.githubstats.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "user")
public class User {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String login;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Team team;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserReview> userReviews = new ArrayList<>();

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Commit> commits = new ArrayList<>();

  public void addReview(UserReview userReview) {
    userReviews.add(userReview);
    userReview.setUser(this);
  }

  public void removeReview(UserReview userReview) {
    userReviews.remove(userReview);
    userReview.setUser(null);
  }

  public void addCommit(Commit commit) {
    commits.add(commit);
    commit.setUser(this);
  }

  public void removeCommit(Commit commit) {
    commits.remove(commit);
    commit.setUser(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User )) return false;
    return id != null && id.equals(((User) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
