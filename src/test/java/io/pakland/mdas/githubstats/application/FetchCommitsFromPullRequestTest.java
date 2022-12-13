package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.PullRequestState;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository.FetchPullRequestFromRepositoryRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FetchCommitsFromPullRequestTest {

    private void validateRequestCaptor(
            ArgumentCaptor<FetchPullRequestFromRepositoryRequest> captor) {
        assertEquals("github-stats-22", captor.getValue().getRepositoryOwner());
        assertEquals("github-stats", captor.getValue().getRepository());
        assertEquals(1, captor.getValue().getPage());
        assertEquals(100, captor.getValue().getPerPage());
        assertEquals(PullRequestState.ALL, captor.getValue().getState());
    }

    @Test
    public void whenValidOrganizationAndRepository_shouldReturnTheListOfPullRequests()
            throws HttpException {
        Commit commit = new Commit();
        commit.setId(1157910685);
        prOne.setNumber(71);
        PullRequest prTwo = new PullRequest();
        prTwo.setId(1157867973);
        prTwo.setNumber(69);

        PullRequestExternalRepository repository = Mockito.mock(
                PullRequestExternalRepository.class);
        Mockito.when(repository.fetchPullRequestsFromRepository(Mockito.any(
                FetchPullRequestFromRepositoryRequest.class))).thenReturn(List.of(prOne, prTwo));

        List<PullRequest> response = new FetchPullRequestsFromRepository(repository).execute(
                "github-stats-22", "github-stats"
        );

        ArgumentCaptor<FetchPullRequestFromRepositoryRequest> captor = ArgumentCaptor.forClass(
                FetchPullRequestFromRepositoryRequest.class);
        Mockito.verify(repository).fetchPullRequestsFromRepository(captor.capture());

        validateRequestCaptor(captor);
        assertEquals(2, response.size());
        assertEquals(prOne.getId(), response.get(0).getId());
        assertEquals(prTwo.getId(), response.get(1).getId());
    }

    @Test
    public void whenRepositoryReturnsEmptyResponse_shouldReturnEmptyList() throws HttpException {
//        PullRequestExternalRepository repository = Mockito.mock(
//                PullRequestExternalRepository.class);
//        Mockito.when(repository.fetchPullRequestsFromRepository(Mockito.any(
//                FetchPullRequestFromRepositoryRequest.class))).thenReturn(new ArrayList<>());
//
//        List<PullRequest> response = new FetchPullRequestsFromRepository(repository).execute(
//                "github-stats-22", "github-stats"
//        );
//
//        ArgumentCaptor<FetchPullRequestFromRepositoryRequest> captor = ArgumentCaptor.forClass(
//                FetchPullRequestFromRepositoryRequest.class);
//        Mockito.verify(repository).fetchPullRequestsFromRepository(captor.capture());
//
//        validateRequestCaptor(captor);
//        assertEquals(0, response.size());
    }

    @Test
    public void whenRepositoryThrowsException_shouldThrowHttpException() throws HttpException {
//        PullRequestExternalRepository repository = Mockito.mock(
//                PullRequestExternalRepository.class);
//        Mockito.when(repository.fetchPullRequestsFromRepository(Mockito.any(
//                        FetchPullRequestFromRepositoryRequest.class)))
//                .thenThrow(new HttpException(404, "Page not found."));
//
//        assertThrows(HttpException.class, () -> {
//            new FetchPullRequestsFromRepository(repository).execute("github-stats-22",
//                    "github-stats");
//        });
    }
}
