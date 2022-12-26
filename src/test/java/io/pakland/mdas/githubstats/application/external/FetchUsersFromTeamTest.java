package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FetchUsersFromTeamTest {

    private final Team team = Team.builder().id(1).slug("gs-internal").build();
    private final Organization organization = Organization.builder().teams(Set.of(team))
        .login("github-stats-22").build();

    @BeforeEach
    public void init() {
        team.setUsers(new HashSet<>());
        team.setOrganization(organization);
    }

    @Test
    public void shouldFetchUsers_givenATeam() throws HttpException {
        User userOne = User.builder().id(1).login("mikededo").build();
        User userTwo = User.builder().id(2).login("manerow").build();
        UserExternalRepository repository = Mockito.mock(UserExternalRepository.class);
        Mockito.when(repository.fetchUsersFromTeam(this.team))
            .thenReturn(List.of(userOne, userTwo));

        List<User> result = new FetchUsersFromTeam(repository).execute(team);

        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getId(), 1);
        assertEquals(result.get(1).getId(), 2);
        assertEquals(result.get(0).getTeam(), team);
        assertEquals(result.get(1).getTeam(), team);
    }

    @Test
    public void shouldThrowAnException_whenRepositoryThrows() throws HttpException {
        UserExternalRepository repository = Mockito.mock(UserExternalRepository.class);
        Mockito.when(repository.fetchUsersFromTeam(this.team))
            .thenThrow(HttpException.class);

        assertThrows(HttpException.class, () ->
            new FetchUsersFromTeam(repository).execute(team));
    }
}
