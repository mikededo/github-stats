package io.pakland.mdas.githubstats.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class TeamTest {

    @Test
    public void shouldAddTheRepository_andAssignTheTeamToTheRepository() {
        Team team = Team.builder().id(1).slug("gs-developers").build();
        Repository repository = Repository.builder().id(1).name("github-stats").build();

        assertNull(repository.getTeam());
        team.addRepository(repository);

        assertEquals(repository.getTeam(), team);
        assertEquals(1, team.getRepositories().size());
    }

    @Test
    public void shouldNotAddTheRepository_whenTheRepositoryIsAlreadyContained() {
        Team team = Team.builder().id(1).slug("gs-developers").build();
        Repository repository = Repository.builder().id(1).name("github-stats").build();
        team.setRepositories(new HashSet<>(Collections.singletonList(repository)));

        assertNull(repository.getTeam());
        assertEquals(1, team.getRepositories().size());
        team.addRepository(repository);

        assertEquals(repository.getTeam(), team);
        assertEquals(1, team.getRepositories().size());
    }

    @Test
    public void shouldCheckForEqualTeams() {
        Team original = Team.builder().id(1).build();
        Team equal = Team.builder().id(1).build();
        Team notEqual = Team.builder().id(2).build();

        assertEquals(original, equal);
        assertNotEquals(original, notEqual);
    }

}
