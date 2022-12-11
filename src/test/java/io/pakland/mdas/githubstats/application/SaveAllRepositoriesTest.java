package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Repository;
import io.pakland.mdas.githubstats.domain.repository.RepositoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SaveAllRepositoriesTest {

    @Test
    public void savingRepositories_shouldCallRepositoryMethods() {
        List<Repository> repositories = new ArrayList<>();
        RepositoryRepository repositoryRepositoryMock = Mockito.mock(RepositoryRepository.class);

        SaveAllRepositories useCase = new SaveAllRepositories(repositoryRepositoryMock);
        useCase.execute(repositories);

        Mockito.verify(repositoryRepositoryMock, Mockito.times(1)).saveAll(Mockito.anyList());
    }
}
