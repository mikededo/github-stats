package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FetchPullRequestFromRepositoryTest {

    private final Repository repositoryEntity = Repository.builder().id(1).ownerLogin("github-stats-22").name("github-stats")
        .build();

    private Date dateFrom = Date.from(ZonedDateTime.now().minusMonths(2).toInstant());
    private Date dateTo = Date.from(ZonedDateTime.now().toInstant());

    private void validateRequestCaptor(
        ArgumentCaptor<Repository> repositoryCaptor,
        ArgumentCaptor<Date> dateFromCaptor,
        ArgumentCaptor<Date> dateToCaptor,
        ArgumentCaptor<Integer> pageCaptor
    ) {
        assertEquals("github-stats", repositoryCaptor.getValue().getName());
        assertEquals("github-stats-22", repositoryCaptor.getValue().getOwnerLogin());
        assertEquals(this.dateFrom, dateFromCaptor.getValue());
        assertEquals(this.dateTo, dateToCaptor.getValue());
        assertEquals(1, pageCaptor.getValue());
    }

    @Test
    void whenValidOrganizationAndRepository_shouldReturnTheListOfPullRequests()
        throws HttpException {
        PullRequest pullRequest = PullRequest.builder().id(1157910685).number(71).build();
        PullRequest pullRequest1 = PullRequest.builder().id(1157867973).number(69).build();

        PullRequestExternalRepository repository = Mockito.mock(PullRequestExternalRepository.class);
        Mockito.when(repository.fetchPullRequestsFromRepositoryByPeriodAndPage(
            Mockito.any(Repository.class),
            Mockito.any(Date.class),
            Mockito.any(Date.class),
            Mockito.anyInt())
        ).thenReturn(List.of(pullRequest, pullRequest1));

        List<PullRequest> response =
            new FetchPullRequestsInPeriodFromRepository(repository)
                .execute(this.repositoryEntity, this.dateFrom, this.dateTo);

        ArgumentCaptor<Repository> repositoryCaptor = ArgumentCaptor.forClass(Repository.class);
        ArgumentCaptor<Date> dateFromCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> dateToCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(repository)
            .fetchPullRequestsFromRepositoryByPeriodAndPage(
                repositoryCaptor.capture(),
                dateFromCaptor.capture(),
                dateToCaptor.capture(),
                pageCaptor.capture()
            );

        validateRequestCaptor(repositoryCaptor, dateFromCaptor, dateToCaptor, pageCaptor);
        assertEquals(2, response.size());
        assertEquals(pullRequest.getId(), response.get(0).getId());
        assertEquals(pullRequest1.getId(), response.get(1).getId());
        assertEquals(2, this.repositoryEntity.getPullRequests().size());
        assertTrue(this.repositoryEntity.getPullRequests().contains(pullRequest));
        assertTrue(this.repositoryEntity.getPullRequests().contains(pullRequest1));
    }

    @Test
    void whenRepositoryReturnsEmptyResponse_shouldReturnEmptyList() throws HttpException {
        PullRequestExternalRepository repository =
            Mockito.mock(PullRequestExternalRepository.class);
        Mockito.when(repository.fetchPullRequestsFromRepositoryByPeriodAndPage(
            Mockito.any(Repository.class),
            Mockito.any(Date.class),
            Mockito.any(Date.class),
            Mockito.anyInt())
        ).thenReturn(new ArrayList<>());

        List<PullRequest> response =
            new FetchPullRequestsInPeriodFromRepository(repository)
                .execute(this.repositoryEntity, this.dateFrom, this.dateTo);

        ArgumentCaptor<Repository> repositoryCaptor = ArgumentCaptor.forClass(Repository.class);
        ArgumentCaptor<Date> dateFromCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> dateToCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(repository)
            .fetchPullRequestsFromRepositoryByPeriodAndPage(
                repositoryCaptor.capture(),
                dateFromCaptor.capture(),
                dateToCaptor.capture(),
                pageCaptor.capture()
            );

        validateRequestCaptor(repositoryCaptor, dateFromCaptor, dateToCaptor, pageCaptor);
        assertEquals(0, response.size());
    }

    @Test
    void whenRepositoryThrowsException_shouldThrowHttpException() throws HttpException {
        PullRequestExternalRepository repository =
            Mockito.mock(PullRequestExternalRepository.class);
        Mockito.when(repository.fetchPullRequestsFromRepositoryByPeriodAndPage(
            Mockito.any(Repository.class),
            Mockito.any(Date.class),
            Mockito.any(Date.class),
            Mockito.anyInt())
        ).thenThrow(new HttpException(404, "Page not found."));

        assertThrows(HttpException.class, () -> {
            new FetchPullRequestsInPeriodFromRepository(repository)
                .execute(this.repositoryEntity, new Date(), new Date());
        });
    }
}
