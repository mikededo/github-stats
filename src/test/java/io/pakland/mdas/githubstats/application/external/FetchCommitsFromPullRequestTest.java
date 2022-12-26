package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FetchCommitsFromPullRequestTest {

    private final PullRequest pullRequestEntity = PullRequest.builder()
        .id(1)
        .number(1)
        .build();
    private final Repository repository = Repository.builder()
        .id(1)
        .name("github-stats")
        .ownerLogin("github-stats-22")
        .pullRequests(Set.of(pullRequestEntity))
        .build();

    @BeforeEach
    public void init() {
        this.pullRequestEntity.setCommits(new HashSet<>());
        this.pullRequestEntity.setRepository(repository);
    }

    @Test
    public void whenValidRepository_shouldReturnTheListOfCommits()
        throws HttpException {
        Commit commitOne = Commit.builder()
            .sha("a0b3ed9d5f1356575f2b16ab8ef5d93c5ce77575")
            .date(new Date())
            .additions(250)
            .deletions(125).build();
        Commit commitTwo = Commit.builder()
            .sha("f16b593d35d6e66dc7e1c8727d4eaa829d3973ed")
            .date(new Date())
            .additions(250)
            .deletions(125).build();

        CommitExternalRepository repository = Mockito.mock(
            CommitExternalRepository.class);

        Mockito.when(repository.fetchCommitsFromPullRequestByPage(
                Mockito.any(PullRequest.class), Mockito.anyInt()))
            .thenReturn(new ArrayList<>(Arrays.asList(commitOne, commitTwo)))
            .thenReturn(new ArrayList<>());

        List<Commit> response = new FetchCommitsFromPullRequest(repository).execute(
            this.pullRequestEntity);

        ArgumentCaptor<PullRequest> pullRequestCaptor = ArgumentCaptor.forClass(PullRequest.class);
        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(repository, Mockito.times(2))
            .fetchCommitsFromPullRequestByPage(pullRequestCaptor.capture(), pageCaptor.capture());
        assertEquals("github-stats-22", pullRequestCaptor.getValue().getRepository().getOwnerLogin());
        assertEquals("github-stats", pullRequestCaptor.getValue().getRepository().getName());
        assertEquals(2, pageCaptor.getValue());
        assertEquals(2, response.size());
        assertEquals(commitOne.getSha(), response.get(0).getSha());
        assertEquals(commitTwo.getSha(), response.get(1).getSha());
    }

    @Test
    public void whenRepositoryReturnsEmptyResponse_shouldReturnEmptyList() throws HttpException {
        CommitExternalRepository repository = Mockito.mock(
            CommitExternalRepository.class);
        Mockito.when(repository.fetchCommitsFromPullRequestByPage(
            Mockito.any(PullRequest.class), Mockito.anyInt())).thenReturn(new ArrayList<>());

        List<Commit> response = new FetchCommitsFromPullRequest(repository)
            .execute(this.pullRequestEntity);

        ArgumentCaptor<PullRequest> pullRequestCaptor = ArgumentCaptor.forClass(PullRequest.class);
        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(repository)
            .fetchCommitsFromPullRequestByPage(pullRequestCaptor.capture(), pageCaptor.capture());
        assertEquals("github-stats-22", pullRequestCaptor.getValue().getRepository().getOwnerLogin());
        assertEquals("github-stats", pullRequestCaptor.getValue().getRepository().getName());
        assertEquals(1, pageCaptor.getValue());
        assertEquals(0, response.size());
    }

    @Test
    public void whenRepositoryThrowsException_shouldThrowHttpException() throws HttpException {
        CommitExternalRepository repository = Mockito.mock(
            CommitExternalRepository.class);
        Mockito.when(repository.fetchCommitsFromPullRequestByPage(
                Mockito.any(PullRequest.class), Mockito.anyInt()))
            .thenThrow(new HttpException(404, "Page not found."));

        assertThrows(HttpException.class, () -> {
            new FetchCommitsFromPullRequest(repository).execute(this.pullRequestEntity);
        });
    }
}
