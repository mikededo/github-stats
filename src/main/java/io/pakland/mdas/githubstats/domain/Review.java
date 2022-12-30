package io.pakland.mdas.githubstats.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    private Integer id;

    private String body;

    private User user;

    private Date submittedAt;

    private PullRequest pullRequest;

    // This value allows us to know if the author of the review is part of the team that
    // owns the repository
    private boolean isReviewFromInternalAuthor;

    public boolean isInternal() {
        return this.isReviewFromInternalAuthor;
    }

    public int bodySize() {
        return this.body.length();
    }

    public boolean isAuthorNamed(String name) {
        return user.isNamed(name);
    }

}
