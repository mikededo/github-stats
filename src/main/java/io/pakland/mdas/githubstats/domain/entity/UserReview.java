package io.pakland.mdas.githubstats.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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

    public boolean isReviewFromTeam(Team team) {
        return pullRequest.getRepository().getTeam().equals(team);
    }

}
