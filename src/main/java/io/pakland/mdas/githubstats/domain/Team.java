package io.pakland.mdas.githubstats.domain;

import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Team {

    private Integer id;

    private String slug;

    private Organization organization;

    private Set<User> users = new HashSet<>();

    private Set<Repository> repositories = new HashSet<>();

    public void addUsers(Collection<User> users) {
        if (this.users == null) {
            this.users = new HashSet<>();
        }

        users.forEach(user -> {
            user.setTeam(this);
            this.users.add(user);
        });
    }

    public void addRepositories(Collection<Repository> repositories) {
        repositories.forEach(this::addRepository);
    }

    public void addRepository(Repository repository) {
        if (repositories == null) {
            repositories = new HashSet<>();
        }

        repositories.add(repository);
        repository.setTeam(this);
    }

    public boolean isNamed(String name) {
        return this.slug.equals(name);
    }

    public boolean hasUser(User user) {
        if (users == null) {
            return false;
        }

        return users.contains(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Team team = (Team) o;

        return id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}