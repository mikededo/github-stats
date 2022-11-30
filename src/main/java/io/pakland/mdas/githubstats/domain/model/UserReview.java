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
@Table(name = "user_review")
public class UserReview {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private PullRequest pullRequest;

  @OneToMany(
    mappedBy = "userReview",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Comment> comments = new ArrayList<>();

  public void addReview(Comment comment) {
    comments.add(comment);
    comment.setUserReview(this);
  }

  public void removeReview(Comment comment) {
    comments.remove(comment);
    comment.setUserReview(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserReview )) return false;
    return id != null && id.equals(((UserReview) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
