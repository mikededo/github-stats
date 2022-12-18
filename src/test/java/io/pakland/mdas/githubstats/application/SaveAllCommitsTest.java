package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class SaveAllCommitsTest {

    @Test
    public void shouldCallCommitRepositorySaveAll_whenExecute() {

        List<Commit> commits = List.of(new Commit());
        CommitRepository commitRepositoryMock = Mockito.mock(CommitRepository.class);
        Mockito.when(commitRepositoryMock.saveAll(Mockito.anyList())).thenReturn(commits);
        SaveAllCommits saveAllCommits = new SaveAllCommits(commitRepositoryMock);

        saveAllCommits.execute(commits);

        Mockito.verify(commitRepositoryMock, Mockito.times(1)).saveAll(Mockito.anyList());

    }

}