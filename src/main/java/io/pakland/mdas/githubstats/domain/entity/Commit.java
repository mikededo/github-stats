package io.pakland.mdas.githubstats.domain.entity;

import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class Commit {

    private String sha;

    private User user;

    private Integer additions;

    private Integer deletions;

    private Date date;

    private PullRequest pullRequest;

    // The hashcode must be overridden otherwise it causes a stackoverflow issue
    // when adding a commit to a set like structure
    @Override
    public int hashCode() {
        return sha.hashCode();
    }
}
