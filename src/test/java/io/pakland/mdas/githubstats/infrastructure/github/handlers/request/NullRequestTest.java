package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class NullRequestTest {

    @Test
    public void shouldReturnNull_whenGetDataIsCalled() {
        NullRequest request = new NullRequest();
        assertNull(request.getData());
    }
}
