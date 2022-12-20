package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.PullRequestState;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import org.junit.jupiter.api.Test;

public class RepositoryPullRequestRequestTest {

    @Test
    public void shouldReturnAPair_whenGetDataIsCalled() {
        Repository repository = Repository.builder().id(1).name("github-stats").build();
        PullRequest pullRequest = PullRequest.builder().id(2).state(PullRequestState.OPEN).build();
        RepositoryPullRequestRequest request = new RepositoryPullRequestRequest(repository, pullRequest);

        assertEquals(request.getData().getFirst(), repository);
        assertEquals(request.getData().getSecond(), pullRequest);
    }
}
