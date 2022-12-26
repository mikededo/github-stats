package io.pakland.mdas.githubstats.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

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

    public boolean isInternal() {
        return getPullRequest().getRepository().getTeam()
                .equals(getUser().getTeam());
    }

}
