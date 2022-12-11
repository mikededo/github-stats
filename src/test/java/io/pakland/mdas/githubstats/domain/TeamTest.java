package io.pakland.mdas.githubstats.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
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
    public void shouldNotAddTheRepository_whenTheTeamIsAlreadyContained() {
        Team team = Team.builder().id(1).slug("gs-developers").build();
        Repository repository = Repository.builder().id(1).name("github-stats").build();
        team.setRepositories(Collections.singletonList(repository));

        assertNull(repository.getTeam());
        team.addRepository(repository);

        assertEquals(repository.getTeam(), team);
        assertEquals(1, team.getRepositories().size());
    }

}
