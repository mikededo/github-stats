package io.pakland.mdas.githubstats.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import javax.persistence.*;
import lombok.*;
import org.springframework.web.reactive.result.HandlerResultHandlerSupport;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public List<Repository> getRepositories() {
        return repositories.stream().toList();
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
