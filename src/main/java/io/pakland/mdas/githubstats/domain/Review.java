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

    public boolean isInternal() {
        return pullRequest.getRepository().getTeam().hasUser(user);
    }

    public int bodySize() {
        return body.length();
    }

    public boolean isAuthorNamed(String name) {
        return user.isNamed(name);
    }

}
