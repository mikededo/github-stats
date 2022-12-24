package io.pakland.mdas.githubstats.application.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.enums.PullRequestState;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository.FetchPullRequestFromRepositoryRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class FetchPullRequestFromRepositoryTest {

    private final Repository repository = Repository.builder().id(1).ownerLogin("github-stats-22").name("github-stats")
        .build();

    private void validateRequestCaptor(
        ArgumentCaptor<FetchPullRequestFromRepositoryRequest> captor) {
        assertEquals("github-stats", captor.getValue().getRepository());
        assertEquals("github-stats-22", captor.getValue().getRepositoryOwner());
        assertEquals(1, captor.getValue().getPage());
        assertEquals(100, captor.getValue().getPerPage());
        assertEquals(PullRequestState.ALL, captor.getValue().getState());
    }

    @Test
    public void whenValidOrganizationAndRepository_shouldReturnTheListOfPullRequests()
        throws HttpException {
        PullRequest prOne = new PullRequest();
        prOne.setId(1157910685);
        prOne.setNumber(71);
        PullRequest prTwo = new PullRequest();
        prTwo.setId(1157867973);
        prTwo.setNumber(69);

        PullRequestExternalRepository repository = Mockito.mock(
            PullRequestExternalRepository.class);
        Mockito.when(repository.fetchPullRequestsFromRepository(Mockito.any(
            FetchPullRequestFromRepositoryRequest.class))).thenReturn(List.of(prOne, prTwo));

        List<PullRequest> response = new FetchPullRequestsFromRepository(repository).execute(
            this.repository
        );

        ArgumentCaptor<FetchPullRequestFromRepositoryRequest> captor = ArgumentCaptor.forClass(
            FetchPullRequestFromRepositoryRequest.class);
        Mockito.verify(repository).fetchPullRequestsFromRepository(captor.capture());

        validateRequestCaptor(captor);
        assertEquals(2, response.size());
        assertEquals(prOne.getId(), response.get(0).getId());
        assertEquals(prTwo.getId(), response.get(1).getId());
        assertEquals(this.repository.getPullRequests().size(), 2);
        assertTrue(this.repository.getPullRequests().contains(prOne));
        assertTrue(this.repository.getPullRequests().contains(prTwo));
    }

    @Test
    public void whenRepositoryReturnsEmptyResponse_shouldReturnEmptyList() throws HttpException {
        PullRequestExternalRepository repository = Mockito.mock(
            PullRequestExternalRepository.class);
        Mockito.when(repository.fetchPullRequestsFromRepository(Mockito.any(
            FetchPullRequestFromRepositoryRequest.class))).thenReturn(new ArrayList<>());

        List<PullRequest> response = new FetchPullRequestsFromRepository(repository).execute(
            this.repository
        );

        ArgumentCaptor<FetchPullRequestFromRepositoryRequest> captor = ArgumentCaptor.forClass(
            FetchPullRequestFromRepositoryRequest.class);
        Mockito.verify(repository).fetchPullRequestsFromRepository(captor.capture());

        validateRequestCaptor(captor);
        assertEquals(0, response.size());
    }

    @Test
    public void whenRepositoryThrowsException_shouldThrowHttpException() throws HttpException {
        PullRequestExternalRepository repository = Mockito.mock(
            PullRequestExternalRepository.class);
        Mockito.when(repository.fetchPullRequestsFromRepository(Mockito.any(
                FetchPullRequestFromRepositoryRequest.class)))
            .thenThrow(new HttpException(404, "Page not found."));

        assertThrows(HttpException.class, () -> {
            new FetchPullRequestsFromRepository(repository).execute(this.repository);
        });
    }
}
