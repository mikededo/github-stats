package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.application.internal.SaveAllTeams;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SaveAllTeamsTest {

    @Test
    public void savingTeams_shouldCallRepositoryMethods() {
        List<Team> teams = new ArrayList<>();
        TeamRepository teamRepositoryMock = Mockito.mock(TeamRepository.class);

        SaveAllTeams useCase = new SaveAllTeams(teamRepositoryMock);
        useCase.execute(teams);

        Mockito.verify(teamRepositoryMock, Mockito.times(1)).saveAll(Mockito.anyList());
    }
}
