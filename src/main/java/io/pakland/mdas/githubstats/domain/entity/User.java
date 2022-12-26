package io.pakland.mdas.githubstats.domain.entity;

import java.util.Objects;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Integer id;

    private String login;

    private Team team;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }

        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
