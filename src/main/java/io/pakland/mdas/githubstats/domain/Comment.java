package io.pakland.mdas.githubstats.domain;

import java.util.Date;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Comment implements Authored {

    private Integer id;

    private User user;

    private String body;

    private Date createdAt;

    @ToString.Exclude
    private PullRequest pullRequest;

    @Override
    public boolean isAuthorNamed(String name) {
        return this.user.isNamed(name);
    }

    @Override
    public boolean isAuthorFromEntityTeam() {
        return this.pullRequest.userBelongsToTeam(this.user);
    }

    public int bodySize() {
        return this.body.length();
    }
}