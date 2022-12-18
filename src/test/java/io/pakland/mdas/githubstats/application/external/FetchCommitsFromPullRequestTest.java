package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.FetchCommitsFromPullRequest;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository.FetchCommitsFromPullRequestRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FetchCommitsFromPullRequestTest {

    @Test
    public void whenValidOrganizationAndRepository_shouldReturnTheListOfPullRequests()
            throws HttpException {
        Commit commit = new Commit();
        commit.setSha("a0b3ed9d5f1356575f2b16ab8ef5d93c5ce77575");
        commit.setDate(new Date());
        Commit commit1 = new Commit();
        commit1.setSha("f16b593d35d6e66dc7e1c8727d4eaa829d3973ed");
        commit1.setDate(new Date());

        CommitExternalRepository repository = Mockito.mock(
                CommitExternalRepository.class);
        FetchCommitsFromPullRequestRequest fetchCommitsFromPullRequestRequest = FetchCommitsFromPullRequestRequest.builder()
                .pullRequestNumber(1)
                .repositoryName("github-stats")
                .repositoryOwner("github-stats-22")
                .page(1)
                .perPage(100)
                .build();
        FetchCommitsFromPullRequestRequest fetchCommitsFromPullRequestRequest1 = FetchCommitsFromPullRequestRequest.builder()
                .pullRequestNumber(1)
                .repositoryName("github-stats")
                .repositoryOwner("github-stats-22")
                .page(2)
                .perPage(100)
                .build();

        Mockito.when(repository.fetchCommitsFromPullRequest(Mockito.any(
                FetchCommitsFromPullRequestRequest.class))).thenReturn(new ArrayList<>(Arrays.asList(commit, commit1))).thenReturn(new ArrayList<>());

        List<Commit> response = new FetchCommitsFromPullRequest(repository).execute(
                "github-stats-22", "github-stats", 1
        );

        ArgumentCaptor<FetchCommitsFromPullRequestRequest> captor = ArgumentCaptor.forClass(
                FetchCommitsFromPullRequestRequest.class);
        Mockito.verify(repository, Mockito.times(2)).fetchCommitsFromPullRequest(captor.capture());
        assertEquals("github-stats-22", captor.getValue().getRepositoryOwner());
        assertEquals("github-stats", captor.getValue().getRepositoryName());
        assertEquals(2, captor.getValue().getPage());
        assertEquals(100, captor.getValue().getPerPage());
        assertEquals(2, response.size());
        assertEquals(commit.getSha(), response.get(0).getSha());
        assertEquals(commit1.getSha(), response.get(1).getSha());
    }

    @Test
    public void whenRepositoryReturnsEmptyResponse_shouldReturnEmptyList() throws HttpException {
        CommitExternalRepository repository = Mockito.mock(
                CommitExternalRepository.class);
        Mockito.when(repository.fetchCommitsFromPullRequest(Mockito.any(
                FetchCommitsFromPullRequestRequest.class))).thenReturn(new ArrayList<>());

        List<Commit> response = new FetchCommitsFromPullRequest(repository).execute(
                "github-stats-22", "github-stats", 1
        );

        ArgumentCaptor<FetchCommitsFromPullRequestRequest> captor = ArgumentCaptor.forClass(
                FetchCommitsFromPullRequestRequest.class);
        Mockito.verify(repository).fetchCommitsFromPullRequest(captor.capture());

        assertEquals("github-stats-22", captor.getValue().getRepositoryOwner());
        assertEquals("github-stats", captor.getValue().getRepositoryName());
        assertEquals(1, captor.getValue().getPage());
        assertEquals(100, captor.getValue().getPerPage());
        assertEquals(0, response.size());
    }

    @Test
    public void whenRepositoryThrowsException_shouldThrowHttpException() throws HttpException {
        CommitExternalRepository repository = Mockito.mock(
                CommitExternalRepository.class);
        Mockito.when(repository.fetchCommitsFromPullRequest(Mockito.any(
                FetchCommitsFromPullRequestRequest.class))).thenThrow(new HttpException(404, "Page not found."));

        assertThrows(HttpException.class, () -> {
            new FetchCommitsFromPullRequest(repository).execute("github-stats-22",
                    "github-stats", 1);
        });
    }
}
