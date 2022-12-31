package io.pakland.mdas.githubstats.domain;

import java.util.Date;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Review implements Authored {

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

    @Override
    public boolean isAuthorNamed(String name) {
        return user.isNamed(name);
    }

    @Override
    public boolean isAuthorFromEntityTeam() {
        return this.pullRequest.userBelongsToTeam(this.user);
    }
}