package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.domain.entity.Repository;
import org.junit.jupiter.api.Test;

public class RepositoryRequestTest {

    @Test
    public void shouldReturnARepository_whenGetDataIsCalled() {
        Repository repository = Repository.builder().id(1).name("github-stats").build();
        RepositoryRequest request = new RepositoryRequest(repository);

        assertEquals(request.getData(), repository);
    }
}
