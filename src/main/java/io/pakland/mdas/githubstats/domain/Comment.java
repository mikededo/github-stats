package io.pakland.mdas.githubstats.domain;

import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Integer id;

    private User user;

    private String body;

    private Date createdAt;

    private PullRequest pullRequest;

    public boolean isAuthorNamed(String name) {
        return this.user.isNamed(name);
    }
}
