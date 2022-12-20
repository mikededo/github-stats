package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import io.pakland.mdas.githubstats.application.handlers.Request;

public class NullRequest implements Request {
    @Override
    public Object getData() {
        return null;
    }
}
