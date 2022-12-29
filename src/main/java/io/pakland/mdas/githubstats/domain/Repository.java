package io.pakland.mdas.githubstats.domain;

import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repository {

    private Integer id;

    private String name;

    private String ownerLogin;

    @ToString.Exclude
    private Team team;

    private Set<PullRequest> pullRequests = new HashSet<>();

    public void addPullRequests(Collection<PullRequest> pullRequests) {
        if (this.pullRequests == null) {
            this.pullRequests = new HashSet<>();
        }

        pullRequests.forEach(pr -> {
            pr.setRepository(this);
            this.pullRequests.add(pr);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Repository that = (Repository) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
