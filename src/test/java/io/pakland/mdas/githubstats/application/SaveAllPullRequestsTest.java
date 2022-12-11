package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.PullRequestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SaveAllPullRequestsTest {

    @Test
    public void savingPullRequests_shouldCallRepositoryMethods() {
        List<PullRequest> pullRequests = new ArrayList<>();
        PullRequestRepository prRepositoryMock = Mockito.mock(PullRequestRepository.class);

        SaveAllPullRequests useCase = new SaveAllPullRequests(prRepositoryMock);
        useCase.execute(pullRequests);

        Mockito.verify(prRepositoryMock, Mockito.times(1)).saveAll(Mockito.anyList());
    }
}
