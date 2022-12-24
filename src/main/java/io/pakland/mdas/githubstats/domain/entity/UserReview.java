package io.pakland.mdas.githubstats.domain.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class UserReview {

  private Integer id;

  private User user;

  private PullRequest pullRequest;

  private List<Comment> comments = new ArrayList<>();

  public int sumCommentLength() {
    return comments.stream().mapToInt(Comment::getLength).sum();
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

  public boolean isReviewFromTeam(Team team) {
    return pullRequest.getRepository().getTeam().equals(team);
  }

}
