package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;

class SaveCommitsTest {

    @Test
    public void shouldCallCommitRepositorySaveAllWhenExecute() {

        List<Commit> commits = List.of(new Commit());
        CommitRepository commitRepositoryMock = Mockito.mock(CommitRepository.class);
        Mockito.when(commitRepositoryMock.saveAll(Mockito.anyList())).thenReturn(commits);
        SaveCommits saveCommits = new SaveCommits(commitRepositoryMock);

        saveCommits.execute(commits);

        Mockito.verify(commitRepositoryMock, Mockito.times(1)).saveAll(Mockito.anyList());

    }

}